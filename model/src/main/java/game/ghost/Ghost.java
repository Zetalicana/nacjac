package game.ghost;


import game.*;
import game.maze.node.GraphBuilder;
import game.state.GhostState;
import lombok.Getter;
import lombok.Setter;
import matrix.Matrix;
import model.AStarPathAlgorithm;
import model.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import point.Point2D;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * {@code Ghost} class represent the enemy in the game.
 * It has four subclasses.
 */
@Setter
@Getter
public class Ghost {
    private static Logger logger = LoggerFactory.getLogger(Ghost.class);

    private int row;
    private int col;
    private boolean alive;

    private Point2D position;
    private Point2D velocity;

    private GhostState ghostState;
    private HitBox hitBox;


    private Game game;
    private Pacman pacman;

    private List<Node> path;
    private GraphBuilder graphBuilder;
    private Matrix<Node> originalCrossNodes;
    private AStarPathAlgorithm aStarPathAlgorithm;

    private boolean enterVulnerable = true;
    private boolean backToCage = false;
    private boolean reachedDestination  = true;

    private int cageTime;
    private int scatteredTime;
    private int vulnerableTime;

    private int cageRow;
    private int cageCol;

    private int startRow;
    private int startCol;

    /**
     * Based on the current {@code ghostState} update the ghost behavior.
     */
    public void update() {
        switch (ghostState) {
            case CAGE: cage();break;
            case DEAD: dead();break;
            case CHASE: chase();break;
            case SCATTERED: scattered();break;
            case VULNERABLE: vulnerable();break;
        }
    }

    /**
     * Initialize the required components.
     */
    public void init() {
        aStarPathAlgorithm = new AStarPathAlgorithm();
        graphBuilder = new GraphBuilder();
        originalCrossNodes = graphBuilder.buildCrossroads(pacman.getMaze());
        graphBuilder.buildEdges(originalCrossNodes);
        int x = (int) position.getX();
        int y = (int) position.getY();
        hitBox = new HitBox(x, y, 20, 20);
        alive = true;
        updateHitbox();
    }

    /**
     * When {@code Pacman} collide with a {@code DragonBall}
     * this method invoke to set up the {@link game.state.GhostState#VULNERABLE} state.
     */
    public void initVulnerable() {
        vulnerableTime = 480;
        this.setGhostState(GhostState.VULNERABLE);
        if (isEnterVulnerable()) {
            pacman.setKillingSpree(1);
        }
    }

    /**
     * Reset the ghosts to their specific starting states.
     */
    public void reset() {
        ghostState = GhostState.CAGE;
        if( path != null) {path.clear();}
        reachedDestination = true;
        resetCageTime();
        //updateArrayPosition();
    }

    protected void chase() {
        chaseOrFear();
        move();
        checkCollision();
        updateHitbox();
    }

    protected void scattered() {
    }

    protected void vulnerable() {
        if (isEnterVulnerable()) {
            this.getVelocity().negate();
            float x = this.getVelocity().getX();
            float y = this.getVelocity().getY();
            this.getVelocity().setXY(x * 1/2, y * 1/2);
            setEnterVulnerable(false);
        }

        this.getPosition().addTogether(this.getVelocity());

        for (int row = 0; row < originalCrossNodes.getRows(); row++) {
            for (int col = 0; col < originalCrossNodes.getColumns(); col++) {
                if (originalCrossNodes.getValue(row, col) != null) {
                    float nodeX = originalCrossNodes.getValue(row,col).getCol() * WallBlock.BLOCKSIZE - (4 * WallBlock.BLOCKSIZE);
                    float nodeY = originalCrossNodes.getValue(row,col).getRow() * WallBlock.BLOCKSIZE;
                    if (nodeX == this.getPosition().getX() && nodeY == this.getPosition().getY()) {
                        this.setVelocity(findDirection(row, col));
                    }
                }
            }
        }
        updateHitbox();
        checkCollision();
        vulnerableTime--;
        checkVulnerableState();

    }
    protected void move() {
        if (reachedDestination) {
            int pacmanRow = pacman.getRow();
            int pacmanCol = pacman.getCol();
            int ghostRow = this.getRow();
            int ghostCol = this.getCol();
            findPath(ghostRow, ghostCol, pacmanRow, pacmanCol);
            reachedDestination = false;

            //logger.debug("GhostRow {} GhostCol {} PathRow{} PathCol {}", ghostData.getRow(), ghostData.getCol(), path.get(path.size() - 1).getRow(), path.get(path.size() -1 ).getCol());
        }
        moveToTarget(path.get(path.size() - 2).getRow(), path.get(path.size()-2).getCol());

    }

