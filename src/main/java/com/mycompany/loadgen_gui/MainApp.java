package com.mycompany.loadgen_gui;

import java.io.IOException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/FirstScreen.fxml"));
        
        Scene scene = new Scene(root);
       // scene.getStylesheets().add("/styles/Styles.css");
        
        stage.setTitle("Load Generator GUI");
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest((WindowEvent event1) -> {
            System.out.println("Closing");
            Platform.exit();
            System.exit(0);
//        JschConnClass closer = new JschConnClass();
//        closer.closeconn();
       
    });
    }


    public static void main(String[] args) {
        launch(args);
    }

}
