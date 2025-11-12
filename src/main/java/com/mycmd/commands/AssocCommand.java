package com.mycmd.commands;

import com.mycmd.Command;
import com.mycmd.ShellContext;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

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

            List<String> command = new ArrayList<>();
            command.add("cmd.exe");
            command.add("/c");
            command.add("assoc");
            if (args != null && args.length > 0) {
                command.addAll(Arrays.asList(args));
            }

            ProcessBuilder pb = new ProcessBuilder(command);
            Process process = pb.start();

            Thread errorGobbler = new Thread(
                    () -> {
                        try (BufferedReader errReader =
                                new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
                            String errLine;
                            while ((errLine = errReader.readLine()) != null) {
                                System.err.println(errLine);
                            }
                        } catch (IOException e) {

                            System.err.println("Error reading process error stream: " + e.getMessage());
                        }
                    },
                    "assoc-error-gobbler");
            errorGobbler.setDaemon(true);
            errorGobbler.start();

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            }

            boolean finished;
            try {
                finished = process.waitFor(30, TimeUnit.SECONDS);
            } catch (InterruptedException ie) {

                Thread.currentThread().interrupt();
                process.destroyForcibly();
                throw new IOException("Interrupted while waiting for assoc process", ie);
            }

            if (!finished) {
                process.destroyForcibly();
                throw new IOException("Command timed out after 30 seconds");
            }

            try {
                errorGobbler.join(1000);
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            }

           } catch (IOException e) {
         throw e;
         } catch (Exception e) {
        throw new IOException("Error executing assoc", e);
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
