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
public class Output_ScreenController  implements Initializable {

    @FXML
    private TextArea Out_area;
    final static String newline = "\n";

    public void showdata(String output_string)
    {   
       
       if (Platform.isFxApplicationThread()) {
        Out_area.appendText(output_string);
        } else {
     Platform.runLater(() -> Out_area.appendText(output_string + newline));
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