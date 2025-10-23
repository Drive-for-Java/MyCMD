package com.mycmd.commands;

import com.mycmd.ShellContext;

import java.io.*;
import java.net.Socket;

/**
 * TelnetCommand - simple TCP client for interactive sessions.
 * Usage: telnet <host> [port]
 *
 * Notes:
 * - This is a minimal implementation (no Telnet option negotiation).
 * - It will take over stdin while connected; type 'exit' to return to the shell.
 */
public class TelnetCommand implements Command {

    @Override
    public void execute(String[] args, ShellContext context) throws Exception {
        if (args.length < 1) {
            System.out.println("Usage: telnet <host> [port]");
            return;
        }

        String host = args[0];
        int port = 23;
        if (args.length >= 2) {
            try {
                port = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                System.out.println("Invalid port: " + args[1]);
                return;
            }
        }

        try (Socket socket = new Socket(host, port)) {
            socket.setSoTimeout(0); // blocking reads
            System.out.println("Connected to " + host + ":" + port + " (type 'exit' to quit)");

            // Reader thread: prints remote data to stdout
            Thread reader = new Thread(() -> {
                try (InputStream in = socket.getInputStream();
                     InputStreamReader isr = new InputStreamReader(in);
                     BufferedReader br = new BufferedReader(isr)) {

                    char[] buffer = new char[2048];
                    int read;
                    while ((read = br.read(buffer)) != -1) {
                        System.out.print(new String(buffer, 0, read));
                        System.out.flush();
                    }
                } catch (IOException ignored) {
                    // socket closed or stream ended
                }
            }, "telnet-reader");
            reader.setDaemon(true);
            reader.start();

            // Writer loop: read stdin and send to remote
            try (OutputStream out = socket.getOutputStream();
                 OutputStreamWriter osw = new OutputStreamWriter(out);
                 BufferedWriter bw = new BufferedWriter(osw);
                 BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in))) {

                String line;
                while ((line = stdin.readLine()) != null) {
                    if ("exit".equalsIgnoreCase(line.trim())) break;
                    bw.write(line);
                    bw.write("\r\n"); // typical telnet line ending
                    bw.flush();
                }
            } catch (IOException ignored) {
                // stdin/socket error, will disconnect
            }

            // ensure socket closes to stop reader
            try { socket.close(); } catch (IOException ignored) {}
            System.out.println("\nDisconnected.");
        } catch (IOException e) {
            System.out.println("Connection failed: " + e.getMessage());
        }
    }
}