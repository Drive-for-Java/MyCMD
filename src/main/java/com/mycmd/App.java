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
                System.out.println("Unknown command: '" + cmd + "'. Enter '" + CommandNames.HELP + "' to list all available commands.");

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

    private static final class CommandNames {
        private CommandNames() {}
        private static final String DIR          = "dir";
        private static final String CD           = "cd";
        private static final String ECHO         = "echo";
        private static final String MKDIR        = "mkdir";
        private static final String RMDIR        = "rmdir";
        private static final String COPY         = "copy";
        private static final String DEL          = "del";
        private static final String TYPE         = "type";
        private static final String CLS          = "cls";
        private static final String HELP         = "help";
        private static final String EXIT         = "exit";
        private static final String VER          = "ver";
        private static final String TITLE        = "title";
        private static final String COLOR        = "color";
        private static final String HOSTNAME     = "hostname";
        private static final String WHOAMI       = "whoami";
        private static final String TOUCH        = "touch";
        private static final String TIME         = "time";
        private static final String TASKLIST     = "tasklist";
        private static final String TREE         = "tree";
        private static final String DATE         = "date";
        private static final String HISTORY      = "history";
        private static final String PING         = "ping";
        private static final String TELNET       = "telnet";
        private static final String PWD          = "pwd";
        private static final String UPTIME       = "uptime";
        private static final String CLEARHISTORY = "clearhistory";
        private static final String IPCONFIG     = "ipconfig";
        private static final String ALIAS        = "alias";
        private static final String UNALIAS      = "unalias";
        private static final String RENAME       = "rename";
        private static final String SET          = "set";
    }
    private static void registerCommands(Map<String, Command> commands) {
        commands.put(CommandNames.DIR, new DirCommand());
        commands.put(CommandNames.CD, new CdCommand());
        commands.put(CommandNames.ECHO, new EchoCommand());
        commands.put(CommandNames.MKDIR, new MkdirCommand());
        commands.put(CommandNames.RMDIR, new RmdirCommand());
        commands.put(CommandNames.COPY, new CopyCommand());
        commands.put(CommandNames.DEL, new DelCommand());
        commands.put(CommandNames.TYPE, new TypeCommand());
        commands.put(CommandNames.CLS, new ClsCommand());
        commands.put(CommandNames.HELP, new HelpCommand(commands));
        commands.put(CommandNames.EXIT, new ExitCommand());
        commands.put(CommandNames.VER, new VersionCommand());
        commands.put(CommandNames.TITLE, new TitleCommand());
        commands.put(CommandNames.COLOR, new ColorCommand());
        commands.put(CommandNames.HOSTNAME, new HostnameCommand());
        commands.put(CommandNames.WHOAMI, new WhoamiCommand());
        commands.put(CommandNames.TOUCH, new TouchCommand());
        commands.put(CommandNames.TIME, new TimeCommand());
        commands.put(CommandNames.TASKLIST, new TasklistCommand());
        commands.put(CommandNames.TREE, new TreeCommand());
        commands.put(CommandNames.DATE, new DateCommand());
        commands.put(CommandNames.HISTORY, new HistoryCommand());
        commands.put(CommandNames.PING, new PingCommand());
        commands.put(CommandNames.TELNET, new TelnetCommand());
        commands.put(CommandNames.PWD, new PwdCommand());
        commands.put(CommandNames.UPTIME, new UptimeCommand());
        commands.put(CommandNames.CLEARHISTORY, new ClearHistoryCommand());
        commands.put(CommandNames.IPCONFIG, new IpConfig());
        commands.put(CommandNames.ALIAS, new AliasCommand());
        commands.put(CommandNames.UNALIAS, new UnaliasCommand());
        commands.put(CommandNames.RENAME, new RenameCommand());
        commands.put(CommandNames.SET, new SetCommand());
    }

}

        
