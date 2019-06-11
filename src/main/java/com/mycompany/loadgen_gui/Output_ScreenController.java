package com.mycompany.loadgen_gui;


import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

/**
 * FXML Controller class
 *
 * @author Anushk
 */
public class Output_ScreenController implements Initializable {

    @FXML
    private TextArea Out_area;
    final static String newline = "\n";

    public void showdata(String output_string)
    {   
       
        if (Platform.isFxApplicationThread()) {
        Out_area.appendText(output_string + newline);
        } else {
        Platform.runLater(() -> Out_area.appendText(output_string + newline));
    }
        
        
    }
//  @FXML 
//  private void showTextAction(ActionEvent event) {
//    
//  }
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
