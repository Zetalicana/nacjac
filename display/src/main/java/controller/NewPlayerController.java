package controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import player.Player;
import player.dao.PlayerDAOImpl;
import player.validator.PlayerNameValidator;

import java.net.URL;

import java.util.ResourceBundle;


public class NewPlayerController implements Initializable {
    private ListView data;

    public void setData(ListView data) {
        this.data = data ;
    }


    @FXML
    private TextField playerNameField;

    @FXML
    public void createNewPlayer() {
        System.out.println(playerNameField.getText());
        PlayerNameValidator playerNameValidator = new PlayerNameValidator();
        PlayerDAOImpl playerDAO = new PlayerDAOImpl();

        if (playerNameValidator.valid(playerNameField.getText())) {
            Player player = new Player();
            player.setName(playerNameField.getText());
            playerDAO.savePlayer(player);
            data.getItems().add(playerNameField.getText());

        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
