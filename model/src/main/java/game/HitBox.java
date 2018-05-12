package game;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * {@code HitBox} class representing an invisible shape for real-time collision detection.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HitBox {
    private float x;
    private float y;
    private float width;
    private float height;

    /**
     * Detect collision between two {@code HitBox}.
     * @param A {@code HitBox}
     * @param B {@code HitBox}
     * @return if the two {@code HitBox}es intersect then {@code true} otherwise {@code false}
     */
    public static boolean intersect(HitBox A, HitBox B) {

        int aX1 = (int) A.getX();
        int aX2 = (int) (A.getX() + A.getWidth());
        int aY1 = (int) A.getY();
        int aY2 = (int) (A.getY() + A.getHeight());
        int bX1 = (int) B.getX();
        int bX2 = (int) (B.getX() + B.getWidth());
        int bY1 = (int) B.getY();
        int bY2 = (int) (B.getY() + B.getHeight());

        return aX1 < bX2 && aX2 > bX1 && aY1 < bY2 && aY2 > bY1;
    }
}
