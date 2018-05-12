package game;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * {@code WallBlock} class representing position and size of a wall element from {@code Maze}.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WallBlock {
    /**
     * Size of the wall.
     */
    public final static int BLOCKSIZE = 20;
    private int row;
    private int col;
}
