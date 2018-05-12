package view;

import game.Game;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import static javafx.scene.text.TextAlignment.LEFT;

public class FontView {

    public void draw(GraphicsContext graphicsContext, Game game) {
        drawLife(graphicsContext, game);
        drawScore(graphicsContext, game);
        drawHighScore(graphicsContext, game);

    }

    public void drawHighScore(GraphicsContext graphicsContext, Game game) {
        graphicsContext.setFont(new Font("Default", 20));
        graphicsContext.setFill(Color.GHOSTWHITE);
        graphicsContext.setTextAlign(LEFT);
        graphicsContext.fillText("HIGH SCORE:" + Integer.toString(game.getHighScore()), 370, 643, 200);
    }

    public void drawScore(GraphicsContext graphicsContext, Game game
    ) {
        graphicsContext.setFont(new Font("Default", 20));
        graphicsContext.setFill(Color.GHOSTWHITE);
        graphicsContext.setTextAlign(LEFT);
        graphicsContext.fillText("SCORE:" + Integer.toString(game.getScore()), 150, 643, 100);
    }

    public void drawLife(GraphicsContext graphicsContext, Game game) {
        graphicsContext.setFill(Color.GOLD);
        for (int i = 0; i < game.getLife(); i++) {
            graphicsContext.fillOval( i * 23, 625, 20, 20);
        }

    }
}
