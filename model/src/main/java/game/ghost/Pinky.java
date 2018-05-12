package game.ghost;


import lombok.Getter;
import lombok.Setter;
import point.Point2D;

/**
 * {@code Pinky} class is the one subclass of the {@link game.ghost.Ghost}.
 * {@code Pinky} always targeting ahead {@code Pacman} with 4 blocks if its possible.
 */
@Setter
@Getter
public class Pinky extends Ghost {
    /***
     * {@inheritDoc}
     */
    @Override
    public void init() {
        super.reset();
        reset();
        super.init();
    }

    /***
     * {@inheritDoc}
     */
    @Override
    public void reset() {
        setAlive(true);
        setStartRow(13);
        setStartCol(18);
        setRow(getStartRow());
        setCol(getStartCol());
        setVelocity(new Point2D(0, 0));
        updatePosition();
        setCageRow(11);
        setCageCol(18);
    }

    @Override
    protected void move() {
        if (isReachedDestination()) {

            int index = 0;
            int ghostRow = this.getRow();
            int ghostCol = this.getCol();
            for (int i = 4; i > -1; i--) {
                if(aheadPacman(i)) {
                    index = i;
                    break;
                }
            }
            int endRow = (int) (getPacman().getRow() + (getPacman().getVelocity().getY() * index));
            int endCol = (int) (getPacman().getCol() + (getPacman().getVelocity().getX() * index));
            findPath(ghostRow, ghostCol, endRow, endCol);

            setReachedDestination(false);

        }

        moveToTarget(getPath().get(getPath().size() - 2).getRow(), getPath().get(getPath().size() - 2).getCol());


    }

    private boolean aheadPacman(int index) {
        int row = getPacman().getRow();
        int col = getPacman().getCol();
        float velocityX = getPacman().getVelocity().getX();
        float velocityY = getPacman().getVelocity().getY();
        int targetRow = (int) (row + index * velocityY);
        int targetCol = (int) (col + index * velocityX);
        if ( targetRow < 31 && targetRow > 1 && targetCol > 1 && targetCol < 36) {
            if (getPacman().getMaze().getMaze()[targetRow][targetCol] == 0) {

                return true;
            }
        }
        return  false;
    }

    @Override
    protected void scattered() {
        chaseOrFear();
        super.scatteredMove(29, 5);
        checkCollision();
        updateHitbox();
    }

    @Override
    protected void resetCageTime() {
        setCageTime(120);
    }
}
