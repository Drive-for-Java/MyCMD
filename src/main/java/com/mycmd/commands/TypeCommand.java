package com.mycmd.commands;

import com.mycmd.Command;
import com.mycmd.ShellContext;
import java.io.*;

/**
 * Displays the contents of a text file to the console.
 * 
 * This command reads and displays the contents of a specified text file
 * line by line. The file path is resolved relative to the current working
 * directory. It's similar to the Unix cat command or Windows type command.
 * 
 * Usage: type filename
 * 
 * The command verifies that the specified path exists and is a regular file
 * before attempting to read it. It uses BufferedReader for efficient line-by-line
 * reading and properly closes the file after reading.
 * 
 * Note: Best suited for text files. Binary files may produce garbled output.
 */
public class TypeCommand implements Command {
    @Override
    public void execute(String[] args, ShellContext context) {
        if (args.length == 0) {
            System.out.println("Usage: type <file_name>");
            return;
        }
        File file = new File(context.getCurrentDir(), args[0]);
        if (!file.exists() || !file.isFile()) {
            System.out.println("File not found.");
            return;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
}
