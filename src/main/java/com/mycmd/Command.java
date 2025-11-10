package com.mycmd;

import java.io.IOException;

/**
 * Interfaace for all commands.
 * Every command impleements this
 */
public interface Command {
    void execute(String[] args, ShellContext context) throws IOException;
    String description();
    String usage();
}
