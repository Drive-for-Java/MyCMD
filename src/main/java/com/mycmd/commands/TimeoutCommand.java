package com.mycmd.commands;

import com.mycmd.Command;
import com.mycmd.ShellContext;
import java.io.IOException;

public class TimeoutCommand implements Command {
    @Override
    public void execute(String[] args, ShellContext context) {
        int seconds = -1;
        boolean hasSecondsArg = false;
        boolean hasSlashT = false;
        boolean noBreak = false;

        for (int i = 0; i < args.length; i++) {
            if (args[i].equalsIgnoreCase("/t")
                    && i + 1 < args.length
                    && args[i + 1].matches("[+-]?\\d+")) {
                if (hasSlashT) {
                    System.out.println(
                            "Error: Invalid syntax. '/t' option is not allowed more than '1' time(s).");
                    return;
                }
                seconds = Integer.parseInt(args[i + 1]);
                i++;
                hasSecondsArg = true;
                hasSlashT = true;
            } else if (args[i].equalsIgnoreCase("/nobreak")) {
                if (noBreak) {
                    System.out.println(
                            "Error: Invalid syntax. '/nobreak' option is not allowed more than '1' time(s).");
                    return;
                }
                noBreak = true;
            } else if (args[i].matches("[+-]?\\d+")) {
                if (hasSecondsArg) {
                    System.out.println(
                            "Error: Invalid syntax. Default option is not allowed more than '1' time(s).");
                    return;
                }
                seconds = Integer.parseInt(args[i]);
                hasSecondsArg = true;
            } else if (args[i].equalsIgnoreCase("/t")) {
                System.out.println("Error: Invalid syntax. Value expected for '/t'.");
                return;
            }
        }

        if (!hasSecondsArg) {
            System.out.println("Error: Invalid syntax. Seconds value is required.");
            return;
        }

        if (seconds < -1 || seconds > 99999) {
            System.out.println(
                    "Error: Invalid value for timeout specified. Valid range is 0-99999 seconds.");
            return;
        }

        if (seconds == -1) {
            System.out.println();
            PauseCommand pauseCmd = new PauseCommand();
            pauseCmd.execute(new String[0], context);
            return;
        }

        Thread inputThread =
                new Thread(
                        () -> {
                            try {
                                System.in.read();
                            } catch (IOException e) {
                                // Ignore
                            }
                        });
        inputThread.setDaemon(true);

        if (!noBreak) {
            inputThread.start();
        }
        System.out.println();

        for (; seconds > 0; seconds--) {
            if (!noBreak && !inputThread.isAlive()) {
                System.out.println("\r");
                System.out.println();
                return;
            }

            System.out.print(
                    "\rWaiting for "
                            + seconds
                            + " seconds, press "
                            + (noBreak ? "CTRL+C to quit ..." : "enter key to continue ..."));
            System.out.flush();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                if (noBreak) {
                    continue;
                }
                System.out.println();
                Thread.currentThread().interrupt();
                break;
            }
        }
        try {
            while (System.in.available() > 0) {
                System.in.read();
            }
        } catch (IOException e) {
            // Ignore
        }
        System.out.println("\r");
        System.out.println();
    }

    @Override
    public String description() {
        return "Sets a timeout for command execution.";
    }

    @Override
    public String usage() {
        return "timeout <seconds>\n"
                + "timeout /t <seconds>\n"
                + "timeout /t <seconds> /nobreak\n"
                + "timeout /t -1";
    }
}
