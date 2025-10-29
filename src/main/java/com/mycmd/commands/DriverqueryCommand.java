package com.mycmd.commands;

import com.mycmd.Command;
import com.mycmd.ShellContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Displays a list of all installed device drivers.
 * 
 * Usage:
 * - driverquery        : Display list of drivers
 * - driverquery /v     : Display verbose information
 */
public class DriverqueryCommand implements Command {
    
    @Override
    public void execute(String[] args, ShellContext context) throws IOException {
        String os = System.getProperty("os.name").toLowerCase();
        
        if (!os.contains("win")) {
            System.out.println("driverquery is only available on Windows systems.");
            return;
        }
        
        try {
            StringBuilder cmdBuilder = new StringBuilder("driverquery");
            for (String arg : args) {
                cmdBuilder.append(" ").append(arg);
            }
            
            Process process = Runtime.getRuntime().exec(cmdBuilder.toString());
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                System.out.println("Command exited with code: " + exitCode);
            }
            
        } catch (Exception e) {
            System.out.println("Error executing driverquery: " + e.getMessage());
        }
    }
    
    @Override
    public String description() {
        return "Displays a list of all installed device drivers.";
    }
    
    @Override
    public String usage() {
        return "driverquery [/v]";
    }
}
