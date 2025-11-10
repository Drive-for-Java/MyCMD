package com.mycmd.gui;

import com.mycmd.ShellContext;
import com.mycmd.ShellEngine;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

/**
 * Controller for the FXML terminal.
 */
public class TerminalController {

    @FXML private TextArea outputArea;
    @FXML private TextField inputField;
    @FXML private ScrollPane scrollPane;

    private ShellEngine engine;
    private ShellContext context;

    public void init(ShellEngine engine, ShellContext context) {
        this.engine = engine;
        this.context = context;

        output("💻 Welcome to MyCMD - Java made Terminal");
        output("Type 'help' for available commands.\n");

        inputField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                String input = inputField.getText();
                inputField.clear();
                output("> " + input);
                engine.execute(input);
                scrollPane.setVvalue(1.0);
            }
        });
    }

    private void output(String text) {
        outputArea.appendText(text + "\n");
    }
}
