package game;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import point.Point2D;


/**
 * {@code Pacman} class representing a game object.
 * The player can manipulate {@code Pacman}'s position.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Pacman {

    private static Logger logger = LoggerFactory.getLogger(Pacman.class);
    private int row;
    private int col;
    private boolean alive;
    private HitBox hitBox;
    private Point2D position;
    private Point2D velocity;
    private Maze maze;
    private Point2D desiredDirection;
    private int killingSpree;


    /**
     * Update {@code Pacman}'s data.
     * If game state is {@link game.state.GameState#PLAY} then
     * this method is invoked by {@code GameManager}.
     * If the player's input was valid then {@code Pacman}'s
     * position will change to the desired direction.
     */
    public void updatePlaying() {
        move();
        updateArrayPosition();
        updateCollider();
    }

    /**
     * Initialize {@code Pacman}'s data.
     * If game state is {@link game.state.GameState#INITIALIZE} then
     * this method is invoked by {@code GameManager}.
     */
    public void init() {
        reset();
        hitBox = new HitBox(0, 0, WallBlock.BLOCKSIZE, WallBlock.BLOCKSIZE);
        killingSpree = 1;
        updateCollider();
    }

    /**
     * Resets pacman's state to the start state.
     */
    public void reset() {
        alive = true;
        col = 18;
        row = 23;
        velocity = new Point2D(1, 0);
        desiredDirection = new Point2D(1, 0);
        updatePosition();
    }

    private void updateArrayPosition() {
        int row = (int) Math.floor(position.getY() / WallBlock.BLOCKSIZE);
        int col = (int) Math.floor((position.getX() + (4 * WallBlock.BLOCKSIZE)) / WallBlock.BLOCKSIZE);
        this.row = row;
        this.col = col;
    }

    private void updateCollider() {
        hitBox.setX(position.getX());
        hitBox.setY(position.getY());
    }


    private void move() {
        if (checkPosition()) {
            float x = velocity.getX();
            float y = velocity.getY();
            Point2D newPos = position.add(x, y);
            position = newPos;
        }
    }
    private void updatePosition() {
        int x = col * WallBlock.BLOCKSIZE - (4 * WallBlock.BLOCKSIZE);
        int y = row * WallBlock.BLOCKSIZE;
        position = new Point2D(x, y);
    }

    private boolean checkPosition() {

        if (position.getX() % 20 == 0 && position.getY() % 20 == 0) {

            Point2D matrixPosition = new Point2D((position.getX() + 80) / 20, (position.getY())/ 20);
            if (matrixPosition.getX() == 1) {
                int y = (int) position.getY();
                int x =  (33 * WallBlock.BLOCKSIZE - (4 * WallBlock.BLOCKSIZE));
                position = new Point2D(x, y);
            }
            else if (matrixPosition.getX() == 34) {
                int y = (int) position.getY();
                int x = (2 * WallBlock.BLOCKSIZE - (4 * WallBlock.BLOCKSIZE));
                position = new Point2D(x, y);
            }

            Point2D positionToCheck = new Point2D(matrixPosition.getX() + desiredDirection.getX(), matrixPosition.getY() + desiredDirection.getY());

            if (maze.getMaze()[(int) Math.floor(positionToCheck.getY())][(int) Math.floor(positionToCheck.getX())] == 1) {
                if (maze.getMaze()[(int) Math.floor(matrixPosition.getY() + velocity.getY())][(int) Math.floor(matrixPosition.getX() + velocity.getX())] == 1) {
                    return false;
                } else {
                    return true;
                }
            } else {
               velocity = new Point2D(desiredDirection.getX(), desiredDirection.getY());
            }
        }

        else {
            if (desiredDirection.getX() + velocity.getX() == 0 && velocity.getY() + desiredDirection.getY() == 0) {
                velocity = new Point2D(desiredDirection.getX(), desiredDirection.getY());
                return true;
            }
        }
        return true;
    }
}
