package com.mycmd.commands;

import com.mycmd.Command;
import com.mycmd.ShellContext;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Compares two files or sets of files and displays the differences.
 *
 * <p>Usage: - fc file1 file2 : Compare two files - fc /B file1 file2 : Binary comparison
 */
public class FcCommand implements Command {

    @Override
    public void execute(String[] args, ShellContext context) throws IOException {
        if (args.length < 2) {
            System.out.println("Compares two files or sets of files and displays the differences.");
            System.out.println("\nFC [/A] [/B] [/C] [/L] [/LBn] [/N] [/T] [/W] [/nnnn]");
            System.out.println("   [drive1:][path1]filename1 [drive2:][path2]filename2");
            System.out.println(
                    "\n  /A         Displays only first and last lines for each set of differences.");
            System.out.println("  /B         Performs a binary comparison.");
            System.out.println("  /C         Disregards the case of letters.");
            System.out.println("  /L         Compares files as ASCII text.");
            System.out.println("  /N         Displays the line numbers on an ASCII comparison.");
            return;
        }

        boolean binary = false;
        boolean ignoreCase = false;
        boolean showLineNumbers = false;
        int argIndex = 0;

        // Parse flags
        while (argIndex < args.length && args[argIndex].startsWith("/")) {
            String flag = args[argIndex].toUpperCase();
            if (flag.equals("/B")) binary = true;
            else if (flag.equals("/C")) ignoreCase = true;
            else if (flag.equals("/N")) showLineNumbers = true;
            argIndex++;
        }

        if (argIndex + 1 >= args.length) {
            System.out.println("Insufficient parameters.");
            return;
        }

        String file1Name = args[argIndex];
        String file2Name = args[argIndex + 1];

        java.io.File file1 = new java.io.File(file1Name);
        java.io.File file2 = new java.io.File(file2Name);

        if (!file1.isAbsolute()) {
            file1 = new java.io.File(context.getCurrentDir(), file1Name);
        }
        if (!file2.isAbsolute()) {
            file2 = new java.io.File(context.getCurrentDir(), file2Name);
        }

        if (!file1.exists()) {
            System.out.println("Cannot find " + file1Name);
            return;
        }
        if (!file2.exists()) {
            System.out.println("Cannot find " + file2Name);
            return;
        }

        System.out.println("Comparing files " + file1.getName() + " and " + file2.getName());

        if (binary) {
            compareBinary(file1, file2);
        } else {
            compareText(file1, file2, ignoreCase, showLineNumbers);
        }
    }

    private void compareBinary(java.io.File file1, java.io.File file2) throws IOException {
        if (file1.length() != file2.length()) {
            System.out.println("Files are different sizes.");
            return;
        }

        try (java.io.FileInputStream fis1 = new java.io.FileInputStream(file1);
                java.io.FileInputStream fis2 = new java.io.FileInputStream(file2)) {

            int b1, b2;
            long position = 0;
            boolean different = false;

            while ((b1 = fis1.read()) != -1) {
                b2 = fis2.read();
                if (b1 != b2) {
                    System.out.println("Files differ at position " + position);
                    different = true;
                    break;
                }
                position++;
            }

            if (!different) {
                System.out.println("FC: no differences encountered");
            }
        }
    }

    private void compareText(
            java.io.File file1, java.io.File file2, boolean ignoreCase, boolean showLineNumbers)
            throws IOException {
        try (BufferedReader reader1 = new BufferedReader(new FileReader(file1));
                BufferedReader reader2 = new BufferedReader(new FileReader(file2))) {

            String line1, line2;
            int lineNum = 0;
            boolean different = false;

            while (true) {
                line1 = reader1.readLine();
                line2 = reader2.readLine();
                lineNum++;

                if (line1 == null && line2 == null) break;

                if (line1 == null || line2 == null) {
                    System.out.println("Files are different lengths.");
                    different = true;
                    break;
                }

                String compare1 = ignoreCase ? line1.toLowerCase() : line1;
                String compare2 = ignoreCase ? line2.toLowerCase() : line2;

                if (!compare1.equals(compare2)) {
                    System.out.println("***** " + file1.getName());
                    if (showLineNumbers) System.out.print(lineNum + ": ");
                    System.out.println(line1);
                    System.out.println("***** " + file2.getName());
                    if (showLineNumbers) System.out.print(lineNum + ": ");
                    System.out.println(line2);
                    System.out.println("*****\n");
                    different = true;
                }
            }

            if (!different) {
                System.out.println("FC: no differences encountered");
            }
        }
    }

    @Override
    public String description() {
        return "Compares two files or sets of files and displays the differences.";
    }

    @Override
    public String usage() {
        return "fc [/B] [/C] [/N] file1 file2";
    }
}
