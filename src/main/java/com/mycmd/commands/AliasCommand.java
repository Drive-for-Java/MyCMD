package com.mycmd.commands;

import com.mycmd.ShellContext;
import java.util.Map;

public class AliasCommand implements Command {
    @Override
    public void execute(String[] args, ShellContext context) {
        if (args.length == 0) {
            // List all aliases
            Map<String, String> aliases = context.getAliases();
            if (aliases.isEmpty()) {
                System.out.println("No aliases defined.");
            } else {
                System.out.println("Current aliases:");
                for (Map.Entry<String, String> entry : aliases.entrySet()) {
                    System.out.println("  " + entry.getKey() + "=\"" + entry.getValue() + "\"");
                }
            }
        } else {
            // Create alias: alias name="command" or alias name=command
            String input = String.join(" ", args);
            
            if (!input.contains("=")) {
                System.out.println("Usage: alias name=\"command\"");
                System.out.println("Example: alias ll=\"dir /w\"");
                return;
            }

            String[] parts = input.split("=", 2);
            String name = parts[0].trim();
            String command = parts[1].trim();

            // Remove quotes if present
            if (command.startsWith("\"") && command.endsWith("\"")) {
                command = command.substring(1, command.length() - 1);
            }

            // Validate alias name
            if (name.isEmpty() || name.contains(" ")) {
                System.out.println("Error: Alias name cannot be empty or contain spaces.");
                return;
            }

            if (command.isEmpty()) {
                System.out.println("Error: Alias command cannot be empty.");
                return;
            }

            // Check for circular reference
            if (command.split("\\s+")[0].equalsIgnoreCase(name)) {
                System.out.println("Error: Cannot create circular alias reference.");
                return;
            }

            context.addAlias(name, command);
            System.out.println("Alias created: " + name + "=\"" + command + "\"");
        }
    }

    @Override
    public String getHelp() {
        return "alias [name=\"command\"] - Create or list command aliases\n" +
               "  alias                  - List all aliases\n" +
               "  alias name=\"command\"   - Create new alias\n" +
               "  Example: alias ll=\"dir /w\"";
    }
}