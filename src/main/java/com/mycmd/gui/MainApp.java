package com.mycmd.gui;

import com.mycmd.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/** JavaFX entry point — futuristic terminal window for MyCMD. */
public class MainApp extends Application {

    private static ShellEngine engine;
    private static ShellContext context;
    private static CommandRegistry registry;

    @Override
    public void start(Stage stage) throws Exception {
        // initialize core shell
        context = new ShellContext();
        registry = new CommandRegistry();
        engine = new ShellEngine(registry, context);

        // register built-in commands (auto load)
        registerBuiltIns();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycmd/gui/terminal.fxml"));
        Scene scene = new Scene(loader.load());
        scene.getStylesheets()
                .add(getClass().getResource("/com/mycmd/gui/style.css").toExternalForm());

        TerminalController controller = loader.getController();
        controller.init(engine, context);

        stage.setTitle("MyCMD ░▓ Java Terminal ▓░");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/com/mycmd/gui/icon.png")));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    private void registerBuiltIns() {
        registry.register("alias", new com.mycmd.commands.AliasCommand());
        registry.register("dir", new com.mycmd.commands.DirCommand());
        registry.register("echo", new com.mycmd.commands.EchoCommand());
        // add others automatically here as you expand
    }

    public static void main(String[] args) {
        launch();
    }
}
