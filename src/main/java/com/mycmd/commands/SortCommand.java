package com.mycmd.commands;

import com.mycmd.Command;
import com.mycmd.ShellContext;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Sorts the contents of a text file.
 * 
 * Usage:
 * - sort filename         : Sort file contents
 * - sort /R filename      : Reverse sort
 */
public class SortCommand implements Command {
    
    @Override
    public void execute(String[] args, ShellContext context) throws IOException {
        if (args.length == 0) {
            System.out.println("Sorts input and writes results to output.");
            System.out.println("\nSORT [/R] [/+n] [/M kilobytes] [/L locale] [/REC recordbytes]");
            System.out.println("  [[drive1:][path1]filename1] [/T [drive2:][path2]]");
            System.out.println("  [/O [drive3:][path3]filename3]");
            System.out.println("\n  /R         Reverses the sort order");
            System.out.println("  /+n        Specifies the character number to begin each comparison");
            System.out.println("  filename1  Specifies the file to be sorted");
            return;
        }
        
        boolean reverse = false;
        int argIndex = 0;
        
        // Parse flags
        while (argIndex < args.length && args[argIndex].startsWith("/")) {
            String flag = args[argIndex].toUpperCase();
            if (flag.equals("/R")) reverse = true;
            argIndex++;
        }
        
        if (argIndex >= args.length) {
            System.out.println("Missing filename.");
            return;
        }
        
        String filename = args[argIndex];
        java.io.File file = new java.io.File(filename);
        
        if (!file.isAbsolute()) {
            file = new java.io.File(context.getCurrentDir(), filename);
        }
        
        if (!file.exists()) {
            System.out.println("File not found - " + filename);
            return;
        }
        
        List<String> lines = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }
        
        Collections.sort(lines);
        
        if (reverse) {
            Collections.reverse(lines);
        }
        
        for (String line : lines) {
            System.out.println(line);
        }
    }
    
    @Override
    public String description() {
        return "Sorts the contents of a text file.";
    }
    
    @Override
    public String usage() {
        return "sort [/R] filename";
    }
}
