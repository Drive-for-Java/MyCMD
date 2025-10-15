package com.mycmd.commands;

import com.mycmd.Command;
import com.mycmd.ShellContext;

/**
 * Displays the username of the current user.
 * 
 * This command retrieves and displays the system username from the Java
 * system property "user.name". This typically corresponds to the login
 * name of the user running the application.
 * 
 * Usage: whoami
 * 
 * Note: This command does not accept any arguments and always displays
 * the current user's name as reported by the Java runtime.
 */
public class WhoamiCommand implements Command {
    @Override
    public void execute(String[] args, ShellContext context) {
        System.out.println(System.getProperty("user.name"));
    }
}
