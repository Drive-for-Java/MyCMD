package com.mycmd.commands;

import com.mycmd.Command;
import com.mycmd.ShellContext;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

public class TimeoutCommand implements Command {
    /**
     * Execute the timeout command.
     *
     * <p>This command will wait for the specified number of seconds before continuing. If the user
     * presses Enter before the timeout expires, the command will terminate immediately.
     *
     * @param args The arguments to the command.
     * @param context The context of the shell.
     * @throws IOException If an I/O error occurs.
     */
    @Override
    public void execute(String[] args, ShellContext context) {
        int seconds = -1;
        boolean hasSecondsArg = false;
        boolean hasSlashT = false;
        boolean noBreak = false;

        for (int i = 0; i < args.length; i++) {
            if (args[i].equalsIgnoreCase("/t") && i + 1 < args.length && args[i + 1].matches("[+-]?\\d+")) {
                if (hasSlashT) {
                    System.out.println("Error: Invalid syntax. '/t' option is not allowed more than '1' time(s).");
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
                    System.out.println("Error: Invalid syntax. Default option is not allowed more than '1' time(s).");
                    return;
                }
                seconds = Integer.parseInt(args[i]);
                hasSecondsArg = true;
            } else if (args[i].equalsIgnoreCase("/t")) {
                System.out.println("Error: Invalid syntax. Value expected for '/t'.");
                return;
            } else {
                System.out.println("Error: Invalid syntax. Unrecognized argument: " + args[i]);
                return;
            }
        }

        if (!hasSecondsArg) {
            System.out.println("Error: Invalid syntax. Seconds value is required.");
            return;
        }

        if (seconds < -1 || seconds > 99999) {
            System.out.println("Error: Invalid value for timeout specified. Valid range is 0-99999 seconds.");
            return;
        }

        if (seconds == -1) {
            System.out.println();
            PauseCommand pauseCmd = new PauseCommand();
            pauseCmd.execute(new String[0], context);
            return;
        }

        AtomicBoolean interrupted = new AtomicBoolean(false);
        AtomicBoolean stopInput = new AtomicBoolean(false);
        Thread inputThread = null;

        if (!noBreak) {
            inputThread = new Thread(() -> {
                try {
                    // Poll non-blocking so we can stop the thread
                    // deterministically.
                    while (!stopInput.get()) {
                        if (System.in.available() > 0) {
                            int r = System.in.read();
                            // Treat CR or LF as Enter across platforms
                            if (r == '\n' || r == '\r') {
                                interrupted.set(true);
                                break;
                            }
                        } else {
                            try {
                                Thread.sleep(25);
                            } catch (InterruptedException ie) {
                                Thread.currentThread().interrupt();
                                break;
                            }
                        }
                    }
                } catch (IOException e) {
                    // Best-effort only; fall through.
                }
            });
            inputThread.setDaemon(true);
            inputThread.start();
        }
        System.out.println();

        for (; seconds > 0; seconds--) {
            if (!noBreak && interrupted.get()) {
                System.out.println("\r");
                System.out.println();
                if (inputThread != null) {
                    stopInput.set(true);
                    try {
                        inputThread.join(200);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                    }
                }
                return;
            }

            System.out.print("\rWaiting for "
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

        // Normal completion: stop the input thread before draining.
        if (!noBreak && inputThread != null) {
            stopInput.set(true);
            try {
                inputThread.join(200);
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            }
        }
        try {
            // Drain any remaining bytes so subsequent commands don't immediately see
            // leftover input. This is a best-effort drain; System.in.available() may
            // not be supported on all streams, but for typical console streams it helps.
            while (System.in.available() > 0) {
                System.in.read();
            }
        } catch (IOException e) {
            // Ignore: if we can't drain the stream it's non-fatal; any leftover input
            // will be handled by the next read and is acceptable.
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
        return "timeout <seconds>\n" + "timeout /t <seconds>\n" + "timeout /t <seconds> /nobreak\n" + "timeout /t -1";
    }
}
