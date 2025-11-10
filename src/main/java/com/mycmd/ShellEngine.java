package com.mycmd;

import java.io.IOException;
import java.util.Arrays;

/**
 * Central execution engine.
 */
public class ShellEngine {

    private final CommandRegistry registry;
    private final ShellContext context;

    public ShellEngine(CommandRegistry registry, ShellContext context) {
        this.registry = registry;
        this.context = context;
    }

    public void execute(String input) {
        if (input == null || input.trim().isEmpty()) return;

        String[] parts = input.trim().split("\\s+");
        String cmdName = context.resolveAlias(parts[0]);
        String[] args = Arrays.copyOfRange(parts, 1, parts.length);

        Command cmd = registry.get(cmdName);
        if (cmd == null) {
            System.out.println("❌ Unknown command: " + cmdName);
            return;
        }

        try {
            cmd.execute(args, context);
        } catch (IOException e) {
            System.out.println("⚠️ Error executing command: " + e.getMessage());
        }
    }
}
