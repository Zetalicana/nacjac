package controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import manager.GameManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import player.Player;
import player.dao.PlayerDAOImpl;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Setter
@Getter
public class SelectPlayerController implements Initializable {
    private static Logger logger = LoggerFactory.getLogger(SelectPlayerController.class);
    private ObservableList<String> observableList;
    private List<Player> playerList;
    private PlayerDAOImpl playerDAO;

    @FXML
    private ListView<String> playerListView;

    @FXML
    public void selectPlayer() {
        Player player = new Player();
        playerList = playerDAO.getAllPlayers();
        observableList = playerListView.getSelectionModel().getSelectedItems();

        if (observableList.size() == 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Invalid Select");
            alert.setHeaderText("Select a player from the list.");
            alert.setContentText("If list is empty or you are a new player, you need to create a new one.");
            alert.showAndWait();
        }

        if (observableList.size() != 0) {
            for (Player existingPlayer : playerList) {
                for (String name : observableList) {
                    if (existingPlayer.getName().equals(name)) {
                        player = existingPlayer;
                    }
                }
            }
            GameManager gameManager = new GameManager(player);
            gameManager.start();

        }

    }

    @FXML
    public void loadNewPlayerMenu() {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/NewPlayer.fxml"));
            Parent root = loader.load();
            NewPlayerController controller = loader.getController();
            controller.setData(playerListView);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Create New Player");
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

        playerListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        playerDAO = new PlayerDAOImpl();

        playerList = playerDAO.getAllPlayers();
        if (playerList != null) {
            for (Player player : playerList) {
                playerListView.getItems().add(player.getName());
            }
        }

    }
}
