package game.ball;

import game.Game;
import game.HitBox;
import game.Pacman;
import game.WallBlock;
import game.ghost.Ghost;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import point.Point2D;

import java.util.List;

/**
 * {@code Ball} class representing a game object.
 * {@code Pacman} can eat this balls.
 */
@Getter
@Setter
@NoArgsConstructor
public class Ball {
    private int row;
    private int col;
    private Point2D position;
    private boolean eated;
    private int ballSize;
    private HitBox hitBox;
    private Pacman pacman;
    private Game game;
    private List<Ghost> ghosts;


    public Ball(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /**
     * Initialize the position of the ball.
     */
    public void init() {
        int x = this.getCol() * WallBlock.BLOCKSIZE - (4 * WallBlock.BLOCKSIZE) + ((WallBlock.BLOCKSIZE - this.getBallSize()) / 2);
        int y = this.getRow() * WallBlock.BLOCKSIZE + ((WallBlock.BLOCKSIZE - this.getBallSize()) / 2);
        this.setPosition(new Point2D(x, y));
    }

    /**
     * In {@link game.state.GameState#PLAY} state
     * this method invoked.
     * Checking the intersect between {@code Pacman} and {@code Ball}.
     */
    public void update() {}

    /**
     * Set the ball visibility to the true again.
     * When game state is {@link game.state.GameState#LEVEL_CLEARED}
     * this method is invoked.
     */
    public void hardReset() {
        this.setEated(false);
        //logger.debug("Number of ball is {}",game.getBallsCount());
    }
}
