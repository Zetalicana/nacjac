package game.state;

/**
 * Represent the different game states.
 */
public enum GameState {
    /**
     * Initialize state.
     * Before the play state we initialize all the game objects.
     */
    INITIALIZE,

    /**
     *  Play state.
     */
    PLAY,

    /**
     * Reset state.
     * If the player collide with the ghosts
     * when they are in the {@link game.state.GhostState#CHASE} state
     * the reset state is triggered.
     * {@code Pacman} and {@code Ghost} position is reset to their starting state.
     */
    RESET,

    /**
     * Level cleared state.
     * After the player obtained all the {@code Ball}
     * this game state is triggered.
     */
    LEVEL_CLEARED,

    /**
     * Game over state.
     * When player life is reduced to zero,
     * this state is triggered.
     */
    GAME_OVER
}
