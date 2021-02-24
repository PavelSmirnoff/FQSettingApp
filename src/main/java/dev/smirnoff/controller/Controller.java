package dev.smirnoff.controller;

import com.intelligt.modbus.jlibmodbus.master.ModbusMaster;
import com.intelligt.modbus.jlibmodbus.master.ModbusMasterFactory;
import com.intelligt.modbus.jlibmodbus.serial.SerialParameters;
import com.intelligt.modbus.jlibmodbus.serial.SerialPort;
import dev.smirnoff.model.Port;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;

public class Controller {
    private final Port port = new Port();
    private ModbusMaster modbusMaster;

    @FXML
    public ChoiceBox<String> portList;
    @FXML
    public ChoiceBox<SerialPort.BaudRate> baudRateList;
    @FXML
    public ChoiceBox<SerialPort.Parity> parityList;
    @FXML
    public ChoiceBox<Integer> stopBitsList;
    @FXML
    public Button buttonFindPorts;
    @FXML
    public Button buttonOpenPort;
    @FXML
    public Button buttonClosePort;
    @FXML
    public ListView<String> logView;

    public void initialize() {
        //portList.setOnAction(this::changePort);
        // existing code ...
        baudRateList.setItems(FXCollections.observableArrayList(SerialPort.BaudRate.values()));
        baudRateList.setValue(SerialPort.BaudRate.BAUD_RATE_9600);
        parityList.setItems(FXCollections.observableArrayList(SerialPort.Parity.values()));
        parityList.setValue(SerialPort.Parity.NONE);
        stopBitsList.setItems(FXCollections.observableArrayList(1, 2));
        stopBitsList.setValue(2);
        buttonClosePort.setDisable(true);
        this.findPorts();
    }

    public Controller() {
    }

    @FXML
    public void findPorts() {
        port.setPortList();
        String[] pL = port.getPortList();
        portList.setItems(FXCollections.observableArrayList(pL));
        if (pL.length > 0) {
            buttonOpenPort.setDisable(false);
            if (portList.getValue() == null) {
                portList.setValue(pL[0]);
            }
        } else {
            buttonOpenPort.setDisable(true);
            writeLog("Нет доступных портов");
        }
    }

    @FXML
    public void changePort() {
        if (portList.getValue() != null) writeLog("Выбран порт: " + portList.getValue());
    }

    @FXML
    public void setSerialParameters() {
        // System.out.println("Изменение настроек порта");
    }

    @FXML
    public void openPort() {
        writeLog("Инициализация порта");

        if (port.getPortList().length > 0) {
            SerialParameters sp = new SerialParameters();
            sp.setDevice(portList.getValue());
            sp.setBaudRate(baudRateList.getValue());
            sp.setDataBits(8);
            sp.setParity(parityList.getValue());
            sp.setStopBits(stopBitsList.getValue());
            port.setSerialParameters(sp);
            writeLog("Параметры инициализации: "
                    + port.getSerialParameters().getDevice() + " "
                    + port.getSerialParameters().getBaudRate() + " "
                    + port.getSerialParameters().getDataBits() + "-"
                    + port.getSerialParameters().getParity() + "-" +
                    +port.getSerialParameters().getStopBits());
            try {
                modbusMaster = ModbusMasterFactory.createModbusMasterRTU(sp);
                modbusMaster.connect();
                if (modbusMaster.isConnected()) {
                    buttonClosePort.setDisable(false);
                    writeLog("Порт открыт");
                }
            } catch (RuntimeException e) {
                throw e;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            writeLog("Отсутствует доступный порт");
        }
    }

    private void writeLog(String str) {
        logView.getItems().add(str);
        logView.scrollTo(logView.getItems().size() - 1);
    }

    @FXML
    public void closePort() {
        try {
            modbusMaster.disconnect();
            if (!modbusMaster.isConnected()) {
                buttonClosePort.setDisable(true);
                writeLog("Порт закрыт");
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
