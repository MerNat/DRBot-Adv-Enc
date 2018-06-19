package com.DRBot.Commands;

import com.DRBot.SecureLine.SecureLine;
import java.io.File;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;

/**
 *
 * @author MerNat
 */

public class delete implements Runnable {
    
    private final String[] fullCommands;
    private String msg;
    private final PrintWriter writer;
    private File tempFile;
    private final SecureLine secureLine;
    
    
    delete(String[] commands,PrintWriter writer,SecureLine secureLine){
        
        this.writer = writer;
        this.secureLine = secureLine;
        fullCommands = new String[commands.length-1];
        
        for(int i = 1;i<commands.length;i++)
            fullCommands[i-1] = commands[i];        
    }
    @Override
    public void run(){
        tempFile = new File(fullCommands[0].replace("\\","\\\\").replace("*",":").replace("_"," "));
        if(tempFile.exists()){
            if(tempFile.isFile()){
                try{
                Files.delete(tempFile.toPath());
                toPrint("[delete] " + tempFile.getPath() + " is deleted.");
                }
                catch(IOException ddff){}
            }
            else{
                try{
                delete(tempFile);
                toPrint("[delete] " + tempFile.getPath() + " is deleted.");
                }
                catch(IOException dif){}
            }
        }
        else{
                toPrint("[delete] The File/Folder " + tempFile.getPath() + " does not exist.");
        }
    }
    private void toPrint(String toPrint){
            this.writer.println(secureLine.encrypt(toPrint));
            this.writer.flush();
    }
    private void delete(File f) throws IOException {
            if (f.isDirectory()) {
                    for (File c : f.listFiles())
                        delete(c);
                            }
                        if (!f.delete())
                            throw new FileNotFoundException();
                                    }
}
