package controller;


import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import manager.GameManager;
import manager.MainMenuManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import player.dao.PlayerDAOImpl;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class MainController implements Initializable {
    private static Logger logger = LoggerFactory.getLogger(MainController.class);

    @FXML
    private Canvas canvas;

    private final String SELECT_PLAYER = "/fxml/SelectPlayer.fxml";
    private final String HIGHSCORE = "/fxml/HighScore.fxml";

    private GraphicsContext graphicsContext;
    private PlayerDAOImpl playerDAO;




    @FXML
    public void start() {
        animationTimer.stop();
        loadMenuPoint(SELECT_PLAYER,"Select Player");
    }

    public void draw() {
        graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

            graphicsContext.setFill(Color.RED);
            graphicsContext.fillRect(30,30,100,100);
    }

    private AnimationTimer animationTimer = new AnimationTimer() {
        @Override
        public void handle(long now) {
            draw();
        }
    };

    @FXML
    public void statistics() {

        playerDAO.getAllPlayers();

    }

    @FXML
    public void exit() {
        MainMenuManager.getStage().close();
    }

    @FXML
    public void showHighScore() {
        loadMenuPoint(HIGHSCORE, "HighScore");
    }


    private void loadMenuPoint(String fxml, String title){
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource(fxml));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle(title);
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (IOException e) {
            logger.error("Failed to load and initialize scene.");
            logger.error(e.getMessage());
            logger.error("Application has been terminated.");
            Platform.exit();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        graphicsContext = canvas.getGraphicsContext2D();
        playerDAO = new PlayerDAOImpl();
        animationTimer.start();


    }
}
