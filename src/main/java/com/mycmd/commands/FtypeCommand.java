package com.mycmd.commands;

import com.mycmd.Command;
import com.mycmd.ShellContext;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Displays or modifies file types used in file extension associations.
 *
 * <p>Usage: - ftype : Display all file types - ftype filetype : Display open command for file type
 */
public class FtypeCommand implements Command {

    @Override
    public void execute(String[] args, ShellContext context) throws IOException {
        String os = System.getProperty("os.name").toLowerCase();

        if (!os.contains("win")) {
            System.out.println("FTYPE is only available on Windows systems.");
            return;
        }

        try {
            StringBuilder cmdBuilder = new StringBuilder("ftype");
            for (String arg : args) {
                cmdBuilder.append(" ").append(arg);
            }

            Process process = Runtime.getRuntime().exec(cmdBuilder.toString());
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            process.waitFor();

        } catch (Exception e) {
            System.out.println("Error executing ftype: " + e.getMessage());
        }
    }

    @Override
    public String description() {
        return "Displays or modifies file types used in file extension associations.";
    }

    @Override
    public String usage() {
        return "ftype [fileType[=[openCommandString]]]";
    }
}
