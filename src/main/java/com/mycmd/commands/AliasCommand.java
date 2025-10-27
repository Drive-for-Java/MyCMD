package com.mycmd.commands;

import com.mycmd.Command;
import com.mycmd.ShellContext;

import java.io.IOException;
import java.util.Map;

public class AliasCommand implements Command {
    @Override
    public void execute(String[] args, ShellContext context) throws IOException {
        // no args: list aliases
        if (args == null || args.length == 0) {
            Map<String, String> aliases = context.getAliases();
            if (aliases.isEmpty()) {
                System.out.println("No aliases defined.");
            } else {
                aliases.forEach((k, v) -> System.out.println(k + "=" + v));
            }
            return;
        }

        // single arg of form name=command
        if (args.length == 1 && args[0].contains("=")) {
            String[] parts = args[0].split("=", 2);
            String name = parts[0].trim();
            String cmd = parts[1].trim();
            if (name.isEmpty() || cmd.isEmpty()) {
                System.out.println("Invalid alias format. Usage: \n" + usage());
                return;
            }
            context.addAlias(name, cmd);
            System.out.println("Alias added: " + name + "=" + cmd);
            return;
        }

        // multiple args: first is name, rest form command
        if (args.length >= 2) {
            String name = args[0];
            StringBuilder sb = new StringBuilder();
            for (int i = 1; i < args.length; i++) {
                if (i > 1) sb.append(' ');
                sb.append(args[i]);
            }
            String cmd = sb.toString();
            if (name.trim().isEmpty() || cmd.trim().isEmpty()) {
                System.out.println(
                        "Invalid alias. Usage: \n" + usage());
                return;
            }
            context.addAlias(name, cmd);
            System.out.println("Alias added: " + name + "=" + cmd);
            return;
        }

        System.out.println("Invalid usage. Usage: alias [name=command] | alias [name command...]");
    }

    @Override
    public String description() {
        return "Create or list command aliases.";
    }

    @Override
    public String usage() {
        return "alias                 # list aliases\n" +
               "alias name=command    # create alias\n" +
               "alias name command... # create alias";
    }
}