package manager;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


public class MainMenuManager {
    private static Logger logger = LoggerFactory.getLogger(MainMenuManager.class);
    @Getter
    private static Stage stage;

    public MainMenuManager(Stage stage) {
        this.stage = stage;
    }

    public void start() {
        loadResources();
        initializeStage();
        try {
            initializeScene();
        } catch (IOException e) {
            logger.error("Failed to load and initialize scene.");
            logger.error(e.getMessage());
            logger.error("Application has been terminated.");
            Platform.exit();
        }

        stage.show();

    }
    public void initializeStage() {
        stage.setTitle("Main Menu");
        stage.centerOnScreen();
        stage.setResizable(false);
        stage.initStyle(StageStyle.UTILITY);
        logger.info("Stage initialized");
    }

    public void initializeScene() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/MainMenu.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }
    public void loadResources() {
        logger.info("Resources loaded.");
    }

}
