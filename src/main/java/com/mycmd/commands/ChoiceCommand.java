package com.mycmd.commands;

import com.mycmd.Command;
import com.mycmd.ShellContext;

import java.io.IOException;
import java.util.Scanner;

/**
 * Prompts the user to select from a set of choices.
 * 
 * Usage:
 * - choice /C YN /M "Prompt"    : Choose Y or N
 */
public class ChoiceCommand implements Command {
    
    @Override
    public void execute(String[] args, ShellContext context) throws IOException {
        String choices = "YN";
        String message = "Y/N?";
        boolean caseInsensitive = false;
        int defaultChoice = 0;
        
        // Parse arguments
        for (int i = 0; i < args.length; i++) {
            String arg = args[i].toUpperCase();
            
            if (arg.equals("/C") && i + 1 < args.length) {
                choices = args[++i].toUpperCase();
            } else if (arg.equals("/M") && i + 1 < args.length) {
                message = args[++i];
            } else if (arg.equals("/CS")) {
                caseInsensitive = false;
            } else if (arg.equals("/N")) {
                // No display of choices
            } else if (arg.equals("/D") && i + 1 < args.length) {
                char defChar = args[++i].charAt(0);
                defaultChoice = choices.indexOf(Character.toUpperCase(defChar));
            }
        }
        
        System.out.print(message + " [" + String.join(",", choices.split("")) + "]? ");
        
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine().trim();
        
        if (input.isEmpty() && defaultChoice >= 0) {
            System.out.println(choices.charAt(defaultChoice));
            return;
        }
        
        if (input.length() > 0) {
            char inputChar = caseInsensitive ? Character.toUpperCase(input.charAt(0)) : input.charAt(0);
            int index = choices.indexOf(inputChar);
            
            if (index >= 0) {
                System.out.println(inputChar);
                // Set errorlevel (simulated)
                System.setProperty("ERRORLEVEL", String.valueOf(index + 1));
            } else {
                System.out.println("Invalid choice.");
            }
        }
    }
    
    @Override
    public String description() {
        return "Prompts the user to select from a set of choices.";
    }
    
    @Override
    public String usage() {
        return "choice [/C choices] [/M text] [/CS] [/N] [/D choice]";
    }
}
