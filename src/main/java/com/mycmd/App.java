package com.mycmd;

import com.mycmd.commands.*;
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
                System.out.println(
                        "Unknown command: '"
                                + cmd
                                + "'. Enter '"
                                + CommandNames.HELP
                                + "' to list all available commands.");

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

        private static final String ALIAS = "alias";
        private static final String ARP = "arp";
        private static final String ASSOC = "assoc";
        private static final String ATTRIB = "attrib";
        private static final String CD = "cd";
        private static final String CHKDSK = "chkdsk";
        private static final String CHOICE = "choice";
        private static final String CLEARHISTORY = "clearhistory";
        private static final String CLIP = "clip";
        private static final String CLS = "cls";
        private static final String COLOR = "color";
        private static final String COMPACT = "compact";
        private static final String COPY = "copy";
        private static final String DATE = "date";
        private static final String DEL = "del";
        private static final String DIR = "dir";
        private static final String DRIVERQUERY = "driverquery";
        private static final String ECHO = "echo";
        private static final String EXIT = "exit";
        private static final String FC = "fc";
        private static final String FIND = "find";
        private static final String FINDSTR = "findstr";
        private static final String FORFILES = "forfiles";
        private static final String FSUTIL = "fsutil";
        private static final String FTYPE = "ftype";
        private static final String GETMAC = "getmac";
        private static final String HELP = "help";
        private static final String HISTORY = "history";
        private static final String HOSTNAME = "hostname";
        private static final String IPCONFIG = "ipconfig";
        private static final String LABEL = "label";
        private static final String LS = "ls";
        private static final String MKDIR = "mkdir";
        private static final String MORE = "more";
        private static final String MOVE = "move";
        private static final String MSG = "msg";
        private static final String NET = "net";
        private static final String NETSH = "netsh";
        private static final String NETSTAT = "netstat";
        private static final String NSLOOKUP = "nslookup";
        private static final String PATH = "path";
        private static final String PAUSE = "pause";
        private static final String PING = "ping";
        private static final String PWD = "pwd";
        private static final String REM = "rem";
        private static final String RENAME = "rename";
        private static final String REPLACE = "replace";
        private static final String RMDIR = "rmdir";
        private static final String ROBOCOPY = "robocopy";
        private static final String ROUTE = "route";
        private static final String SET = "set";
        private static final String SFC = "sfc";
        private static final String SHUTDOWN = "shutdown";
        private static final String SORT = "sort";
        private static final String START = "start";
        private static final String SYSTEMINFO = "systeminfo";
        private static final String TASKKILL = "taskkill";
        private static final String TASKLIST = "tasklist";
        private static final String TELNET = "telnet";
        private static final String TIME = "time";
        private static final String TIMEOUT = "timeout";
        private static final String TITLE = "title";
        private static final String TOUCH = "touch";
        private static final String TRACERT = "tracert";
        private static final String TREE = "tree";
        private static final String TYPE = "type";
        private static final String UNALIAS = "unalias";
        private static final String UPTIME = "uptime";
        private static final String VER = "ver";
        private static final String VERIFY = "verify";
        private static final String VOL = "vol";
        private static final String WHOAMI = "whoami";
        private static final String WMIC = "wmic";
        private static final String XCOPY = "xcopy";
    }

    private static void registerCommands(Map<String, Command> commands) {
        commands.put(CommandNames.ALIAS, new AliasCommand());
        commands.put(CommandNames.ARP, new ArpCommand());
        commands.put(CommandNames.ASSOC, new AssocCommand());
        commands.put(CommandNames.ATTRIB, new AttribCommand());
        commands.put(CommandNames.CD, new CdCommand());
        commands.put(CommandNames.CHKDSK, new ChkdskCommand());
        commands.put(CommandNames.CHOICE, new ChoiceCommand());
        commands.put(CommandNames.CLEARHISTORY, new ClearHistoryCommand());
        commands.put(CommandNames.CLIP, new ClipCommand());
        commands.put(CommandNames.CLS, new ClsCommand());
        commands.put(CommandNames.COLOR, new ColorCommand());
        commands.put(CommandNames.COMPACT, new CompactCommand());
        commands.put(CommandNames.COPY, new CopyCommand());
        commands.put(CommandNames.DATE, new DateCommand());
        commands.put(CommandNames.DEL, new DelCommand());
        commands.put(CommandNames.DIR, new DirCommand());
        commands.put(CommandNames.DRIVERQUERY, new DriverqueryCommand());
        commands.put(CommandNames.ECHO, new EchoCommand());
        commands.put(CommandNames.EXIT, new ExitCommand());
        commands.put(CommandNames.FC, new FcCommand());
        commands.put(CommandNames.FIND, new FindCommand());
        commands.put(CommandNames.FINDSTR, new FindstrCommand());
        commands.put(CommandNames.FORFILES, new ForfilesCommand());
        commands.put(CommandNames.FSUTIL, new FsutilCommand());
        commands.put(CommandNames.FTYPE, new FtypeCommand());
        commands.put(CommandNames.GETMAC, new GetmacCommand());
        commands.put(CommandNames.HELP, new HelpCommand(commands));
        commands.put(CommandNames.HISTORY, new HistoryCommand());
        commands.put(CommandNames.HOSTNAME, new HostnameCommand());
        commands.put(CommandNames.IPCONFIG, new IpConfig());
        commands.put(CommandNames.LABEL, new LabelCommand());
        commands.put(CommandNames.LS, new LsCommand());
        commands.put(CommandNames.MKDIR, new MkdirCommand());
        commands.put(CommandNames.MORE, new MoreCommand());
        commands.put(CommandNames.MOVE, new MoveCommand());
        commands.put(CommandNames.MSG, new MsgCommand());
        commands.put(CommandNames.NET, new NetCommand());
        commands.put(CommandNames.NETSH, new NetshCommand());
        commands.put(CommandNames.NETSTAT, new NetstatCommand());
        commands.put(CommandNames.NSLOOKUP, new NslookupCommand());
        commands.put(CommandNames.PATH, new PathCommand());
        commands.put(CommandNames.PAUSE, new PauseCommand());
        commands.put(CommandNames.PING, new PingCommand());
        commands.put(CommandNames.PWD, new PwdCommand());
        commands.put(CommandNames.REM, new RemCommand());
        commands.put(CommandNames.RENAME, new RenameCommand());
        commands.put(CommandNames.REPLACE, new ReplaceCommand());
        commands.put(CommandNames.RMDIR, new RmdirCommand());
        commands.put(CommandNames.ROBOCOPY, new RobocopyCommand());
        commands.put(CommandNames.ROUTE, new RouteCommand());
        commands.put(CommandNames.SET, new SetCommand());
        commands.put(CommandNames.SFC, new SfcCommand());
        commands.put(CommandNames.SHUTDOWN, new ShutdownCommand());
        commands.put(CommandNames.SORT, new SortCommand());
        commands.put(CommandNames.START, new StartCommand());
        commands.put(CommandNames.SYSTEMINFO, new SysteminfoCommand());
        commands.put(CommandNames.TASKKILL, new TaskkillCommand());
        commands.put(CommandNames.TASKLIST, new TasklistCommand());
        commands.put(CommandNames.TELNET, new TelnetCommand());
        commands.put(CommandNames.TIME, new TimeCommand());
        commands.put(CommandNames.TIMEOUT, new TimeoutCommand());
        commands.put(CommandNames.TITLE, new TitleCommand());
        commands.put(CommandNames.TOUCH, new TouchCommand());
        commands.put(CommandNames.TRACERT, new TracertCommand());
        commands.put(CommandNames.TREE, new TreeCommand());
        commands.put(CommandNames.TYPE, new TypeCommand());
        commands.put(CommandNames.UNALIAS, new UnaliasCommand());
        commands.put(CommandNames.UPTIME, new UptimeCommand());
        commands.put(CommandNames.VER, new VersionCommand());
        commands.put(CommandNames.VERIFY, new VerifyCommand());
        commands.put(CommandNames.VOL, new VolCommand());
        commands.put(CommandNames.WHOAMI, new WhoamiCommand());
        commands.put(CommandNames.WMIC, new WmicCommand());
        commands.put(CommandNames.XCOPY, new XcopyCommand());
    }
}
