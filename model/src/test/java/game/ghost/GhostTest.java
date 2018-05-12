package game.ghost;

import game.Game;
import game.Maze;
import game.Pacman;
import game.maze.processor.MazeProcessor;
import game.state.GameState;
import game.state.GhostState;
import javafx.stage.PopupWindow;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import point.Point2D;

import static org.junit.Assert.*;

public class GhostTest {

    private Ghost blinky;
    private Ghost clyde;
    private Ghost inky;
    private Ghost pinky;
    private Pacman pacman;
    private Game game;
    private MazeProcessor mazeProcessor;
    private Maze cleanMaze;

    @Before
    public void setUp() {
        mazeProcessor = new MazeProcessor();
        pacman = new Pacman();
        pacman.init();
        cleanMaze = mazeProcessor.getCleanMaze();
        pacman.setMaze(cleanMaze);
        game = new Game();
        blinky = new Blinky();
        blinky.setPacman(pacman);
        blinky.init();
        blinky.setGame(game);
        clyde = new Clyde();
        clyde.setPacman(pacman);
        clyde.init();
        clyde.setGame(game);
        inky = new Inky(blinky);
        inky.setPacman(pacman);
        inky.init();
        inky.setGame(game);
        pinky = new Pinky();
        pinky.setPacman(pacman);
        pinky.init();
        pinky.setGame(game);
    }

    @Test
    public void update() {

        Assert.assertEquals(13, blinky.getRow());
        Assert.assertEquals(16, blinky.getCol());
        Assert.assertEquals(GhostState.CAGE, blinky.getGhostState());

        blinky.setRow(11);
        blinky.updatePosition();
        blinky.setGhostState(GhostState.CHASE);
        blinky.update();
        Assert.assertEquals(false, blinky.isReachedDestination());

        blinky.initVulnerable();
        Assert.assertEquals(GhostState.VULNERABLE, blinky.getGhostState());

        blinky.setVelocity(new Point2D(0, -1));
        blinky.setPosition(new Point2D(420,19));
        blinky.update();
        Assert.assertEquals(new Point2D(420, 19.5f),blinky.getPosition());

        blinky.setVulnerableTime(-1);
        blinky.setPosition(new Point2D(420,19));
        blinky.update();
        Assert.assertEquals(GhostState.VULNERABLE, blinky.getGhostState());
        Assert.assertEquals(false, blinky.isOnCell());

        blinky.update();
        Assert.assertEquals(GhostState.CHASE, blinky.getGhostState());

        blinky.setScatteredTime(1500);
        blinky.update();
        Assert.assertEquals(GhostState.SCATTERED, blinky.getGhostState());

        blinky.setPosition(new Point2D(420,20));
        blinky.update();
        Assert.assertEquals(false, blinky.isReachedDestination());

        blinky.setGhostState(GhostState.CHASE);
        blinky.setReachedDestination(false);
        blinky.setRow(14);
        blinky.setCol(8);
        blinky.updatePosition();
        Assert.assertEquals(new Point2D(80, 280), blinky.getPosition());
        Assert.assertEquals(true, blinky.isOnCell());
        Assert.assertEquals(GhostState.CHASE, blinky.getGhostState());

        blinky.setReachedDestination(true);
        blinky.setVelocity(new Point2D(-1, 0));
        pacman.setRow(14);
        pacman.setCol(7);
        Point2D temp = new Point2D(blinky.getPosition().getX() -1, blinky.getPosition().getY());
        blinky.update();
        Assert.assertEquals(temp, blinky.getPosition());

        blinky.setCol(1);
        blinky.updatePosition();
        blinky.update();
        Assert.assertEquals(33, blinky.getCol());


        blinky.setPosition(new Point2D(519, 20));
        blinky.updateArrayPosition();
        blinky.setVelocity(new Point2D(1, 0));
        blinky.setGhostState(GhostState.DEAD);
        blinky.update();
        Assert.assertEquals(new Point2D(520, 20), blinky.getPosition());
        blinky.update();
        Assert.assertEquals(new Point2D(520, 21), blinky.getPosition());

        clyde.setCol(5);
        clyde.setRow(1);
        clyde.updatePosition();
        pacman.setCol(19);
        pacman.setRow(1);
        clyde.setGhostState(GhostState.CHASE);
        temp = new Point2D(clyde.getPosition().getX() + 1, clyde.getPosition().getY());
        clyde.update();
        Assert.assertEquals(temp, clyde.getPosition());
        clyde.setCol(6);
        clyde.setRow(1);
        clyde.updatePosition();
        clyde.setReachedDestination(true);
        pacman.setCol(8);
        pacman.setRow(1);
        pacman.setPosition(new Point2D(80, 20));
        temp = new Point2D(clyde.getPosition().getX() - 1, clyde.getPosition().getY());
        clyde.update();
        Assert.assertEquals(temp, clyde.getPosition());

        pinky.setGhostState(GhostState.CHASE);
        pacman.setCol(13);
        pacman.setRow(20);
        pinky.setRow(25);
        pinky.setCol(13);
        pinky.updatePosition();
        pacman.setVelocity(new Point2D(0, -1));
        temp = new Point2D(pinky.getPosition().getX(),pinky.getPosition().getY() - 1);
        pinky.update();
        Assert.assertEquals(temp, pinky.getPosition());
        for (int i = 0; i < 39; i++) {
            pinky.update();
        }
        Assert.assertEquals(new Point2D(180, 460), pinky.getPosition());
        pinky.update();
        Assert.assertEquals(new Point2D(180, 460), pinky.getPosition());
    }
}