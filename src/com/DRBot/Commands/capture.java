package com.DRBot.Commands;

import com.DRBot.SecureLine.SecureLine;
import java.io.PrintWriter;
import java.net.Inet4Address;



public class capture extends get{
        
    capture(String[] commands,PrintWriter writer,Inet4Address inetServer,String botName,SecureLine secureLine){
        super(commands[1],inetServer,writer,botName,secureLine);
    }
}
