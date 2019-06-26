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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
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
    private ToggleGroup toggleGroup;
    
    @FXML
    private ComboBox Run_Time;
    
    @FXML
    private RadioButton Script_2g;
    
    @FXML
    private RadioButton Script_3g;
    
    @FXML
    private CheckBox CheckIri;
    
    ObservableList secondgen = FXCollections.observableArrayList( 
    "93", "50");
    ObservableList thirdgen = FXCollections.observableArrayList( 
     "38", "48", "95","120", "150", "153", "155");
     
    @FXML
    public void runbutton(ActionEvent event) throws NumberFormatException, IOException {
        FXMLLoader Loader = new FXMLLoader();
        
        Loader.setLocation(getClass().getResource("/fxml/Output_Screen.fxml"));
        
        Parent output_screen = Loader.load();
        Scene output_scene = new Scene(output_screen);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
        app_stage.setTitle("Integrated Load Generator v1.1");
      //  output_scene.getWindow().centerOnScreen();
        try{
        int noofcall = Integer.parseInt(No_call.getText());
        int concurcall = Integer.parseInt(Concur_call.getText());
        
        
        String DestIP = Dest_IP.getText();
        
            if (!isValidInet4Address(DestIP)) {
                throw new IPException(" The IP Address is not valid ");
            }
            String scriptContent = null;
            
            if(Run_Time.getValue().toString().equalsIgnoreCase("93"))
            {
                scriptContent = "cd /home/cms/LOADGEN/SIPP_LOADGEN/sipp-1.1rc6/\n" +
                ". ../Env_$4.sh\n" +
                "./sipp_2g_93 $3:5040 -sn uac_pcap -i 10.16.0.28 -p 9050 -mp 6030 -inf isfBest_$4 -nr -m $1 -l $2 -trace_msg -trace_err -trace_stat";
            }
            else if(Run_Time.getValue().toString().equalsIgnoreCase("50"))
            {
                scriptContent = "cd /home/cms/LOADGEN/SIPP_LOADGEN/sipp-1.1rc6/\n" +
                ". ../Env_$4.sh\n" +
                "./sipp_2G_50 $3:5040 -sn uac_pcap -i 10.16.0.28 -p 8060 -mp 4020 -inf isfBest_$4 -r 19 -d 5 -nr -m $1 -l $2 -trace_msg -trace_err -trace_stat";
            }
            else if(Run_Time.getValue().toString().equalsIgnoreCase("38"))
            {
                scriptContent = "cd /home/cms/LOADGEN/SIPP_LOADGEN/sipp-1.1rc6/\n" +
                ". ../Env_$4.sh\n" +
                "";
            }
            else if(Run_Time.getValue().toString().equalsIgnoreCase("48"))
            {
                scriptContent = "cd /home/cms/LOADGEN/SIPP_LOADGEN/sipp-1.1rc6/\n" +
                ". ../Env_$4.sh\n" +
                "";
            }
            else if(Run_Time.getValue().toString().equalsIgnoreCase("95"))
            {
                scriptContent = "cd /home/cms/LOADGEN/SIPP_LOADGEN/sipp-1.1rc6/\n" +
                ". ../Env_$4.sh\n" +
                "./sipp_3g_95 10.16.0.25:5040 -sn uac_pcap -i 10.16.0.28 -p 8040 -mp 4000 -inf isfBest_$4 -r 60 -d 10000 -nr -m 2 -l 2 -trace_msg -trace_err -trace_stat";
            } 
            else if(Run_Time.getValue().toString().equalsIgnoreCase("120"))
            {
                scriptContent = "cd /home/cms/LOADGEN/SIPP_LOADGEN/sipp-1.1rc6/\n" +
                ". ../Env_$4.sh\n" +
                "";
            } 
            else if(Run_Time.getValue().toString().equalsIgnoreCase("150"))
            {
                scriptContent = "cd /home/cms/LOADGEN/SIPP_LOADGEN/sipp-1.1rc6/\n" +
                ". ../Env_$4.sh\n" +
                "./sipp_3g_150 $3:5040 -sn uac_pcap -i 10.16.0.28 -p 9042 -mp 7000 -inf isfBest_$4 -r 10 -d 10000 -nr -m $1 -l $2 -trace_msg -trace_err -trace_stat";
            } 
            else if(Run_Time.getValue().toString().equalsIgnoreCase("153"))
            {
                scriptContent = "cd /home/cms/LOADGEN/SIPP_LOADGEN/sipp-1.1rc6/\n" +
                ". ../Env_$4.sh\n" +
                "";
            } 
            else if(Run_Time.getValue().toString().equalsIgnoreCase("155"))
            {
                scriptContent = "cd /home/cms/LOADGEN/SIPP_LOADGEN/sipp-1.1rc6/\n" +
                ". ../Env_$4.sh\n" +
                "";
            }
            try (Writer output = new BufferedWriter(new FileWriter("E:\\TempLoadGen.sh"))) {
                output.write(scriptContent);
            }
       String[] splittedArray = DestIP.split("\\.");
       String Env_var = splittedArray[3];   
       JschConnClass connection = new JschConnClass();
       
       if (CheckIri.isSelected()==true)
       {
        Thread iriconnectionthread = new Thread(() ->{   
           
               connection.setiriscript(Env_var);
          
        });    
        iriconnectionthread.start();
       }
       else
       {
           Thread iriconnectionthread = new Thread(() ->{   
           
               connection.killiriscript(Env_var);
          
        });    
        iriconnectionthread.start();
       }
       Thread connectionthread = new Thread(() ->{   
            try {
                
               connection.uploadscript();
               connection.connectssh(DestIP,noofcall,concurcall,Env_var);
                
            } 
               catch (IOException ex) {
                Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
         connectionthread.start();
          app_stage.setScene(output_scene);
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
//        
   
    Run_Time.setItems(secondgen);
 
    Script_2g.selectedProperty().addListener((ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected) -> {
        if (isNowSelected) {
            Run_Time.setItems(secondgen);
        }
        });
    Script_3g.selectedProperty().addListener((ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected) -> {
        if (isNowSelected) {
            Run_Time.setItems(thirdgen);
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