package rhythmgame.players;

import java.util.Random;
import rhythmgame.notes.Note;
import rhythmgame.notes.TrapNote;

/** A computer player. A higher skill means fewer mistakes. */
public class BotPlayer extends Player {

    private final double skill;        // 0.0 = hopeless, 1.0 = perfect
    private final Random random = new Random();

    public BotPlayer(String name, double skill) {
        super(name);
        this.skill = Math.max(0.0, Math.min(1.0, skill));   // keep it between 0 and 1
    }

    public double getSkill() {
        return skill;
    }

    @Override
    public char nextInput(Note note) {
        boolean playsWell = random.nextDouble() < skill;    // true skill% of the time

        if (note instanceof TrapNote) {
            // a good bot avoids the bomb, a bad bot falls for it
            return playsWell ? ' ' : note.getKey();
        }

        if (playsWell) {
            return note.getKey();                           // presses the right key
        }
        if (random.nextBoolean()) {
            return ' ';                                     // freezes: no press
        }
        return (char) ('A' + random.nextInt(26));           // presses a random letter
    }
}