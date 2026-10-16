package rhythmgame.core;

/** Decides how well a key press matched the time the note was due. */
public class Judge {

    private Difficulty difficulty;   // decides how strict the timing is

    public Judge(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    /**
     * noteTime  = when the note was due (seconds)
     * pressTime = when the player pressed (seconds), or -1 if they never pressed
     */
    public Judgement judge(double noteTime, double pressTime) {
        if (pressTime < 0) {
            return Judgement.MISS;                       // never pressed
        }

        double difference = Math.abs(pressTime - noteTime);   // how far off, early or late

        if (difference <= difficulty.getPerfectWindow()) {
            return Judgement.PERFECT;
        } else if (difference <= difficulty.getGoodWindow()) {
            return Judgement.GOOD;
        } else {
            return Judgement.MISS;
        }
    }
}