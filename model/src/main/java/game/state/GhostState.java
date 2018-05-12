package game.state;

/**
 * Represent the different states of the ghosts.
 */
public enum GhostState {
    /**
     * Chase state.
     * The ghost are chasing pacman.
     */
    CHASE,

    /**
     * Cage state.
     * The ghosts waiting for their state change in their cage.
     */
    CAGE,

    /**
     * Scattered state.
     * Sometimes they give a little break to the player,
     * so instead of chase they move to their own corners.
     */
    SCATTERED,

    /**
     * Dead state.
     * If they collide with {@code Pacman} while they are in the
     * {@link game.state.GhostState#VULNERABLE} state, then
     * this state is triggered.
     * In this state they are moving back to their cage.
     */
    DEAD,

    /**
     * Vulnerable state.
     * When {@code Pacman} ate {@code DragonBall},
     * this state is triggered.
     */
    VULNERABLE,
}
