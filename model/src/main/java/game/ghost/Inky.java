package game.ghost;

import game.Maze;
import lombok.Getter;
import lombok.Setter;
import point.Point2D;

/**
 * {@code Inky} class is the one subclass of the {@link game.ghost.Ghost}.
 * {@code Inky} is the tricky one. It try to ambush pacman with the help of {@code Blinky}.
 */
@Setter
@Getter
public class Inky extends Ghost {
    private Ghost blinky;

    public Inky(Ghost blinky) {
        this.blinky = blinky;
    }

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
        setStartCol(17);
        setRow(getStartRow());
        setCol(getStartCol());
        setVelocity(new Point2D(0, 0));
        updatePosition();
        setCageRow(11);
        setCageCol(17);
    }

    @Override
    protected void scattered() {
        chaseOrFear();
        super.scatteredMove(1, 30);
        checkCollision();
        updateHitbox();
    }

    @Override
    protected void resetCageTime() {
        setCageTime(240);
    }


    @Override
    protected void move() {
        if (isReachedDestination()) {
            int inkyRow = this.getRow();
            int inkyCol = this.getCol();
            int blinkyRow = blinky.getRow();
            int blinkyCol = blinky.getCol();
            int pacmanRow = getPacman().getRow();
            int pacmanCol = getPacman().getCol();
            int blinkyToPacmanRow = getPacman().getRow()  - blinkyCol;
            int blinkyToPacmanCol = getPacman().getRow()  - blinkyRow;
            int targetRow = pacmanRow + blinkyToPacmanRow;
            int targetCol = pacmanCol + blinkyToPacmanCol;

            Point2D nearestBlock = getNearestNonWallToTarget(targetRow, targetCol);
            targetRow = (int) nearestBlock.getX();
            targetCol = (int) nearestBlock.getY();
            if (distance(inkyRow, inkyCol, targetRow, targetCol) < 1) {

                findPath(inkyRow, inkyCol, pacmanRow, pacmanCol);
            }
            else {
                findPath(inkyRow, inkyCol, targetRow, targetCol);
            }

            setReachedDestination(false);

        }

        moveToTarget(getPath().get(getPath().size() - 2).getRow(), getPath().get(getPath().size() - 2).getCol());


    }

    private Point2D getNearestNonWallToTarget(int targetRow, int targetCol) {
        double min = 1000;
        int minIndexI = 0;
        int minIndexJ = 0;
        Maze maze = getPacman().getMaze();
        int row = maze.getRow();
        int col = maze.getCol();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (maze.getMaze()[i][j] == 0) {
                    if (distance(i, j, targetRow, targetCol) < min) {
                        min =  distance(i, j, targetRow, targetCol);
                        minIndexI = i;
                        minIndexJ = j;
                    }
                }
            }
        }
        return new Point2D(minIndexI, minIndexJ);
    }
}
