package rhythmgame.powerups;

/** Doubles the points you earn while active. */
public class ScoreBoost extends PowerUp {

    public ScoreBoost() {
        super("Score Boost");
    }

    @Override
    public int modifyScore(int points) {
        if (isActive() && points > 0) {
            return points * 2;      // only gains are doubled, not penalties
        }
        return points;
    }
}