    protected void updatePosition() {
        int x = col * WallBlock.BLOCKSIZE - (4 * WallBlock.BLOCKSIZE);
        int y = row * WallBlock.BLOCKSIZE;
        position = new Point2D(x, y);
    }
    private Node createNode(int row, int col) {
        Node node = new Node();
        node.setRow(row);
        node.setCol(col);
        return node;
    }

    protected void findPath(int startRow, int startCol, int endRow, int endCol) {
        /*int pacmanRow = pacman.getPacmanData().getRow();
        int pacmanCol = pacman.getPacmanData().getCol();
        int ghostRow = ghostData.getRow();
        int ghostCol = ghostData.getCol();*/
        Node startNode = createNode(startRow, startCol);
        Node endNode = createNode(endRow, endCol);
        Matrix<Node> clone = graphBuilder.buildCrossroads(pacman.getMaze());
        clone.setValue(startRow, startCol, startNode);
        clone.setValue(endRow, endCol, endNode);
        graphBuilder.buildEdges(clone);
        if (!(startRow == endRow && startCol == endCol))
            path = aStarPathAlgorithm.generatePath(startNode, endNode);

    }

    protected void checkCollision() {
        if (HitBox.intersect(pacman.getHitBox(), this.getHitBox()) && this.isAlive()) {
            if (ghostState ==  GhostState.CHASE || ghostState == GhostState.SCATTERED) {
                game.decreasePacmanLife();
                game.getPlayer().setTotalDeaths(game.getPlayer().getTotalDeaths() + 1);
            }
            else if (ghostState == GhostState.VULNERABLE) {
                game.addScore(100 * pacman.getKillingSpree());
                logger.info("added {} to score", pacman.getKillingSpree() * 100);
                int killingSpree = pacman.getKillingSpree();
                pacman.setKillingSpree(++killingSpree);
                this.setAlive(false);
                updateArrayPosition();
                ghostState = GhostState.DEAD;
                reachedDestination = true;
            }
        }
    }

    protected void updateArrayPosition() {
        int row = (int) Math.floor(position.getY() / WallBlock.BLOCKSIZE);
        int col = (int) Math.floor((position.getX() + (4 * WallBlock.BLOCKSIZE)) / WallBlock.BLOCKSIZE);
        this.row = row;
        this.col = col;
    }

    protected void moveToTarget(int row, int col) {
        float currentRow = this.getRow();
        float currentCol = this.getCol();
        float targetRow =  row ;
        float targetCol =  col ;

        if (targetCol < 9 && targetRow == 14 && this.getVelocity().getX() == -1) {
            targetCol = 1;
        }
        else if (targetCol > 25 && targetRow == 14 && this.getVelocity().getX() == 1) {
            targetCol = 34;
        }
        float x = targetCol - currentCol;
        if(x > 0) {
            x = 1;
        }
        else if (x < 0) {
            x = -1;
        }
        float y = targetRow - currentRow;
        if(y > 0) {
            y = 1;
        }
        else if (y < 0) {
            y = -1;
        }
        this.setVelocity(new Point2D(x, y));
        if (this.getPosition().getX() == ((targetCol * 20) - 80) && this.getPosition().getY() == targetRow * 20) {
            reachedDestination = true;
            updateArrayPosition();
            if (targetCol == 1) {
                int ghostY = (int) this.getPosition().getY();
                int ghostX =  (33 * WallBlock.BLOCKSIZE - (4 * WallBlock.BLOCKSIZE));
                this.setPosition(new Point2D(ghostX, ghostY));
                updateArrayPosition();
            }
            else if (targetCol == 34) {
                int ghostY = (int) this.getPosition().getY();
                int ghostX =  (2 * WallBlock.BLOCKSIZE - (4 * WallBlock.BLOCKSIZE));
                this.setPosition(new Point2D(ghostX, ghostY));
                updateArrayPosition();
            }

        }
        else {
            float x1 = this.getPosition().getX() + this.getVelocity().getX();
            float y1 = this.getPosition().getY() + this.getVelocity().getY();
            this.setPosition(new Point2D(x1, y1));
        }
    }

    protected void updateHitbox() {
        this.getHitBox().setX(this.getPosition().getX());
        this.getHitBox().setY(this.getPosition().getY());
    }

    private Point2D findDirection(int row, int col) {
        Point2D velocity;
        List<Point2D> velocities = new ArrayList<>();
        for (Node edge : originalCrossNodes.getValue(row, col).getEdges()) {
            float targetX = edge.getCol() * WallBlock.BLOCKSIZE - (4 * WallBlock.BLOCKSIZE);
            float targetY = edge.getRow() * WallBlock.BLOCKSIZE;
            float currentX = this.getPosition().getX();
            float currentY = this.getPosition().getY();
            float x = targetX - currentX;

            if(x > 0) {
                x = 1;
            }
            else if (x < 0) {
                x = -1;
            }
            float y = targetY - currentY;
            if(y > 0) {
                y = 1;
            }
            else if (y < 0) {
                y = -1;
            }

            if (x != this.getVelocity().getX() && y != this.getVelocity().getY()) {
                velocities.add(new Point2D(x*1/2 , y *1/2));
            }
            else if (x == this.getVelocity().getX() && 0 == this.getVelocity().getY()) {
                velocities.add(new Point2D(x*1/2 , y *1 /2));
            }

        }
        Random rand = new Random();
        velocity = velocities.get(rand.nextInt(velocities.size()));
        return velocity;
    }

