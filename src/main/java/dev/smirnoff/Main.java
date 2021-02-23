package dev.smirnoff;

import com.intelligt.modbus.jlibmodbus.Modbus;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
//        primaryStage.setTitle("FQ Setting");
//        primaryStage.setWidth(700);
//        primaryStage.setHeight(400);
//        InputStream iconStream = getClass().getResourceAsStream("/0x55logo.png");
//        Image image = new Image(iconStream);
//        primaryStage.getIcons().add(image);
//
//
//        Label helloWorldLabel = new Label("Hello world!");
//        Scene primaryScene = new Scene(helloWorldLabel);
//        primaryStage.setScene(primaryScene);
        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = getClass().getResource("/view/index.fxml");
        loader.setLocation(xmlUrl);
        Parent root = loader.load();

        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
