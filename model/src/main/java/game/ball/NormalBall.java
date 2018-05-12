package game.ball;

import game.HitBox;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static game.HitBox.intersect;

/**
 * {@code NormalBall} is one of the {@link game.ball.Ball}'s subclass.
 * When {@code Pacman} collide with a {@code NormalBall} its gain a specified amount of point.
 */
@Getter
@Setter
public class NormalBall extends Ball {
    private static Logger logger = LoggerFactory.getLogger(NormalBall.class);
    private final int ballSize = 6;


    public NormalBall(int row, int col) {
        super(row, col);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void init() {
        super.init();
        int x = (int) this.getPosition().getX();
        int y = (int) this.getPosition().getY();
        this.setHitBox(new HitBox(x, y, 5, 5));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update() {
        if (!isEated()) {
            if (intersect(getPacman().getHitBox(), this.getHitBox())) {
                setEated(true);
                getGame().addScore(10);
                getGame().decreaseBallsCount();
                //logger.info("Collosion detected");
                //logger.info("Added +10 to pacman's score");
                //logger.info("Current score={}", getGame().getScore());
            }
        }
    }
}
