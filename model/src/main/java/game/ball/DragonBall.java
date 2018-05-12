package game.ball;


import game.HitBox;
import game.ghost.Ghost;
import game.state.GhostState;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static game.HitBox.intersect;

/**
 * {@code DragonBall} is one of the {@link game.ball.Ball}'s subclass.
 * When {@code Pacman} collide with a {@code NormalBall} its gain a specified amount of point
 * and set the {@code Ghost}s state to {@link game.state.GhostState#VULNERABLE}.
 */
@Getter
@Setter
public class DragonBall extends Ball {
    private static Logger logger = LoggerFactory.getLogger(DragonBall.class);
    private  final int ballSize = 13;


    public DragonBall(int row, int col) {
        super.setRow(row);
        super.setCol(col);
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
                getGame().addScore(50);
                getGame().decreaseBallsCount();
                for (Ghost ghost : getGhosts()) {
                    if (ghost.getGhostState() != GhostState.CAGE && ghost.getGhostState() != GhostState.DEAD) {
                        ghost.initVulnerable();
                    }

                }
                //logger.info("Collosion detected");
                //logger.info("Added +50 to pacman's score");
                //logger.info("Current score={}", getGame().getScore());
            }
        }
    }
}
