package com.mycompany.loadgen_gui;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
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
       
        
        stage.setTitle("Integrated Load Generator v1.1");
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest((WindowEvent event1) -> {
            System.out.println("Closing");
            Platform.exit();
            System.exit(0);
     

       
    });
    }


    public static void main(String[] args) {
        launch(args);
    }

}
