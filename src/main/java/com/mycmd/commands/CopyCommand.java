package com.mycmd.commands;

import com.mycmd.Command;
import com.mycmd.ShellContext;
import java.io.*;

/**
 * Copies a file from source to destination location.
 * 
 * This command reads the contents of a source file and writes it to a
 * destination file using buffered I/O streams for efficient copying. Both
 * source and destination paths are resolved relative to the current working
 * directory unless absolute paths are provided.
 * 
 * Usage: copy source destination
 * 
 * The command verifies that the source exists and is a regular file before
 * attempting the copy operation. If the destination file already exists, it
 * will be overwritten.
 */
public class CopyCommand implements Command {
    @Override
    public void execute(String[] args, ShellContext context) {
        if (args.length < 2) {
            System.out.println("Usage: copy <source> <destination>");
            return;
        }
        File src = new File(context.getCurrentDir(), args[0]);
        File dest = new File(context.getCurrentDir(), args[1]);
        if (!src.exists() || !src.isFile()) {
            System.out.println("Source file does not exist.");
            return;
        }
        try (InputStream in = new FileInputStream(src);
             OutputStream out = new FileOutputStream(dest)) {
            byte[] buf = new byte[8192];
            int len;
            while ((len = in.read(buf)) != -1) {
                out.write(buf, 0, len);
            }
            System.out.println("File copied.");
        } catch (IOException e) {
            System.out.println("Error copying file: " + e.getMessage());
        }
    }
    @Override
    public String description() {
        return "Copia um ou mais ficheiros para outro local.";
    }

    @Override
    public String usage() {
        return "Usage: copy <source> <destination>";
    }
}
