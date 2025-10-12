package com.mycmd;

import com.mycmd.commands.*;
import java.util.*;

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

            String[] parts = input.split("\\s+");
            String cmd = parts[0].toLowerCase();
            String[] cmdArgs = Arrays.copyOfRange(parts, 1, parts.length);

            Command command = commands.get(cmd);
            if (command != null) {
                try {
                    command.execute(cmdArgs, context);
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
            } else {
                System.out.println("'" + cmd + "' is not recognized as an internal or external command.");
            }
        }
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
        commands.put("hostname", new HostnameCommand());
        commands.put("whoami", new WhoamiCommand());
        commands.put("touch", new TouchCommand());
        commands.put("time", new TimeCommand());
        commands.put("date", new DateCommand());
    }
}
