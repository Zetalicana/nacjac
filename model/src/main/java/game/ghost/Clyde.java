package game.ghost;

import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import point.Point2D;



/**
 * {@code Clyde} class is the one subclass of the {@link game.ghost.Ghost}.
 * {@code Clyde} is the stupid one. It never try to kill {@code Pacman}.
 * After it is in a specified range with {@code Pacman} , it moving back to the corner.
 */
@Setter
@Getter
public class Clyde extends Ghost {
    private static Logger logger = LoggerFactory.getLogger(Clyde.class);
    private boolean reachedCorner = true;

    /**
     * {@inheritDoc}
     */
    @Override
    public void init() {
        super.reset();
        reset();
        super.init();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void reset() {
        setAlive(true);
        setStartRow(13);
        setStartCol(19);
        setRow(getStartRow());
        setCol(getStartCol());
        setVelocity(new Point2D(0, 0));
        updatePosition();
        updateArrayPosition();
        setCageRow(11);
        setCageCol(19);

    }

    protected void move() {
        if (isReachedDestination()) {
            if (distanceBetweenPacman()) {
                int ghostRow = this.getRow();
                int ghostCol = this.getCol();
                if (ghostRow == 1 && ghostCol == 5) {
                    findPath(ghostRow, ghostCol, 3, 16);
                }
                else findPath(ghostRow, ghostCol, 1, 5);
            }
            else {
                int pacmanRow = getPacman().getRow();
                int pacmanCol = getPacman().getCol();
                int ghostRow = this.getRow();
                int ghostCol = this.getCol();
                findPath(ghostRow, ghostCol, pacmanRow, pacmanCol);
            }
            setReachedDestination(false);

        }

        moveToTarget(getPath().get(getPath().size() - 2).getRow(), getPath().get(getPath().size() - 2).getCol());


    }

    private boolean distanceBetweenPacman() {
        float distance = this.getPosition().distance(getPacman().getPosition());
        if ( distance < 160) {
            return true;
        }
        return  false;
    }

    protected void scattered() {
        chaseOrFear();
        int temp = getScatteredTime();
        super.scatteredMove(1, 5);
        checkCollision();
        updateHitbox();
    }

    @Override
    protected void resetCageTime() {
        setCageTime(0);
    }
}
