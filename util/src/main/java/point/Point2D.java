package point;

import lombok.*;

/**
 * The {@code Point2D} class defines a point representing a location in (x,y) coordinate space.
 * It can also represent a 2D vector.
 */
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Point2D {
    private float x;
    private float y;

    /**
     * Returns a point with the specified coordinates added to the coordinates
     * of this point.
     * @param x the x coordinate of other points
     * @param y the y coordinate of other point
     * @return the point with added coordinates
     */
    public Point2D add(float x, float y) {
        return new Point2D(getX() + x, getY() + y);
    }

    /**
     * Returns a point with the coordinates of the specified point added to the
     * coordinates of this point.
     * @param point2D the point whose coordinates added to this point
     * @return the point with added coordinates
     */
    public Point2D add(Point2D point2D) {
        return add(point2D.getX(), point2D.getY());
    }

    /**
     * Add a point coordinates to this point.
     * @param point2D the point whose coordinates added to this point
     */
    public void addTogether(Point2D point2D) {
        this.x = this.getX() + point2D.getX();
        this.y = this.getY() + point2D.getY();
    }

    /**
     * Compute the distance between two points.
     * @param x the x coordinate of the other point
     * @param y the y coordinate of the other point
     * @return the distance between this point and {@code (x, y)}
     */
    public float distance(float x, float y) {
        float a = x - getX();
        float b = y - getY();
        return (float) Math.sqrt(a*a + b*b);
    }

    /**
     * Compute the distance between two points.
     * @param point2D the other point
     * @return the distance between this point and {@code point2D}
     */
    public float distance(Point2D point2D) {
        return distance(point2D.getX(), point2D.getY());
    }


    /**
     * Return a point what multiplied with the multiplier.
     * @param multiplier the factor multiplying the coordinates
     * @return the point with multiplied coordinates
     */
    public Point2D multiply(float multiplier) {
        return new Point2D(this.getX() * multiplier, this.getY() * multiplier);
    }

    /**
     * Change the sign of the point.
     */
    public void negate() {
        Point2D point2D = multiply(-1);
        this.setX(point2D.getX());
        this.setY(point2D.getY());
    }

    /**
     * Set this point x and y coordinates.
     * @param x the new desired x coordinate
     * @param y the new desired y coordinate
     */
    public void setXY(float x, float y) {
        this.x = x;
        this.y = y;
    }
}
