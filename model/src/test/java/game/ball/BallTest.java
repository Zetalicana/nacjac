package game.ball;

import game.Game;
import game.HitBox;
import game.Maze;
import game.Pacman;
import game.ghost.Blinky;
import game.ghost.Ghost;
import game.maze.processor.MazeProcessor;
import game.state.GhostState;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import player.Player;
import point.Point2D;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class BallTest {
    private Ball dragonBall;
    private Ball normalBall;
    private Game game;
    private Pacman pacman;
    private Ghost blinky;
    private List<Ghost> ghosts;
    private MazeProcessor mazeProcessor;
    private Maze cleanMaze;
    private Player player;

    @Before
    public void setUp() {
        player = new Player();
        player.setName("test");
        ghosts = new ArrayList<>();
        mazeProcessor = new MazeProcessor();
        dragonBall = new DragonBall(1, 5);
        normalBall = new NormalBall(1, 15);
        dragonBall.init();
        normalBall.init();
        game = new Game(player);
        pacman = new Pacman();
        pacman.init();
        cleanMaze = mazeProcessor.getCleanMaze();
        pacman.setMaze(cleanMaze);
        blinky = new Blinky();
        blinky.setPacman(pacman);
        blinky.init();
        blinky.setGame(game);
        ghosts.add(blinky);
        dragonBall.setGame(game);
        dragonBall.setPacman(pacman);
        dragonBall.setGhosts(ghosts);
        normalBall.setGame(game);
        normalBall.setPacman(pacman);
        normalBall.setGhosts(ghosts);
        blinky.setGhostState(GhostState.DEAD);
    }

    @Test
    public void update() {

        dragonBall.setEated(true);
        pacman.setHitBox(new HitBox(25, 20, 20, 20));
        dragonBall.update();
        Assert.assertEquals(0, game.getScore());

        dragonBall.setEated(false);
        dragonBall.update();
        Assert.assertEquals(50, game.getScore());
        Assert.assertEquals(GhostState.DEAD, blinky.getGhostState());

        dragonBall.setEated(false);
        blinky.setGhostState(GhostState.CHASE);
        dragonBall.update();
        Assert.assertEquals(GhostState.VULNERABLE, blinky.getGhostState());

        blinky.setGhostState(GhostState.CHASE);
        dragonBall.setEated(true);
        Assert.assertEquals(GhostState.CHASE, blinky.getGhostState());


        dragonBall.setEated(false);
        pacman.setHitBox(new HitBox(50, 20, 20, 20));
        game.setScore(0);
        dragonBall.update();
        Assert.assertEquals(0, game.getScore());


        game.setScore(0);
        normalBall.setEated(true);
        pacman.setHitBox(new HitBox(220, 20, 20, 20));
        normalBall.update();
        Assert.assertEquals(0, game.getScore());

        normalBall.setEated(false);
        normalBall.update();
        Assert.assertEquals(10, game.getScore());

        pacman.setHitBox(new HitBox(260, 20, 20, 20));
        normalBall.setEated(false);
        normalBall.update();
        Assert.assertEquals(10, game.getScore());


    }
}