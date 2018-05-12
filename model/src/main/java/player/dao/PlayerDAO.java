package player.dao;

import player.Player;

import java.util.List;

/**
 *  A common interface for DAO classes that can act as a
 *  source of {@code Player} objects.
 */
public interface PlayerDAO {

    /**
     * Save the specified player to the database.
     * @param player who will be saved
     */
    void savePlayer(Player player);

    /**
     * Read all player from the database.
     * @return a list with all {@code Player}
     */
    List<Player> getAllPlayers();

}
