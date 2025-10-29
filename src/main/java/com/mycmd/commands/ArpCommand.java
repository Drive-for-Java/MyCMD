package com.mycmd.commands;

import com.mycmd.Command;
import com.mycmd.ShellContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Displays and modifies the IP-to-Physical address translation tables (ARP cache).
 * 
 * Usage:
 * - arp -a           : Display ARP cache
 */
public class ArpCommand implements Command {
    
    @Override
    public void execute(String[] args, ShellContext context) throws IOException {
        try {
            StringBuilder cmdBuilder = new StringBuilder("arp");
            for (String arg : args) {
                cmdBuilder.append(" ").append(arg);
            }
            
            // Default to -a if no args provided
            if (args.length == 0) {
                cmdBuilder.append(" -a");
            }
            
            ProcessBuilder pb = new ProcessBuilder();
            String os = System.getProperty("os.name").toLowerCase();
            
            if (os.contains("win")) {
                pb.command("cmd.exe", "/c", cmdBuilder.toString());
            } else {
                pb.command("sh", "-c", cmdBuilder.toString());
            }
            
            Process process = pb.start();
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
            System.out.println("Error executing arp: " + e.getMessage());
        }
    }
    
    @Override
    public String description() {
        return "Displays and modifies the IP-to-Physical address translation tables.";
    }
    
    @Override
    public String usage() {
        return "arp [-a] [-d ip_addr] [-s ip_addr eth_addr]";
    }
}
