package game;

import game.maze.processor.MazeProcessor;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import point.Point2D;

import static org.junit.Assert.*;

public class PacmanTest {
    Pacman pacman;
    MazeProcessor mazeProcessor;
    Maze maze;

    @Before
    public void setUp() {
        mazeProcessor = new MazeProcessor();
        maze = mazeProcessor.getCleanMaze();
        pacman = new Pacman(0, 0, true, new HitBox(), new Point2D(21, 20), new Point2D(-1, 0), maze, new Point2D(0, -1), 0);
    }

    @Test
    public void updatePlaying() {

        pacman.updatePlaying();

        Assert.assertEquals(1, pacman.getRow());
        Assert.assertEquals(5, pacman.getCol());
        Assert.assertEquals(new Point2D(0, -1), pacman.getDesiredDirection());
        Assert.assertEquals(new Point2D(-1, 0), pacman.getVelocity());

        pacman.setDesiredDirection(new Point2D(0, -1));
        pacman.setPosition(new Point2D(393, 440));
        pacman.setVelocity(new Point2D(-1, 0));
        pacman.updatePlaying();

        Assert.assertEquals(new Point2D(392, 440), pacman.getPosition());

        for (int i = 0; i < 12; i++) {
            pacman.updatePlaying();
        }

        Assert.assertEquals(new Point2D(380, 440), pacman.getPosition());
        Assert.assertEquals(new Point2D(-1, 0), pacman.getVelocity());
        pacman.updatePlaying();
        pacman.updatePlaying();
        Assert.assertEquals(new Point2D(0, -1), pacman.getDesiredDirection());
        pacman.updatePlaying();
        Assert.assertEquals(new Point2D(380, 440), pacman.getPosition());

        for (int i = 0; i < 19; i++) {
            pacman.updatePlaying();
        }
        Assert.assertEquals(new Point2D(380, 440), pacman.getPosition());
        pacman.setDesiredDirection(new Point2D(0, 1));
        pacman.updatePlaying();
        Assert.assertEquals(new Point2D(380, 441), pacman.getPosition());

    }
}