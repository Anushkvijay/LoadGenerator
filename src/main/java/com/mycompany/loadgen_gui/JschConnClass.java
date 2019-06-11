package com.mycompany.loadgen_gui;

import com.jcraft.jsch.*;
import java.io.*;

public class JschConnClass extends Output_ScreenController {
   //Output_ScreenController outputscreen = new Output_ScreenController(); 
    public void connectssh() throws IOException {
        JSch jsch=new JSch();
        try {
            Session session = jsch.getSession("csg", "10.16.0.28", 22);
            session.setPassword("csg123");
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            
            session.connect();
            String command = "su -c 'ls && pwd' - root";
            Channel channel=session.openChannel("exec");
            ((ChannelExec)channel).setCommand(command);
            channel.setInputStream(null);
            ((ChannelExec) channel).setErrStream(System.err);
            ((ChannelExec) channel).setPty(true);
            OutputStream out=channel.getOutputStream();
            InputStream in = new PipedInputStream();
            PipedOutputStream pin = new PipedOutputStream((PipedInputStream) in);
            in=channel.getInputStream();
            channel.connect();
            //System.out.println("Connected");
            String password = "root123";
            out.write((password + "\n").getBytes());
            out.flush();
            byte[] tmp = new byte[1024];
            while (true) {
                while (in.available() > 0) {
                    int i = in.read(tmp, 0, 1024);
                    if (i < 0) break;
                    //outputscreen.showdata(new String(tmp, 0, i));
                    showdata(new String(tmp, 0, i));
                }
                if (channel.isClosed()) {
                     //outputscreen.showdata("Exit status: " + channel.getExitStatus());
                     showdata("Exit status: " + channel.getExitStatus());
                    break;
                }
            }
            channel.disconnect();
            session.disconnect();
            showdata("DONE");
        } catch (JSchException | IOException e) {
            e.printStackTrace();
        }
          
        } 
    }
    
    
//    public void closeconn()
//    {
//        session.disconnect();
//        System.out.println("Disconnected");
//    }

