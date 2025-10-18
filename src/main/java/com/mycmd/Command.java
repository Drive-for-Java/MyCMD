package com.mycmd;

/**
 * Represents a shell command that can be executed inside the MyCMD shell.
 * Implementations perform their operation when {@link #execute(String[], ShellContext)} is called.
 */
public interface Command {
    /**
     * Execute the command.
     *
     * <p>Implementations should perform all output to standard output or use the provided
     * {@link ShellContext} for interactions that depend on the shell state (for example,
     * changing the current directory).</p>
     *
     * @param args    command-line style arguments passed to the command. May be empty but
     *                will not be null.
     * @param context current shell context containing state such as the current working
     *                directory. Implementations may read and/or modify this context.
     * @throws Exception if the command cannot complete successfully. The caller (shell)
     *                   is responsible for catching and reporting exceptions.
     *
     * @implNote Common concrete commands include `date` and `time` which simply print the
     * current date/time to standard output. See their implementations for formatting details.
     */
    void execute(String[] args, ShellContext context);

    String description();

    String usage();
}
