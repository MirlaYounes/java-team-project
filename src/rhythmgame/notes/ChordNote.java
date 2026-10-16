package rhythmgame.notes;

import rhythmgame.core.Judgement;
import rhythmgame.exceptions.InvalidKeyException;

/** A note where two keys must be pressed together. */
public class ChordNote extends Note {

    private final char secondKey;   // the extra key to press

    public ChordNote(double time, char firstKey, char secondKey) {
        super(time, firstKey, 150);   // base points are 150
        if (!Character.isLetter(secondKey)) {
            throw new InvalidKeyException("Second key must be a letter: " + secondKey);
        }
        this.secondKey = Character.toUpperCase(secondKey);
    }

    public char getSecondKey() {
        return secondKey;
    }

    @Override
    public int score(Judgement judgement) {
        int points = (int) (getBasePoints() * judgement.getMultiplier());
        if (judgement == Judgement.PERFECT) {
            points = points + 100;    // bonus for a perfect chord
        }
        return points;
    }

    @Override
    public String toString() {
        return super.toString() + " + [" + secondKey + "]";
    }
}