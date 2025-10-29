package com.mycmd.commands;

import com.mycmd.Command;
import com.mycmd.ShellContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Traces the route packets take to a network host.
 * 
 * Usage:
 * - tracert hostname    : Trace route to host
 */
public class TracertCommand implements Command {
    
    @Override
    public void execute(String[] args, ShellContext context) throws IOException {
        if (args.length == 0) {
            System.out.println("Usage: tracert [-d] [-h maximum_hops] [-w timeout] target_name");
            System.out.println("\nTraces the route packets take to a network host.");
            System.out.println("\nOptions:");
            System.out.println("  -d                 Do not resolve addresses to hostnames.");
            System.out.println("  -h maximum_hops    Maximum number of hops to search for target.");
            System.out.println("  -w timeout         Wait timeout milliseconds for each reply.");
            return;
        }
        
        try {
            StringBuilder cmdBuilder = new StringBuilder();
            String os = System.getProperty("os.name").toLowerCase();
            
            if (os.contains("win")) {
                cmdBuilder.append("tracert");
            } else {
                cmdBuilder.append("traceroute");
            }
            
            for (String arg : args) {
                cmdBuilder.append(" ").append(arg);
            }
            
            ProcessBuilder pb = new ProcessBuilder();
            
            if (os.contains("win")) {
                pb.command("cmd.exe", "/c", cmdBuilder.toString());
            } else {
                pb.command("sh", "-c", cmdBuilder.toString());
            }
            
            Process process = pb.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            
            process.waitFor();
            
        } catch (Exception e) {
            System.out.println("Error executing tracert: " + e.getMessage());
        }
    }
    
    @Override
    public String description() {
        return "Traces the route packets take to a network host.";
    }
    
    @Override
    public String usage() {
        return "tracert [-d] [-h maximum_hops] target_name";
    }
}
