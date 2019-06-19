package com.mycompany.loadgen_gui;

import static com.mycompany.loadgen_gui.ValidateIPv4.isValidInet4Address;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FXMLController  implements Initializable {
    
    @FXML
    private Label label;

    @FXML
    private TextField No_call;

    @FXML
    private TextField Concur_call;

    @FXML
    private TextField Dest_IP;

    @FXML
    private TextField Dest_port;

    @FXML
    private TextField Run_Time;
    
    @FXML
    private CheckBox Script_2g;
    
    @FXML
    private CheckBox Script_3g;
    

    
    @FXML
    private void handleButtonAction(ActionEvent event) throws NumberFormatException, IOException {
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("/fxml/Output_Screen.fxml"));
        //System.out.println(getClass().getResource("/fxml/Output_Screen.fxml"));
        Parent output_screen = Loader.load();
        Scene output_scene = new Scene(output_screen);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setTitle("Load Generator");
        try{
        int noofcall = Integer.parseInt(No_call.getText());
        int concurcall = Integer.parseInt(Concur_call.getText());
        int Destport = Integer.parseInt(Dest_port.getText());
        int time = Integer.parseInt(Run_Time.getText());
        String DestIP = Dest_IP.getText();
            if (!isValidInet4Address(DestIP)) {
                throw new IPException(" The IP Address is not valid ");
            }
//        String[] splittedArray = DestIP.split(".");
//        String Env_var = splittedArray [2];
//        System.out.println(Env_var);
        //InetAddress.getByName(DestIP);
            String scriptContent;
            scriptContent = ". ../Env_$5.sh\n" +
"\n" +
"./sipp_2g_93 $3:$4 -sn uac_pcap -i 10.16.0.28 -p 9050 -mp 6030 -inf isfBest_40 -nr -m $1 -l $2 -trace_msg -trace_err -trace_stat";
            
            try (Writer output = new BufferedWriter(new FileWriter("E:\\TempLoadGen.sh"))) {
                output.write(scriptContent);
            }
            
       Thread thread = new Thread(() ->{   
            try {
                JschConnClass connection = new JschConnClass();
                connection.uploadscript();
                connection.connectssh(DestIP,Destport,noofcall,concurcall);
               
            } catch (IOException ex) {
                Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
         thread.start();
          app_stage.setScene(output_scene);
                //app_stage.hide(); //optional
          app_stage.show();
    }
     catch (IPException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("IP Address is not valid");
            alert.setContentText("Please enter a valid IP address");
            alert.showAndWait();
        }}
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        No_call.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*") || !newValue.matches("^-(?!0*\\.?0+$)\\d*\\.?\\d+$")) {
                No_call.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        Concur_call.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*") || !newValue.matches("^-(?!0*\\.?0+$)\\d*\\.?\\d+$")) {
                Concur_call.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        Dest_port.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*") || !newValue.matches("^-(?!0*\\.?0+$)\\d*\\.?\\d+$")) {
                Dest_port.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        Run_Time.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*") || !newValue.matches("^-(?!0*\\.?0+$)\\d*\\.?\\d+$")) {
                Run_Time.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }    
}
class ValidateIPv4 {

    private static final String ipv4_regex
            = "^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\."
            + "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\."
            + "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\."
            + "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";

    private static final Pattern IPv4_PATTERN = Pattern.compile(ipv4_regex);

    public static boolean isValidInet4Address(String ip) {
        if (ip == null) {
            return false;
        }

        Matcher matcher = IPv4_PATTERN.matcher(ip);

        return matcher.matches();
    }
}
class IPException extends Exception {

    public IPException(String s) {
        super(s);
    }
}