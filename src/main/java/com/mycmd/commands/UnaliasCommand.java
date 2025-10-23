package com.mycmd.commands;

import com.mycmd.ShellContext;

public class UnaliasCommand implements Command {
    @Override
    public void execute(String[] args, ShellContext context) {
        if (args.length == 0) {
            System.out.println("Usage: unalias <name>");
            System.out.println("Example: unalias ll");
            return;
        }

        String aliasName = args[0].trim();

        if (!context.hasAlias(aliasName)) {
            System.out.println("Error: Alias '" + aliasName + "' not found.");
            return;
        }

        context.removeAlias(aliasName);
        System.out.println("Alias removed: " + aliasName);
    }

    @Override
    public String getHelp() {
        return "unalias <name> - Remove a command alias\n" +
               "  Example: unalias ll";
    }
}