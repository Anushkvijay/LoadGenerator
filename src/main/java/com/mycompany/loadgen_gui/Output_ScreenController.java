package com.mycompany.loadgen_gui;


import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
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
public class Output_ScreenController extends OutputStream implements Initializable {

    @FXML
    private TextArea Out_area;
    final static String newline = "\n";

//    public void showdata(String output_string)
//    {   
//       
//        if (Platform.isFxApplicationThread()) {
//        Out_area.appendText(output_string + newline);
//        } else {
//        Platform.runLater(() -> Out_area.appendText(output_string + newline));
//    } 
     @Override
    public void initialize(URL location, ResourceBundle resources) {
     OutputStream out = new OutputStream(){
       public void write(int b) throws IOException {
        // redirects data to the text area
        Out_area.appendText(String.valueOf((char) b));
        // scrolls the text area to the end of data
        Out_area.positionCaret(Out_area.getText().length());
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        String s = new String(b,off,len);
        Out_area.appendText(s);
        Out_area.positionCaret(Out_area.getText().length());
    }

    @Override
    public void write(byte[] b) throws IOException {
        this.write(b, 0, b.length);
    } 
     };
    
        
    }  


//    @Override
//    public void initialize(URL url, ResourceBundle rb) {
//       
//
//}

    @Override
    public void write(int b) throws IOException {
      
    }
}