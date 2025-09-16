package com.mycmd;

public interface Command {
    void execute(String[] args, ShellContext context);
}
