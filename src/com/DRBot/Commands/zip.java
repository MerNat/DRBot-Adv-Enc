package com.DRBot.Commands;

import com.DRBot.SecureLine.SecureLine;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

/**
 *
 * Usage: >zip [zipPath] [destPath]
 * @author MerNat
 */

public class zip implements Runnable {
    private final String[] myCommand;
    private final String[] fullComm;
    private final String zipPath,destPath;
    private File destiFile;
    private final PrintWriter writer;
    private static final int BUFFER_SIZE = 1024;
    private ZipInputStream zipIn;
    private ZipEntry entry;
    private final SecureLine secureLine;
    
   zip(String[] myCommand,PrintWriter writer,SecureLine secureLine){
       this.myCommand = myCommand;
       this.writer = writer;
       this.secureLine = secureLine;
       fullComm = new String[myCommand.length-1];
       for(int i=1;i<myCommand.length;i++){
           fullComm[i-1] = myCommand[i]; 
       }
       zipPath = fullComm[0].replace("*",":").replace("_"," ");
       if(fullComm[1] == null || fullComm[1].equals("null")){
           destPath = "null";
       }
       else{
       destPath = fullComm[1].replace("*",":").replace("_"," ");
       }
   }
    @Override
   public void run(){
            extractFolder(zipPath,destPath);
   }
   private void toPrint(String toPrint){
        writer.println(secureLine.encrypt(toPrint));
        writer.flush();
    }
   public void extractFolder(String zipFile,String destPath) 
{
        try {
            //System.out.println(zipFile);
            int BUFFER = 2048;
            File file = new File(zipFile);
            
            ZipFile zip = new ZipFile(file);
            if(destPath.equals("null")){
                String newPath = zipFile.substring(0, zipFile.length() - 4);
                destiFile = new File(newPath);
            }
            else{
                destiFile = new File(destPath);
            }
            if(!destiFile.exists() || !destiFile.isDirectory()){
                destiFile.mkdir();
            }
            
            //new File(newPath).mkdir(); //i need to change the path to where it extracts .....
            Enumeration zipFileEntries = zip.entries();
            
            // Process each entry
            while (zipFileEntries.hasMoreElements())
            {
                // grab a zip file entry
                entry = (ZipEntry) zipFileEntries.nextElement();
                String currentEntry = entry.getName();
                File destFile = new File(destiFile, currentEntry);
                //destFile = new File(newPath, destFile.getName());
                File destinationParent = destFile.getParentFile();
                
                // create the parent directory structure if needed
                destinationParent.mkdirs();
                
                if (!entry.isDirectory())
                {
                    BufferedInputStream is = new BufferedInputStream(zip
                            .getInputStream(entry));
                    int currentByte;
                    // establish buffer for writing file
                    byte data[] = new byte[BUFFER];
                    
                    // write the current file to disk
                    FileOutputStream fos = new FileOutputStream(destFile);
                    BufferedOutputStream dest = new BufferedOutputStream(fos,
                            BUFFER);
                    
                    // read and write until last byte is encountered
                    while ((currentByte = is.read(data, 0, BUFFER)) != -1) {
                        dest.write(data, 0, currentByte);
                    }
                    dest.flush();
                    dest.close();
                    is.close();
                }
                
                if (currentEntry.endsWith(".zip"))
                {
                    // found a zip file, try to open
                    extractFolder(destFile.getAbsolutePath(),destPath);
                }
            }
            toPrint("[zip] Success.");
        } catch (IOException ex) {
            toPrint("[zip] Extract Error.");
        }
    }
}
