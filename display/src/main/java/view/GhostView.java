package view;



import game.WallBlock;
import game.ghost.Blinky;
import game.ghost.Ghost;
import game.ghost.Inky;
import game.ghost.Pinky;
import game.state.GhostState;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import lombok.Getter;
import lombok.Setter;
import model.Node;

import java.util.List;

@Getter
@Setter
public class GhostView {

    private List<Ghost> ghosts;
    private boolean showPath;

    public void draw(GraphicsContext graphicsContext) {
        for (Ghost ghost : ghosts) {
                if (!ghost.isAlive()) {
                    graphicsContext.setGlobalAlpha(0.5);
                }
                else {graphicsContext.setGlobalAlpha(1.0);}
                if (ghost instanceof Blinky) {
                    graphicsContext.setFill(Color.RED);
                    graphicsContext.setStroke(Color.RED);
                } else if (ghost instanceof Inky) {
                    graphicsContext.setFill(Color.TURQUOISE);
                    graphicsContext.setStroke(Color.TURQUOISE);
                } else if (ghost instanceof Pinky) {
                    graphicsContext.setFill(Color.PINK);
                    graphicsContext.setStroke(Color.PINK);
                } else {
                    graphicsContext.setFill(Color.ORANGE);
                    graphicsContext.setStroke(Color.ORANGE);
                }

                if (ghost.getGhostState() == GhostState.VULNERABLE ) {
                    if (Math.floor(ghost.getVulnerableTime() / 30) %2 == 0) {
                        graphicsContext.setFill(Color.BLUE);
                    }
                    else {
                        graphicsContext.setFill(Color.WHITE);
                    }
                }

                float x = ghost.getPosition().getX();
                float y = ghost.getPosition().getY();
                graphicsContext.setLineWidth(1.5);
                List<Node> path = ghost.getPath();
                if (path != null && isShowPath()) {
                    for (int i = 1; i < path.size(); i++) {
                        float x1 = path.get(i - 1).getCol() * WallBlock.BLOCKSIZE - (4 * WallBlock.BLOCKSIZE);
                        float y1 = path.get(i - 1).getRow() * WallBlock.BLOCKSIZE;
                        float x2 = path.get(i).getCol() * WallBlock.BLOCKSIZE - (4 * WallBlock.BLOCKSIZE);
                        float y2 = path.get(i).getRow() * WallBlock.BLOCKSIZE;

                        graphicsContext.strokeLine(x1 + 10, y1 + 10, x2 + 10, y2 + 10);
                    }
                }
                graphicsContext.fillRect(x, y, WallBlock.BLOCKSIZE, WallBlock.BLOCKSIZE);
                //graphicsContext.setStroke(Color.WHITE);
                //graphicsContext.strokeRect(ghost.getHitBox().getX(), ghost.getHitBox().getY(), WallBlock.BLOCKSIZE, WallBlock.BLOCKSIZE);
                graphicsContext.setGlobalAlpha(1.0);
            }

    }
}
