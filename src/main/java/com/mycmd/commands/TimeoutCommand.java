package com.mycmd.commands;

import com.mycmd.Command;
import com.mycmd.ShellContext;

import java.io.IOException;

/**
 * Delays execution for a specified time period.
 * 
 * Usage:
 * - timeout /T 10       : Wait 10 seconds
 * - timeout 5           : Wait 5 seconds
 */
public class TimeoutCommand implements Command {
    
    @Override
    public void execute(String[] args, ShellContext context) throws IOException {
        if (args.length == 0) {
            System.out.println("TIMEOUT [/T] timeout [/NOBREAK]");
            System.out.println("\nDescription:");
            System.out.println("    This utility accepts a timeout parameter to wait for the specified");
            System.out.println("    time period (in seconds) or until any key is pressed. It also accepts");
            System.out.println("    a parameter to ignore the key press.");
            System.out.println("\nParameter List:");
            System.out.println("    /T        timeout       Specifies the number of seconds to wait.");
            System.out.println("                            Valid range is -1 to 99999 seconds.");
            System.out.println("    /NOBREAK                Ignore key presses and wait specified time.");
            return;
        }
        
        int seconds = 0;
        boolean noBreak = false;
        
        for (int i = 0; i < args.length; i++) {
            String arg = args[i].toUpperCase();
            
            if (arg.equals("/T") && i + 1 < args.length) {
                try {
                    seconds = Integer.parseInt(args[++i]);
                } catch (NumberFormatException e) {
                    System.out.println("ERROR: Invalid argument/option - '" + args[i] + "'.");
                    return;
                }
            } else if (arg.equals("/NOBREAK")) {
                noBreak = true;
            } else {
                try {
                    seconds = Integer.parseInt(arg);
                } catch (NumberFormatException e) {
                    System.out.println("ERROR: Invalid argument/option - '" + arg + "'.");
                    return;
                }
            }
        }
        
        if (seconds < 0) {
            System.out.println("Waiting forever - press any key to continue...");
            try {
                System.in.read();
            } catch (IOException e) {
                // Ignore
            }
        } else {
            System.out.println("Waiting for " + seconds + " seconds, press a key to continue ...");
            
            try {
                long startTime = System.currentTimeMillis();
                long endTime = startTime + (seconds * 1000L);
                
                while (System.currentTimeMillis() < endTime) {
                    if (!noBreak && System.in.available() > 0) {
                        System.in.read();
                        break;
                    }
                    Thread.sleep(100);
                }
            } catch (InterruptedException | IOException e) {
                System.out.println("Timeout interrupted.");
            }
        }
    }
    
    @Override
    public String description() {
        return "Delays execution for a specified time period.";
    }
    
    @Override
    public String usage() {
        return "timeout [/T] seconds [/NOBREAK]";
    }
}
