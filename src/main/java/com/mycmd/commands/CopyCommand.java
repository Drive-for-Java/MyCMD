package com.mycmd.commands;

import com.mycmd.Command;
import com.mycmd.ShellContext;
import java.io.*;

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
}
