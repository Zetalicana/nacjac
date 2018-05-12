package player;


import lombok.Data;

/**
 * Class representing a player.
 */
@Data
public class Player {
    private String name;
    private int highScore;
    private int totalScore;
    private int totalDeaths;
    private int totalBallEated;
    private int totalGhostEated;
    private int totalLevelCleared;

}
