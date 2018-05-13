package view;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import point.Point2D;

public class MenuAnimationView {

    private Canvas canvas;
    private GraphicsContext graphicsContext;

    private Point2D pacman;
    private Point2D blinky;
    private Point2D inky;
    private Point2D pinky;
    private Point2D clyde;
    private int width = 100;
    private int height = 100;
    private int counter;
    private boolean chase = true;
    private int chaseTime = 0;

    public MenuAnimationView(Canvas canvas, GraphicsContext graphicsContext) {
        this.canvas = canvas;
        this.graphicsContext = graphicsContext;
    }



    public void draw() {
        for (int i = 0; i < 5; i++) {
            graphicsContext.setStroke(Color.BLUE);
            graphicsContext.strokeRect(i * 80,-20,80,80);

            graphicsContext.setStroke(Color.BLUE);
            graphicsContext.strokeRect(i * 80,160,80,80);
        }

        if (chase) {
            counter -= 3;

            if (clyde.getX() + counter < -160) {
                chase = false;
            }
            graphicsContext.setFill(Color.GOLD);
            graphicsContext.fillOval(pacman.getX() + counter, 60, width, height);

            graphicsContext.setFill(Color.RED);
            graphicsContext.fillRect(blinky.getX() + counter, 60, width, height);

            graphicsContext.setFill(Color.TURQUOISE);
            graphicsContext.fillRect(inky.getX() + counter, 60, width, height);

            graphicsContext.setFill(Color.PINK);
            graphicsContext.fillRect(pinky.getX() + counter, 60, width, height);

            graphicsContext.setFill(Color.ORANGE);
            graphicsContext.fillRect(clyde.getX() + counter, 60, width, height);
        }
        else {
            chaseTime++;
            counter += 2;
            if (clyde.getX() + counter > 860) {
                chase = true;
                chaseTime = 0;
            }
            graphicsContext.setFill(Color.GOLD);
            graphicsContext.fillOval(pacman.getX() + counter, 60, width, height);

            if (Math.floor(chaseTime / 30) %2 == 0) {
                graphicsContext.setFill(Color.BLUE);

            }
            else {
                graphicsContext.setFill(Color.WHITE);
            }

            graphicsContext.fillRect(blinky.getX() + counter, 60, width, height);
            graphicsContext.fillRect(inky.getX() + counter, 60, width, height);
            graphicsContext.fillRect(pinky.getX() + counter, 60, width, height);
            graphicsContext.fillRect(clyde.getX() + counter, 60, width, height);
        }



    }

    public void init() {
        pacman = new Point2D((float) (canvas.getWidth() + 110), 60);
        blinky = new Point2D((float) (canvas.getWidth() + 2 * 120), 60);
        inky = new Point2D((float) (canvas.getWidth() + 3 * 120), 60);
        pinky = new Point2D((float) (canvas.getWidth() +  4 * 120), 60);
        clyde = new Point2D((float) (canvas.getWidth() +  5 * 120), 60);
    }
}
