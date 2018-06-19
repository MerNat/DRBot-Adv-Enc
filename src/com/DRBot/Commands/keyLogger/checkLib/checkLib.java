package com.DRBot.Commands.keyLogger.checkLib;

/**
 *
 * @author MerNat
 */

import java.io.File;


public class checkLib{
    private final File platformFile = new File("lib\\platform.jar");
    private final File jnaFile = new File("lib\\jna.jar");
    
    public boolean checkFiles(){
        return platformFile.exists() && jnaFile.exists();
    }
}
