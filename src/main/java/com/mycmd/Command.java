package com.mycmd;

import java.io.IOException;

/**
 * Represents a shell command that can be executed inside the MyCMD shell. Implementations perform
 * their operation when {@link #execute(String[], ShellContext)} is called.
 */
public interface Command {
    /**
     * Execute the command.
     *
     * @param args command-line style arguments passed to the command. May be empty but will not be
     *     null.
     * @param context current shell context containing state such as the current working directory.
     * @throws IOException if the command cannot complete successfully.
     */
    void execute(String[] args, ShellContext context) throws IOException;

    /**
     * Short description of the command. Default is empty so existing implementations do not break.
     */
    default String description() {
        return "";
    }

    /** Usage string for the command. Default is empty. */
    default String usage() {
        return "";
    }
}
