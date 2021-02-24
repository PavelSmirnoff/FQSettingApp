package dev.smirnoff.model;

import com.intelligt.modbus.jlibmodbus.master.ModbusMaster;
import com.intelligt.modbus.jlibmodbus.master.ModbusMasterFactory;
import com.intelligt.modbus.jlibmodbus.serial.SerialParameters;
import jssc.SerialPortList;

/**
 * @author pavelsmirnov
 */
public class Port {

    private String[] portList;

    private SerialParameters serialParameters;

    public Port() {
        this.setPortList();
    }

    public SerialParameters getSerialParameters() {
        return serialParameters;
    }

    public void setSerialParameters(SerialParameters serialParameters) {
        this.serialParameters = serialParameters;
    }

    public String[] getPortList() {
        return portList;
    }

    public void setPortList() {
        this.portList = SerialPortList.getPortNames();
    }

}
