package game.maze.parser;

import game.Maze;
import javafx.application.Platform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.Scanner;

/**
 * {@code MazeParser} class provides parser to get {@code Maze} from a TXT.
 */
public class MazeParser {
    private static Logger logger = LoggerFactory.getLogger(MazeParser.class);
    private final String fileName= "/maze.txt";

    /**
     * Parsing the {@code Maze} from a file.
     * If the file cannot be found then the program will be terminated.
     * @return {@code Maze}
     */
    public Maze parser() {
        InputStream inputStream = getClass().getResourceAsStream(this.fileName);
        if (inputStream == null) {
            logger.error("Cannot be found resource file {} .", this.fileName);
            logger.error("The application has been closed.");
            Platform.exit();
        }

        logger.info("File scanning has begun.");
        Scanner in = new Scanner(inputStream);
        int row = in.nextInt();
        int col = in.nextInt();
        int[][] maze = new int[row][col];
        for (int i = 0; i < row && in.hasNextInt(); i++) {
            for (int j = 0; j < col && in.hasNextInt(); j++) {
                maze[i][j] = in.nextInt();
            }
        }
        in.close();
        logger.info("File scanning has been completed.");

        return new Maze(row, col, maze);
    }

}
