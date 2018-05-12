package view;


import game.WallBlock;

import game.ball.Ball;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class BallView {
    private final int width = WallBlock.BLOCKSIZE;
    private final int height = WallBlock.BLOCKSIZE;
    private final Color color = Color.ORANGE;
    private List<Ball> balls;


    public void draw(GraphicsContext graphicsContext) {
        graphicsContext.setFill(color);
        for (Ball ball : balls) {
            if (!ball.isEated()) {
                float x = ball.getPosition().getX();
                float y = ball.getPosition().getY();
                int ballSize = ball.getBallSize();
                graphicsContext.fillOval(x, y, ballSize, ballSize);
            }

        }
    }
/*
    public double calcBallX(Actor actor) {
        return ((actor.getCol() * WallBlock.BLOCKSIZE - (4 * WallBlock.BLOCKSIZE)) + ((WallBlock.BLOCKSIZE - ballSize) / 2) );
    }

    public double calcBallY(Actor actor) {
        return ((actor.getRow() * WallBlock.BLOCKSIZE) + ((WallBlock.BLOCKSIZE - ballSize) / 2));
    }

    public double calcDragonBallX(Actor actor) {
        return ((actor.getCol() * WallBlock.BLOCKSIZE - (4 * WallBlock.BLOCKSIZE)) + ((WallBlock.BLOCKSIZE - dragonBallSize) / 2));
    }

    public double calcDragonBallY(Actor actor) {
        return ((actor.getRow() * WallBlock.BLOCKSIZE) + ((WallBlock.BLOCKSIZE - dragonBallSize) / 2));
    }
    */
}
