package game;

import org.junit.Assert;
import org.junit.Test;

public class HitBoxTest {
    HitBox A;
    HitBox B;


    @Test
    public void intersect() {

        A = new HitBox(5, 9, 10, 10);
        B = new HitBox(14, 15, 20, 20);

        Assert.assertEquals(true,HitBox.intersect(A,B));


        A = new HitBox(30, 40, 10, 10);
        B = new HitBox(20, 30, 10, 10);

        Assert.assertEquals(false,HitBox.intersect(A,B));


        A = new HitBox(76, 70, 10, 10);
        B = new HitBox(55, 65, 300, 300);

        Assert.assertEquals(true,HitBox.intersect(A,B));

    }
}