package com.mycompany.loadgen_gui;

import com.jcraft.jsch.*;
import java.io.*;

public class JschConnClass  {
   Output_ScreenController outputscreen = new Output_ScreenController(); 
    public void connectssh(String ipadd,int noofcall,int concurcall,String Env_var ) throws IOException {
        JSch jsch=new JSch();
        try {
            Session session = jsch.getSession("csg", "10.16.0.28", 22);
            session.setPassword("csg123");
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            
            session.connect();
            String command = "su -c 'cd /tmp && . ./TempTempLoadGen.sh " + noofcall + " " + concurcall + " " + ipadd + " " + Env_var + " '";
            Channel channel=session.openChannel("exec");
            ((ChannelExec)channel).setCommand(command);
            channel.setInputStream(null);
            ((ChannelExec) channel).setErrStream(System.err);
            ((ChannelExec) channel).setPty(true);
            OutputStream out=channel.getOutputStream();
            InputStream in = new PipedInputStream();
            PipedOutputStream pin = new PipedOutputStream((PipedInputStream) in);
            in=channel.getInputStream();
            
            
            
            String password = "root123";
            channel.connect();
            out.write((password + "\n").getBytes());
            out.flush();
            byte[] tmp = new byte[1024];
            
            while (true) {
                while (in.available() > 0) {
                    int i = in.read(tmp, 0, 1024);
                    if (i < 0) break;
                    System.out.println(new String(tmp, 0, i));
                }
                if (channel.isClosed()) {
                     outputscreen.showdata("Exit status: " + channel.getExitStatus());
                     System.out.println("Exit status: " + channel.getExitStatus());
                    break;
                }
                try{Thread.sleep(1000);}catch(Exception ee){}
            }
            channel.disconnect();
            session.disconnect();
            //System.out.println("DONE");
        } catch (JSchException | IOException e) {
            e.printStackTrace();
        }
          
        } 
    public void uploadscript()
    {   
       try {
           JSch jsch=new JSch();
           Session session = jsch.getSession("csg", "10.16.0.28", 22);
           session.setPassword("csg123");
           java.util.Properties config = new java.util.Properties();
           config.put("StrictHostKeyChecking", "no");
           session.setConfig(config);
           session.connect();
           Channel channel = session.openChannel("sftp");
           channel.connect();
           
           ChannelSftp sftp = (ChannelSftp) channel;
           
           sftp.cd("/tmp");
           sftp.put("E:/TempLoadGen.sh", "TempTempLoadGen.sh",sftp.OVERWRITE);
           Boolean success = true;

     if(success){
       // The file has been uploaded succesfully
     }
     sftp.exit();
     channel.disconnect();
     session.disconnect();
        }
    catch (JSchException | SftpException e) {
    System.out.println(e.getMessage());
}
    }
    
    
    
    
    
    
    
    }
    
    

