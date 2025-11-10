package com.mycmd;

import java.util.Scanner;

/**
 * Developer console mode for debugging.
 */
public class ConsoleShell {

    public static void main(String[] args) {
        CommandRegistry registry = new CommandRegistry();
        ShellContext context = new ShellContext();
        ShellEngine engine = new ShellEngine(registry, context);

        Scanner sc = new Scanner(System.in);
        System.out.println("MyCMD Developer Console Mode\n(Type 'exit' to quit)");

        while (true) {
            System.out.print("> ");
            String input = sc.nextLine();
            if (input.equalsIgnoreCase("exit")) break;
            engine.execute(input);
        }

        sc.close();
    }
}
