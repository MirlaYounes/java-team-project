package rhythmgame.notes;

import rhythmgame.core.Judgement;

/** A bomb note: the player must NOT press it. Pressing it costs points. */
public class TrapNote extends Note {

    // static = ONE counter shared by every TrapNote (not one per note)
    private static int timesShown = 0;

    public TrapNote(double time, char key) {
        super(time, key, 0);   // base points are 0
    }

    /** Returns a different message each time a trap is shown. */
    public String getWarning() {
        timesShown++;
        if (timesShown == 1) {
            return "BOMB!";
        } else if (timesShown == 2) {
            return "Are you blind??";
        } else if (timesShown == 3) {
            return "Seriously? It's literally a bomb.";
        } else {
            return "Okay, you're doing this on purpose.";
        }
    }

    @Override
    public int score(Judgement judgement) {
        if (judgement == Judgement.MISS) {
            return 0;          // the player avoided the bomb: no penalty
        }
        return -50;            // the player pressed it: lose 50 points
    }

    @Override
    public String toString() {
        return "TRAP " + super.toString();
    }
}