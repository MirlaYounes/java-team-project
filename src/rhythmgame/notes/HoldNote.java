package rhythmgame.notes;

import rhythmgame.core.Judgement;
import rhythmgame.exceptions.InvalidTimingException;

/** A note you hold down for a number of seconds. Worth more than a tap. */
public class HoldNote extends Note {

    private final double duration;   // how long to hold, in seconds

    public HoldNote(double time, char key, double duration) {
        super(time, key, 200);       // base points are 200
        if (duration <= 0) {
            throw new InvalidTimingException("Hold duration must be positive: " + duration);
        }
        this.duration = duration;
    }

    public double getDuration() {
        return duration;
    }

    @Override
    public int score(Judgement judgement) {
        // 200 base points + 50 extra for every second held
        double total = getBasePoints() + duration * 50;
        return (int) (total * judgement.getMultiplier());
    }

    @Override
    public String toString() {
        return super.toString() + " hold for " + duration + "s";
    }
}