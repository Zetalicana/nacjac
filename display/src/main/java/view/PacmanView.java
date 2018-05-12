package view;


import game.Pacman;
import game.WallBlock;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class PacmanView {
    Pacman pacman;
    private Color color = Color.GOLD;

    public PacmanView(Pacman pacman) {
        this.pacman = pacman;
    }

    public void draw(GraphicsContext graphicsContext) {
        float x = pacman.getPosition().getX();
        float y = pacman.getPosition().getY();
        graphicsContext.setFill(color);
        graphicsContext.fillOval(x, y, WallBlock.BLOCKSIZE, WallBlock.BLOCKSIZE);
        //drawCollider(graphicsContext);
    }

    public void drawCollider(GraphicsContext graphicsContext) {
        float x = pacman.getHitBox().getX();
        float y = pacman.getHitBox().getY();
        float height = pacman.getHitBox().getHeight();
        float width = pacman.getHitBox().getWidth();
        graphicsContext.setStroke(Color.RED);
        graphicsContext.strokeRect(x, y, height, width);
    }
}
