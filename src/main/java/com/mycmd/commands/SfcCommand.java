package com.mycmd.commands;

import com.mycmd.Command;
import com.mycmd.ShellContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * System File Checker - Scans and verifies system files.
 * 
 * Usage:
 * - sfc /scannow    : Scans all protected system files
 * - sfc /verifyonly : Scans but doesn't repair
 */
public class SfcCommand implements Command {
    
    @Override
    public void execute(String[] args, ShellContext context) throws IOException {
        String os = System.getProperty("os.name").toLowerCase();
        
        if (!os.contains("win")) {
            System.out.println("SFC (System File Checker) is only available on Windows systems.");
            return;
        }
        
        if (args.length == 0) {
            System.out.println("Microsoft Windows System File Checker");
            System.out.println("\nUsage:");
            System.out.println("    sfc [/scannow] [/verifyonly] [/scanfile=<file>]");
            System.out.println("\nOptions:");
            System.out.println("    /scannow      - Scans integrity of all protected system files");
            System.out.println("    /verifyonly   - Scans integrity but does not repair");
            System.out.println("    /scanfile     - Scans integrity of specific file");
            System.out.println("\nNote: Administrator privileges required.");
            return;
        }
        
        try {
            StringBuilder cmdBuilder = new StringBuilder("sfc");
            for (String arg : args) {
                cmdBuilder.append(" ").append(arg);
            }
            
            Process process = Runtime.getRuntime().exec(cmdBuilder.toString());
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            
            while ((line = errorReader.readLine()) != null) {
                System.err.println(line);
            }
            
            process.waitFor();
            
        } catch (Exception e) {
            System.out.println("Error executing sfc: " + e.getMessage());
            System.out.println("Administrator privileges may be required.");
        }
    }
    
    @Override
    public String description() {
        return "System File Checker - Scans and verifies system files.";
    }
    
    @Override
    public String usage() {
        return "sfc [/scannow] [/verifyonly] [/scanfile=<file>]";
    }
}
