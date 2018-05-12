package game.maze.processor;


import game.*;
import game.ball.Ball;
import game.ball.DragonBall;
import game.ball.NormalBall;
import game.maze.parser.MazeParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code MazeProcessor} class processing the raw {@code Maze}
 * that was emitted by the {@code MazeParser}.
 */
public class MazeProcessor {
    private static Logger logger = LoggerFactory.getLogger(MazeProcessor.class);

    private Maze rawMaze;

    public MazeProcessor() {
        rawMaze = new MazeParser().parser();
    }

    /**
     * Remove all unnecessary data from {@code rawMaze}.
     * @return {@code Maze} which only hold zeroes and ones.
     */
    public Maze getCleanMaze() {
        int[][] maze = new int[rawMaze.getRow()][rawMaze.getCol()];
        for (int i = 0; i < rawMaze.getRow(); i++) {
            for (int j = 0; j < rawMaze.getCol(); j++) {
                if (rawMaze.getMaze()[i][j] == 2 ||rawMaze.getMaze()[i][j] == 3) {
                    maze[i][j] = 0;
                }
                else if (rawMaze.getMaze()[i][j] == 1) {
                    maze[i][j] = 1;
                }
                else if (rawMaze.getMaze()[i][j] == 4){maze[i][j] = 4;}
            }
        }
        return new Maze(rawMaze.getRow(), rawMaze.getCol(), maze);
    }

    /**
     * Get the {@code WallBlock}s position from the {@code rawMaze}.
     * @return a list with the {@code WallBlock}s
     */
    public List<WallBlock> getWallBlocks() {
        List<WallBlock> wallBlockList = new ArrayList<>();
        for (int i = 0; i < rawMaze.getRow(); i++) {
            for (int j = 4; j < rawMaze.getCol() - 4; j++) {
                if (rawMaze.getMaze()[i][j] == 1) {
                    wallBlockList.add(new WallBlock(i, j));
                }
            }
        }
        return wallBlockList;
    }

    /**
     * Get the @code Ball}s position from the {@code rawMaze}.
     * @return a list with the {@code Ball}s
     */
    public List<Ball> getBalls() {
        List<Ball> balls = new ArrayList<>();
        for (int i = 0; i < rawMaze.getRow(); i++) {
            for (int j = 0; j < rawMaze.getCol(); j++) {
                if (rawMaze.getMaze()[i][j] == 2) {
                    Ball normalBall = new NormalBall(i, j);
                    balls.add(normalBall);
                }
                else if (rawMaze.getMaze()[i][j] == 3) {
                    Ball dragonBall = new DragonBall(i, j);
                    balls.add(dragonBall);
                }
            }
        }
        return balls;
    }
}
