package com.mycmd.commands;

import com.mycmd.Command;
import com.mycmd.ShellContext;

import java.io.IOException;

public class UnaliasCommand implements Command {
    @Override
    public void execute(String[] args, ShellContext context) throws IOException {
        if (args == null || args.length == 0) {
            System.out.println("Usage: unalias name [name2 ...]");
            return;
        }

        for (String name : args) {
            if (name == null || name.trim().isEmpty()) continue;
            if (context.hasAlias(name)) {
                context.removeAlias(name);
                System.out.println("Removed alias: " + name);
            } else {
                System.out.println("Alias not found: " + name);
            }
        }
    }

    @Override
    public String description() {
        return "Remove one or more aliases.";
    }

    @Override
    public String usage() {
        return "unalias name [name2 ...]";
    }
}
