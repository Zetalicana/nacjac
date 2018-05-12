package manager;


import game.*;
import game.ball.Ball;
import game.ghost.*;
import game.state.GameState;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.Getter;
import lombok.Setter;
import game.maze.node.GraphBuilder;

import matrix.Matrix;
import model.AStarPathAlgorithm;


import model.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import game.maze.processor.MazeProcessor;

import player.Player;
import player.dao.PlayerDAOImpl;
import point.Point2D;
import view.*;

import java.util.ArrayList;
import java.util.List;

public class GameManager {
    private static Logger logger = LoggerFactory.getLogger(Game.class);
    private final int HEIGHT = 1000;
    private final int WIDTH = 1000;
    private boolean pathOn = true;
    private GraphicsContext graphicsContext;
    @Getter @Setter
    private static Stage gameStage;
    private MazeProcessor mazeProcessor;
    private List<WallBlock> wallBlocks;
    private List<Ball> balls;
    private Pacman pacman;
    private Maze cleanMaze;
    private AStarPathAlgorithm aStarPathAlgorithm;
    private Matrix<Node> matrix;
    private GraphBuilder graphBuilder;
    private List<Point2D> path;
    private List<Ghost> ghosts;
    private Game game;

    private BallView ballView;
    private WallBlockView wallBlockView;
    private PacmanView pacmanView;
    private GhostView ghostView;
    private FontView fontView;


    private AnimationTimer gameAnimationTimer = new AnimationTimer() {
        @Override
        public void handle(long now) {
            update();
            draw();
        }
    };


    public GameManager(Player player) {
        game = new Game(player);
        pacman = new Pacman();
        aStarPathAlgorithm = new AStarPathAlgorithm();
        mazeProcessor = new MazeProcessor();

        graphBuilder = new GraphBuilder();
        matrix = new Matrix<>();
        pacmanView = new PacmanView();
        wallBlockView = new WallBlockView();
        ballView = new BallView();
        ghostView = new GhostView();
        ghosts = new ArrayList<>();
        fontView = new FontView();
    }


    public void start() {
        game.setGameState(GameState.INITIALIZE);
        logger.debug("GameState set to {}", game.getGameState());
        initializeGameStage();
        gameAnimationTimer.start();



    }

    public void keyListener(Scene scene) {
        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            switch (key.getCode()) {
                case UP:    pacman.setDesiredDirection(new Point2D(0, -1)); break;
                case DOWN:  pacman.setDesiredDirection(new Point2D(0, 1));break;
                case LEFT:  pacman.setDesiredDirection(new Point2D(-1, 0));break;
                case RIGHT: pacman.setDesiredDirection(new Point2D(1, 0)); break;
                case NUMPAD1: game.setGameState(GameState.PLAY);break;
                case NUMPAD2: game.setGameState(GameState.RESET);break;
                case NUMPAD3: game.setGameState(GameState.LEVEL_CLEARED);break;
                case NUMPAD5: if (game.isShowPath()){game.setShowPath(false);} else game.setShowPath(true);break;
                case NUMPAD6: game.setShowPath(false);break;
            }
        });
    }



    public void initializeGameStage() {
        gameStage = new Stage();
        gameStage.setTitle("Neo Armstrong Cyclone Jet Armstrong Cannon");
        Group root = new Group();
        Scene gameScene = new Scene(root);
        gameStage.setScene(gameScene);

        Canvas gameCanvas = new Canvas(28 * WallBlock.BLOCKSIZE, 31 * WallBlock.BLOCKSIZE + 30);
        root.getChildren().add(gameCanvas);
        graphicsContext = gameCanvas.getGraphicsContext2D();
        gameStage.setResizable(false);
        gameStage.initStyle(StageStyle.UTILITY);
        gameStage.initModality(Modality.APPLICATION_MODAL);
        gameStage.show();
        gameStage.setOnCloseRequest(event -> {
            gameAnimationTimer.stop();
            PlayerDAOImpl playerDAO = new PlayerDAOImpl();
            playerDAO.savePlayer(game.getPlayer());
            System.out.println("player" + game.getPlayer().getName());
            gameStage.close();
        });
        logger.info("GameStage initialized.");
    }

    public void update() {
        switch (game.getGameState()) {
            case INITIALIZE: init(); break;
            case PLAY: updatePlaying();break;
            case RESET: reset(); break;
            case LEVEL_CLEARED: hardReset(); break;
            case GAME_OVER: gameAnimationTimer.stop();break;
        }
    }

    public void init() {
        cleanMaze = mazeProcessor.getCleanMaze();

        matrix = graphBuilder.buildCrossroads(cleanMaze);
        graphBuilder.buildEdges(matrix);

        wallBlocks = mazeProcessor.getWallBlocks();
        wallBlockView.setWallBlockList(wallBlocks);
        balls = mazeProcessor.getBalls();
        for (Ball ball : balls) {
            ball.init();
            ball.setPacman(pacman);
            ball.setGame(game);
        }
        game.setBallsCount(balls.size());
        logger.info("Balls count {}",game.getBallsCount());
        ballView.setBalls(balls);
        pacman.init();
        pacman.setMaze(cleanMaze);
        pacmanView.setPacman(pacman);
        Ghost blinky = new Blinky();
        Ghost inky = new Inky(blinky);
        Ghost pinky = new Pinky();
        Ghost clyde = new Clyde();
        ghosts.add(blinky);
        ghosts.add(inky);
        ghosts.add(pinky);
        ghosts.add(clyde);
        for (Ghost ghost : ghosts) {
            ghost.setPacman(pacman);
            ghost.init();
            ghost.setGame(game);
        }
        for (Ball ball : balls) {
            ball.setGhosts(ghosts);
        }
        ghostView.setGhosts(ghosts);
        //aStarPathAlgorithm.findPath(matrix.getValue(1, 10), matrix.getValue(5, 10));
        //path = aStarPathAlgorithm.generatePath(matrix.getValue(1, 30), matrix.getValue(29, 5));
        /*for (Point2D a : path) {
            System.out.println(a.toString());
        }*/

        keyListener(gameStage.getScene());
        game.setGameState(GameState.PLAY);
        logger.debug("GameState set to {}", game.getGameState());
    }

    public void updatePlaying() {
        pacman.updatePlaying();
        for (Ball ball : balls) {
            ball.update();
        }
        for (Ghost ghost : ghosts) {
            ghost.update();
        }
    }
    public void reset() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        pacman.reset();
        for (Ghost ghost : ghosts) {
            ghost.reset();
            ghost.init();
        }
        game.setGameState(GameState.PLAY);
        logger.info("Reset is done");
        logger.info("Game state set to {}", game.getGameState());
    }

    public void hardReset() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        pacman.reset();
        for (Ball ball : balls) {
            ball.hardReset();
        }
        logger.info("Number of ball is {}",balls.size());
        game.setBallsCount(balls.size());
        for (Ghost ghost : ghosts) {
            ghost.reset();
            ghost.init();
        }
        game.reset();
        game.setGameState(GameState.PLAY);
        logger.info("Hard reset is done");
        logger.info("GameState set to {}", game.getGameState());
    }

    public void draw() {
        graphicsContext.setFill(Color.BLACK);
        graphicsContext.clearRect(0, 0, WIDTH, HEIGHT);
        graphicsContext.fillRect(0, 0, WIDTH, HEIGHT);
        wallBlockView.draw(graphicsContext);
        ballView.draw(graphicsContext);
        pacmanView.draw(graphicsContext);
        ghostView.draw(graphicsContext);
        fontView.draw(graphicsContext, game);
    }
}