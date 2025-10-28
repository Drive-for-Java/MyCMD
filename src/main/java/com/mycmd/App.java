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
        public static final String DIR          = "dir";
        public static final String CD           = "cd";
        public static final String ECHO         = "echo";
        public static final String MKDIR        = "mkdir";
        public static final String RMDIR        = "rmdir";
        public static final String COPY         = "copy";
        public static final String DEL          = "del";
        public static final String TYPE         = "type";
        public static final String CLS          = "cls";
        public static final String HELP         = "help";
        public static final String EXIT         = "exit";
        public static final String VER          = "ver";
        public static final String TITLE        = "title";
        public static final String COLOR        = "color";
        public static final String HOSTNAME     = "hostname";
        public static final String WHOAMI       = "whoami";
        public static final String TOUCH        = "touch";
        public static final String TIME         = "time";
        public static final String TASKLIST     = "tasklist";
        public static final String TREE         = "tree";
        public static final String DATE         = "date";
        public static final String HISTORY      = "history";
        public static final String PING         = "ping";
        public static final String TELNET       = "telnet";
        public static final String PWD          = "pwd";
        public static final String UPTIME       = "uptime";
        public static final String CLEARHISTORY = "clearhistory";
        public static final String IPCONFIG     = "ipconfig";
        public static final String ALIAS        = "alias";
        public static final String UNALIAS      = "unalias";
        public static final String RENAME       = "rename";
        public static final String SET          = "set";
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

        
