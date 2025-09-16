package com.mycmd.commands;

import com.mycmd.Command;
import com.mycmd.ShellContext;
import java.io.*;

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