    protected void checkVulnerableState() {
        if (vulnerableTime <= 0 && isOnCell()) {
            setEnterVulnerable(true);
            float x = this.getVelocity().getX() * 2;
            float y = this.getVelocity().getY() * 2;
            this.getVelocity().setXY(x, y);
            updateArrayPosition();
            reachedDestination = true;
            this.setGhostState(GhostState.CHASE);
        }
    }

    protected boolean isOnCell() {
        if (this.getPosition().getX() % 20 == 0 && this.getPosition().getY() % 20 == 0) {
            return true;
        }
        return false;
    }




    protected void scatteredMove(int cornerRow, int cornerCol) {
        if (reachedDestination) {
            int ghostRow = this.getRow();
            int ghostCol = this.getCol();
            findPath(ghostRow, ghostCol, cornerRow, cornerCol);
            reachedDestination = false;
        }
        moveToTarget(path.get(path.size() - 2).getRow(), path.get(path.size()-2).getCol());
    }

    protected void chaseOrFear() {
        scatteredTime++;
        if (scatteredTime > 360 && isOnCell()) {
            this.setGhostState(GhostState.CHASE);
        }

        if (scatteredTime > 1000 && isOnCell()) {
            scatteredTime = 0;
            this.setGhostState(GhostState.SCATTERED);
        }
    }

    protected void dead() {
        deadMove();
    }


    private void cage() {
        if (!backToCage) {
            cageMove(cageRow, cageCol);
        }
        else cageMove(startRow, startCol);
        updateHitbox();

    }

    protected void cageMove(int endRow, int endCol) {

        if (!backToCage) {
            if (cageTime > 420)
                moveToTarget(endRow, endCol);
            cageTime++;
            if (isOnSpecificCell(endRow, endCol)) {

                this.setGhostState(GhostState.CHASE);
                resetCageTime();
                backToCage = true;
                updateArrayPosition();
                updateHitbox();
            }

        }
        else {
            moveToTarget(endRow, endCol);
            if (isOnSpecificCell(endRow, endCol)) {
                backToCage = false;
                updateArrayPosition();
                updateHitbox();
                this.setAlive(true);
                resetCageTime();
            }
        }
    }


    protected boolean isOnSpecificCell(int row, int col) {
        float ghostX = this.getPosition().getX();
        float ghostY = this.getPosition().getY();
        float cellX =  col * WallBlock.BLOCKSIZE - (4 * WallBlock.BLOCKSIZE);
        float cellY = row * WallBlock.BLOCKSIZE;
        if (ghostX == cellX && ghostY == cellY) {
            return true;
        }
        return false;
    }

    protected void resetCageTime() {
    }

    protected void deadMove() {
        if (!isOnCell()) {
            moveGhost();
        }
        else {
            updateArrayPosition();
            int ghostRow = this.getRow();
            int ghostCol = this.getCol();
            findPath(ghostRow, ghostCol, cageRow, cageCol);
            calcDirection();
            moveGhost();
        }
        if (this.getRow() == cageRow && this.getCol() == cageCol)  {

            this.setGhostState(GhostState.CAGE);
            reachedDestination = true;
        }
    }

    protected void moveGhost() {
        float x = this.getVelocity().getX();
        float y = this.getVelocity().getY();
        float x1 = this.getPosition().getX();
        float y1 = this.getPosition().getY();
        this.setPosition(new Point2D(x + x1, y + y1));
    }

    protected void calcDirection() {
        int row = path.get(path.size() - 2).getRow();
        int col = path.get(path.size() -2).getCol();
        float targetX = col * WallBlock.BLOCKSIZE - (4 * WallBlock.BLOCKSIZE);
        float targetY = row * WallBlock.BLOCKSIZE;
        float currentX = this.getPosition().getX();
        float currentY = this.getPosition().getY();
        float x = targetX - currentX;

        if(x > 0) {
            x = 1;
        }
        else if (x < 0) {
            x = -1;
        }
        float y = targetY - currentY;
        if(y > 0) {
            y = 1;
        }
        else if (y < 0) {
            y = -1;
        }
        this.setVelocity(new Point2D(x, y));

    }

    protected double distance(int row, int col, int row1, int col1) {
        int a = row1 - row;
        int b = col1 -col;
        return Math.sqrt(a * a + b * b);
    }
}
