package com.mycmd;

import com.mycmd.gui.MainApp;
import javafx.application.Application;

/** Entry point for MyCMD-GUI. */
public class App {
    public static void main(String[] args) {
        // Security check to prevent CMD access
        String launchedFrom = System.getenv("MYCMD_LAUNCHED");
        if (launchedFrom == null || !launchedFrom.equalsIgnoreCase("true")) {
            System.out.println("❌ MyCMD-GUI cannot be run directly from CMD.");
            System.out.println("➡️  Please use the official launcher (MyCMD.bat).");
            return;
        }

        // Launch JavaFX GUI
        Application.launch(MainApp.class, args);
    }
}
