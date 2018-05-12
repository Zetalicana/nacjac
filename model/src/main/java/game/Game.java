package game;

import game.state.GameState;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import player.Player;

/**
 * {@code Game} is a model class that stores the game state information.
 */
@Getter
@Setter
@NoArgsConstructor
public class Game {
    private static Logger logger = LoggerFactory.getLogger(Game.class);
    private GameState gameState;
    private Player player;
    private int life = 3;
    private int score;
    private int highScore;
    private int ballsCount;
    private boolean showPath;

    public Game(Player player) {
        this.player = player;
        highScore = player.getHighScore();
    }

    /**
     * Reset the player's lives.
     */
    public void reset() {
        life = 3;
        //score = 0;
    }

    /**
     * Add the specified amount score to the {@code Game}'s score.
     * If {@code score} &gt; {@code highScore} then {@code highScore} = {@code score}.
     * @param x the amount of the score
     */
    public void addScore(int x) {
        int current = this.getScore();
        this.setScore(current + x);
        checkHighScore();
    }

    /**
     * Decrease the number of the balls in the field with 1.
     * If {@code ballsCount} = 0 then set the game state to
     * {@link game.state.GameState#LEVEL_CLEARED}.
     */
    public void decreaseBallsCount() {
        ballsCount--;
        if (ballsCount == 0) {
            setGameState(GameState.LEVEL_CLEARED);
            logger.info("Pacman ate all balls");
            logger.info("Set state to {}", getGameState());
        }
    }

    /**
     * Decrease the number of {@code life} with 1.
     * If {@code life} = 0 then set the game state to
     * {@link game.state.GameState#GAME_OVER}.
     */
    public void decreasePacmanLife() {
        life--;
        if (life == 0) {
            setGameState(GameState.GAME_OVER);
            logger.info("Pacman is dead, there is no more life left");
            logger.info("Game state set to {}", getGameState());
        }
        else {setGameState(GameState.RESET);}
    }


    private void checkHighScore() {
        if (score > highScore) {
            highScore = score;
            player.setHighScore(highScore);
        }
    }
}
