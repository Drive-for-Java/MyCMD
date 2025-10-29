package com.mycmd.commands;

import com.mycmd.Command;
import com.mycmd.ShellContext;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Displays output one screen at a time.
 * 
 * Usage:
 * - more filename         : Display file content page by page
 * - command | more        : Paginate command output
 */
public class MoreCommand implements Command {
    
    private static final int LINES_PER_PAGE = 20;
    
    @Override
    public void execute(String[] args, ShellContext context) throws IOException {
        if (args.length == 0) {
            System.out.println("Displays output one screen at a time.");
            System.out.println("\nMORE [/E [/C] [/P] [/S] [/Tn] [+n]] < [drive:][path]filename");
            System.out.println("command-name | MORE [/E [/C] [/P] [/S] [/Tn] [+n]]");
            System.out.println("\nExample: more filename.txt");
            return;
        }
        
        String filename = args[0];
        java.io.File file = new java.io.File(filename);
        
        if (!file.isAbsolute()) {
            file = new java.io.File(context.getCurrentDir(), filename);
        }
        
        if (!file.exists()) {
            System.out.println("Cannot access " + filename);
            return;
        }
        
        if (!file.isFile()) {
            System.out.println(filename + " is not a file.");
            return;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            int lineCount = 0;
            
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                lineCount++;
                
                if (lineCount % LINES_PER_PAGE == 0) {
                    System.out.print("-- More -- ");
                    System.in.read(); // Wait for user input
                    // Clear the input buffer
                    while (System.in.available() > 0) {
                        System.in.read();
                    }
                }
            }
        }
    }
    
    @Override
    public String description() {
        return "Displays output one screen at a time.";
    }
    
    @Override
    public String usage() {
        return "more [filename]";
    }
}
