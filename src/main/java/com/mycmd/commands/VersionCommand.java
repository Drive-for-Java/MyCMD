package com.mycmd.commands;

import com.mycmd.Command;
import com.mycmd.ShellContext;

public class VersionCommand implements Command {
    @Override
    public void execute(String[] args, ShellContext context) {
        System.out.println("MyCMD Java Shell v1.0");
    }
}
