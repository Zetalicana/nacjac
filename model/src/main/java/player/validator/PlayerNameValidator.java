package player.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import player.Player;
import player.dao.PlayerDAOImpl;

import java.util.List;
import java.util.regex.Pattern;

/**
 * Class for validate the player's name.
 */
public class PlayerNameValidator {
    private static Logger logger = LoggerFactory.getLogger(PlayerNameValidator.class);


    /**
     * Based on the pattern, it determines that the name does not contain unacceptable characters.
     * @param userName the name to be verified
     * @return if name was valid and user name doesn't exist in the database {@code true} otherwise {@code false}
     */
    public boolean valid(String userName) {
        Pattern pattern = Pattern.compile("[A-Za-z0-9_]+");
        if ((userName != null) && pattern.matcher(userName).matches() && !userNameExist(userName)) {
            logger.info("Valid username.");
            return true;
        }
        logger.info("Invalid characters in the username.");
        return false;
    }

    private boolean userNameExist(String userName) {
        PlayerDAOImpl playerDAO = new PlayerDAOImpl();
        List<Player> players = playerDAO.getAllPlayers();
        if (players == null) return false;
        for (Player player : players) {
            if (player.getName().contains(userName)) {
                logger.info("Username is already exist in the database.");
                return true;
            }
        }
        return false;
    }

}
