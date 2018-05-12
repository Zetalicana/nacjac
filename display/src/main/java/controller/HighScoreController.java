package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import player.Player;
import player.dao.PlayerDAOImpl;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class HighScoreController implements Initializable {

    @FXML
    private TableView<Player> tableView;

    @FXML
    private TableColumn<Player, String> nameColumn;

    @FXML
    private TableColumn<Player, Integer> highScoreColumn;





    @Override
    public void initialize(URL location, ResourceBundle resources) {
        PlayerDAOImpl playerDAO = new PlayerDAOImpl();
        List<Player> playerList;
        playerList = playerDAO.getAllPlayers();

        if (playerList != null) {
            for (Player player : playerList) {
                tableView.getItems().add(player);
            }
        }

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        highScoreColumn.setSortType(TableColumn.SortType.DESCENDING);
        highScoreColumn.setCellValueFactory(new PropertyValueFactory<>("highScore"));
        tableView.getSortOrder().add(highScoreColumn);


    }
}
