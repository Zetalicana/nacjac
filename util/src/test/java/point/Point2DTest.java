package point;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class Point2DTest {
    Point2D point2D;

    @Before
    public void setUp() {
        point2D = new Point2D();
    }

    @Test
    public void add() {
        point2D.setXY(5, 5);
        Point2D temp = point2D.add(10, 5);
        Assert.assertEquals(new Point2D(15, 10), temp);
    }

    @Test
    public void add1() {
        point2D.setXY(5, 5);
        Point2D temp = point2D.add(new Point2D(13, 20));
        Assert.assertEquals(new Point2D(18, 25), temp);
        temp = point2D.add(new Point2D(30, 30));
        Assert.assertEquals(new Point2D(35, 35), temp);
    }

    @Test
    public void addTogether() {
        point2D.setXY(5, 5);
        point2D.addTogether(new Point2D(13, 20));
        Assert.assertEquals(new Point2D(18, 25), point2D);
        point2D.addTogether(new Point2D(-3, 100));
        Assert.assertEquals(new Point2D(15, 125), point2D);
    }

    @Test
    public void distance() {
        point2D.setXY(130, 150);
        float distance = point2D.distance(30, 100);
        Assert.assertEquals(111.83, distance, 0.1);

        point2D.setXY(4500, 3213);
        distance = point2D.distance(100, 210);
        Assert.assertEquals(5327.101, distance, 0.1);
    }

    @Test
    public void distance1() {
    }

    @Test
    public void multiply() {
    }

    @Test
    public void negate() {
    }
}