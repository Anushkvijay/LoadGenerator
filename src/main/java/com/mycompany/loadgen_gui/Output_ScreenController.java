package com.mycompany.loadgen_gui;


import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Anushk
 */
public class Output_ScreenController  implements Initializable {

   
    
    @FXML
    private TextArea Out_area;
    final static String newline = "\n";
    boolean updated;
   
    @FXML
    public void NewLoadbutton(ActionEvent event) throws IOException
    {
        FXMLLoader Loader = new FXMLLoader();
        
        Loader.setLocation(getClass().getResource("/fxml/FirstScreen.fxml"));
        
        Parent first_screen = Loader.load();
        Scene first_scene = new Scene(first_screen);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
        app_stage.setTitle("Integrated Load Generator v1.1");
        app_stage.centerOnScreen();
        app_stage.setScene(first_scene);
        app_stage.show();
    }
    public void showdata(String output_string)
    {   
     Out_area.setWrapText(false);
     if (Platform.isFxApplicationThread()) {
        Out_area.appendText(output_string);
        } else {
     Platform.runLater(() -> Out_area.appendText(output_string));
    } 
     
    }
    


public void redirectOutputStream() {
    OutputStream out = new OutputStream() {

        private final StringBuilder sb = new StringBuilder();

        @Override
        public void write(int b) throws IOException {
            if (b == '\r') {
                return;
            }
            if (b == '\n') {
                final String tmp = sb.toString() + "\n";
                showdata(tmp);
                //updateText(text);
                sb.setLength(0);
            } else {
                sb.append((char) b);
            }
        }
    };
    System.setOut(new PrintStream(out, true));
    System.setErr(new PrintStream(out, true));
}
    @Override
    public void initialize(URL location, ResourceBundle resources) {
       redirectOutputStream();
    }
}