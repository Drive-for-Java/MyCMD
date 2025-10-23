package com.mycmd;

import com.mycmd.ShellContext;

public interface Command {
    String getName();
    String getDescription();
    /**
     * Execute the command.
     * @param context shell context
     * @param args command arguments
     */
    void execute(ShellContext context, String[] args) throws Exception;
}
