package game.ghost;


import lombok.Getter;
import lombok.Setter;
import point.Point2D;

/**
 * {@code Blinky}  is a subclass of {@link game.ghost.Ghost}.
 * In the {@link game.state.GhostState#CHASE} state it, always target {@code Pacman} position.
 */
@Setter
@Getter
public class Blinky extends Ghost {

    /**
     * {@inheritDoc}
     */
    public void init() {
        super.reset();
        reset();
        super.init();
    }

    /**
     * {@inheritDoc}
     */
    public void reset() {
        setAlive(true);
        setStartRow(13);
        setStartCol(16);
        setRow(getStartRow());
        setCol(getStartCol());
        setVelocity(new Point2D(0, 0));
        updatePosition();
        updateArrayPosition();
        setCageRow(11);
        setCageCol(16);
    }

    protected void scattered() {
        chaseOrFear();
        int temp = getScatteredTime();
        super.scatteredMove(29, 30);
        checkCollision();;
        updateHitbox();
    }
    /* public void cageMove(int endRow, int endCol) {
         super.cageMove(11, 16);
     }
 */
    @Override
    protected void resetCageTime() {
        setCageTime(300);
    }
}
