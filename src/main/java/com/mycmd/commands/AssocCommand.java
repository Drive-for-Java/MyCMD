package com.mycmd.commands;

import com.mycmd.Command;
import com.mycmd.ShellContext;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Displays or modifies file extension associations.
 *
 * <p>Usage: - assoc : Display all associations - assoc .ext : Display association for extension
 */
public class AssocCommand implements Command {

    @Override
    public void execute(String[] args, ShellContext context) throws IOException {
        String os = System.getProperty("os.name").toLowerCase();

        if (!os.contains("win")) {
            System.out.println("ASSOC is only available on Windows systems.");
            return;
        }

        try {
            StringBuilder cmdBuilder = new StringBuilder("assoc");
            for (String arg : args) {
                cmdBuilder.append(" ").append(arg);
            }

            Process process = Runtime.getRuntime().exec(cmdBuilder.toString());
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            process.waitFor();

        } catch (Exception e) {
            System.out.println("Error executing assoc: " + e.getMessage());
        }
    }

    @Override
    public String description() {
        return "Displays or modifies file extension associations.";
    }

    @Override
    public String usage() {
        return "assoc [.ext[=[fileType]]]";
    }
}
