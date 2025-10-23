package com.mycmd;

import com.mycmd.commands.*;
import com.mycmd.utils.StringUtils;

import java.util.*;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        ShellContext context = new ShellContext();

        // Register commands
        Map<String, Command> commands = new HashMap<>();
        registerCommands(commands);

        System.out.println("MyCMD [Version 1.0]");
        System.out.println("(c) 2025 MyCMD Organization. All rights reserved.");

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.print(context.getCurrentDir().getAbsolutePath() + ">");
            String input = sc.nextLine().trim();
            if (input.isEmpty()) continue;

            // Resolve aliases before processing
            input = resolveAliases(input, context);

            String[] parts = input.split("\\s+");
            String cmd = parts[0].toLowerCase();
            String[] cmdArgs = Arrays.copyOfRange(parts, 1, parts.length);

            Command command = commands.get(cmd);
            if (command != null) {
                try {
                    command.execute(cmdArgs, context);
                    // Add to history after successful execution
                    context.addToHistory(input);
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
            } else {
                // Single, clear not-recognized message + optional suggestion
                System.out.println("'" + cmd + "' is not recognized as an internal or external command.");

                // compute suggestion safely
                try {
                    List<String> validCommands = new ArrayList<>(commands.keySet());
                    String suggestion = StringUtils.findClosest(cmd, validCommands);
                    if (suggestion != null && !suggestion.equalsIgnoreCase(cmd)) {
                        System.out.println("Did you mean '" + suggestion + "'?");
                    }
                } catch (Exception ex) {
                    // don't let suggestion errors break the shell
                }
            }
        }
    }

    private static String resolveAliases(String input, ShellContext context) {
        String[] parts = input.split("\\s+", 2);
        String cmd = parts[0];
        String rest = parts.length > 1 ? parts[1] : "";

        // Check if the command is an alias
        if (context.hasAlias(cmd)) {
            String aliasCommand = context.getAlias(cmd);
            // Replace the alias with its command, preserving arguments
            return rest.isEmpty() ? aliasCommand : aliasCommand + " " + rest;
        }

        return input;
    }

    private static void registerCommands(Map<String, Command> commands) {
        commands.put("dir", new DirCommand());
        commands.put("cd", new CdCommand());
        commands.put("echo", new EchoCommand());
        commands.put("mkdir", new MkdirCommand());
        commands.put("rmdir", new RmdirCommand());
        commands.put("copy", new CopyCommand());
        commands.put("del", new DelCommand());
        commands.put("type", new TypeCommand());
        commands.put("cls", new ClsCommand());
        commands.put("help", new HelpCommand(commands));
        commands.put("exit", new ExitCommand());
        commands.put("ver", new VersionCommand());
        commands.put("title", new TitleCommand());
        commands.put("color", new ColorCommand());
        commands.put("hostname", new HostnameCommand());
        commands.put("whoami", new WhoamiCommand());
        commands.put("touch", new TouchCommand());
        commands.put("time", new TimeCommand());
        commands.put("tree", new TreeCommand());
        commands.put("date", new DateCommand());
        commands.put("history", new HistoryCommand());
        commands.put("ping", new PingCommand());
        commands.put("telnet", new TelnetCommand());
        commands.put("pwd", new PwdCommand());
        commands.put("uptime", new UptimeCommand());
        commands.put("clearhistory", new ClearHistoryCommand());
        commands.put("alias", new AliasCommand());
        commands.put("unalias", new UnaliasCommand());
    }
}
