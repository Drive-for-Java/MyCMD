package com.mycmd.commands;

import com.mycmd.Command;
import com.mycmd.ShellContext;

public class EchoCommand implements Command {
    @Override
    public void execute(String[] args, ShellContext context) {
        if (args.length == 0) {
            System.out.println();
        } else {
            System.out.println(String.join(" ", args));
        }
    }
}
