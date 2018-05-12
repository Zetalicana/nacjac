package game;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * {@code Maze} class representing the game field.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Maze {
    private int row;
    private int col;
    private int[][] maze;

}
