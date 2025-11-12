package com.mycmd.commands;

import com.mycmd.Command;
import com.mycmd.ShellContext;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Searches for a text string in a file or files.
 *
 * <p>Usage: - find "string" filename : Search for string in file - find /I "string" file :
 * Case-insensitive search
 */
public class FindCommand implements Command {

    @Override
    public void execute(String[] args, ShellContext context) throws IOException {
        if (args.length < 2) {
            System.out.println("Searches for a text string in a file or files.");
            System.out.println("\nFIND [/V] [/C] [/N] [/I] [/OFF[LINE]] \"string\" [[drive:][path]filename[ ...]]");
            System.out.println("\n  /V         Displays all lines NOT containing the specified string.");
            System.out.println("  /C         Displays only the count of lines containing the string.");
            System.out.println("  /N         Displays line numbers with the displayed lines.");
            System.out.println("  /I         Ignores the case of characters when searching for the string.");
            System.out.println("  \"string\"   Specifies the text string to find.");
            System.out.println("  [drive:][path]filename");
            System.out.println("             Specifies a file or files to search.");
            return;
        }

        boolean caseInsensitive = false;
        boolean showLineNumbers = false;
        boolean countOnly = false;
        boolean invertMatch = false;
        int argIndex = 0;

        // Parse flags
        while (argIndex < args.length && args[argIndex].startsWith("/")) {
            String flag = args[argIndex].toUpperCase();
            if (flag.equals("/I")) caseInsensitive = true;
            else if (flag.equals("/N")) showLineNumbers = true;
            else if (flag.equals("/C")) countOnly = true;
            else if (flag.equals("/V")) invertMatch = true;
            argIndex++;
        }

        if (argIndex >= args.length) {
            System.out.println("Missing search string.");
            return;
        }

        String searchString = args[argIndex++].replace("\"", "");

        if (argIndex >= args.length) {
            System.out.println("Missing filename.");
            return;
        }

        // Process files
        for (int i = argIndex; i < args.length; i++) {
            String filename = args[i];
            java.io.File file = new java.io.File(filename);

            if (!file.isAbsolute()) {
                file = new java.io.File(context.getCurrentDir(), filename);
            }

            if (!file.exists()) {
                System.out.println("File not found - " + filename);
                continue;
            }

            searchInFile(file, searchString, caseInsensitive, showLineNumbers, countOnly, invertMatch);
        }
    }

    private void searchInFile(
            java.io.File file,
            String searchString,
            boolean caseInsensitive,
            boolean showLineNumbers,
            boolean countOnly,
            boolean invertMatch)
            throws IOException {
        System.out.println("\n---------- " + file.getName().toUpperCase());

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            int lineNumber = 0;
            int matchCount = 0;

            while ((line = reader.readLine()) != null) {
                lineNumber++;
                String compareLine = caseInsensitive ? line.toLowerCase() : line;
                String compareString = caseInsensitive ? searchString.toLowerCase() : searchString;

                boolean matches = compareLine.contains(compareString);
                if (invertMatch) matches = !matches;

                if (matches) {
                    matchCount++;
                    if (!countOnly) {
                        if (showLineNumbers) {
                            System.out.println("[" + lineNumber + "]" + line);
                        } else {
                            System.out.println(line);
                        }
                    }
                }
            }

            if (countOnly) {
                System.out.println("Count: " + matchCount);
            }
        }
    }

    @Override
    public String description() {
        return "Searches for a text string in a file or files.";
    }

    @Override
    public String usage() {
        return "find [/I] [/N] [/C] \"string\" filename";
    }
}
