package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import player.Player;
import player.dao.PlayerDAOImpl;
import player.validator.PlayerNameValidator;

import java.net.URL;

import java.util.ResourceBundle;


public class NewPlayerController implements Initializable {
    private static Logger logger = LoggerFactory.getLogger(NewPlayerController.class);
    private ListView data;

    public void setData(ListView data) {
        this.data = data ;
    }


    @FXML
    private TextField playerNameField;

    @FXML
    public void createNewPlayer() {
        PlayerNameValidator playerNameValidator = new PlayerNameValidator();
        PlayerDAOImpl playerDAO = new PlayerDAOImpl();

        if (playerNameValidator.valid(playerNameField.getText())) {
            Player player = new Player();
            player.setName(playerNameField.getText());
            playerDAO.savePlayer(player);
            logger.info("{} added to the database.", playerNameField.getText());
            data.getItems().add(playerNameField.getText());
            Stage stage = (Stage) playerNameField.getScene().getWindow();
            stage.close();
        }

        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Invalid Name");
            alert.setHeaderText("Pls enter a different name.");
            alert.setContentText("The name already exist or contains invalid characters.");
            alert.showAndWait();
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
