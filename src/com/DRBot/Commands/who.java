package com.DRBot.Commands;

import com.DRBot.SecureLine.SecureLine;
import java.io.PrintWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author MerNat
 */

public class who implements Runnable{
    
    private final ArrayList<String> myList;
    private final PrintWriter writer;
    private String toSend;
    private final SecureLine secureLine;
    
    who(PrintWriter writer,SecureLine secureLine){
        this.secureLine = secureLine;
        this.toSend = "";
        myList = new ArrayList<>();
        this.writer = writer;
    }
    
    @Override
    public void run(){
        try {
            try (Scanner input = new Scanner(Paths.get("logTo"))) {
                while(input.hasNext()){
                    myList.add(input.next());
                }
                input.close();
                int len = myList.size()-1;
            if(len<=10){
                for(int j = len;j>=0;j--){
                   toSend = toSend + "["+myList.get(j)+"] -- ";
                }
                toPrint(toSend);
            }
            else{
                for(int j = len;j>=(len-10);j--){
                    toSend = toSend + "["+myList.get(j)+"] -- ";
                }
                toPrint(toSend);
                }
            }      
        } catch (IOException ex) {
            //Error opening file.
        }
    }
    private void toPrint(String toPrint){
        this.writer.println(secureLine.encrypt(toPrint));
        this.writer.flush();
    }
    
}
