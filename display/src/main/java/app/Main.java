package app;

import javafx.application.Application;
import javafx.stage.Stage;
import manager.MainMenuManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main extends Application {
    private static Logger logger = LoggerFactory.getLogger(Main.class);

    @Override
    public void start(Stage mainStage) {
        MainMenuManager mainMenuManager = new MainMenuManager(mainStage);
        mainMenuManager.start();
    }

    public static void main(String[] args) {
        logger.info("Application has been started.");
        launch(args);
    }
}
