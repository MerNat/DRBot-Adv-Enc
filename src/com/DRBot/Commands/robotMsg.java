package com.DRBot.Commands;

import java.io.IOException;

/**
 *
 * @author MerNat
 */

public class robotMsg implements Runnable{
    private String fullMsg = "";
    private ProcessBuilder pB;
    private Process ps;
    
    
    robotMsg(String[] commands){
            for(int i=1;i<commands.length;i++){
                    fullMsg = fullMsg + commands[i] + " ";
                }
    } 
    
    @Override
    public void run(){
        pB = new ProcessBuilder("msg","*",fullMsg);
        try {
            ps = pB.start();
            ps.waitFor();
            ps.destroyForcibly();
        } catch (IOException ex) {}
            catch(InterruptedException ioEx){}
    }

}
