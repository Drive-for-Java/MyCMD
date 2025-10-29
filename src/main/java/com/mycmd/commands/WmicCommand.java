package com.mycmd.commands;

import com.mycmd.Command;
import com.mycmd.ShellContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Windows Management Instrumentation Command-line interface.
 * Simplified implementation that wraps actual wmic calls.
 * 
 * Usage:
 * - wmic [query]    : Execute WMIC query
 */
public class WmicCommand implements Command {
    
    @Override
    public void execute(String[] args, ShellContext context) throws IOException {
        if (args.length == 0) {
            System.out.println("WMIC - Windows Management Instrumentation Command-line");
            System.out.println("Usage: wmic [global switches] <command>");
            System.out.println("\nCommon commands:");
            System.out.println("  wmic cpu get name");
            System.out.println("  wmic bios get serialnumber");
            System.out.println("  wmic os get caption");
            return;
        }
        
        // Check if wmic is available on the system
        String os = System.getProperty("os.name").toLowerCase();
        if (!os.contains("win")) {
            System.out.println("WMIC is only available on Windows systems.");
            return;
        }
        
        try {
            // Build the wmic command
            StringBuilder cmdBuilder = new StringBuilder("wmic");
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
            System.out.println("Error executing WMIC command: " + e.getMessage());
        }
    }
    
    @Override
    public String description() {
        return "Windows Management Instrumentation Command-line interface.";
    }
    
    @Override
    public String usage() {
        return "wmic [query]";
    }
}
