package view;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import point.Point2D;


import java.util.List;

public class PathView {

    public void draw(GraphicsContext graphicsContext, List<Point2D> path) {
        graphicsContext.setStroke(Color.GREEN);
        graphicsContext.setLineWidth(1.5);
        for (int i = 1; i < path.size(); i++) {
            float x = path.get(i-1).getX();
            float y = path.get(i-1).getY();
            float x1 = path.get(i).getX();
            float y1 = path.get(i).getY();

            graphicsContext.strokeLine(x + 10, y + 10, x1 + 10, y1 + 10);
        }

    }
}
