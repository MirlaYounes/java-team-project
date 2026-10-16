package rhythmgame.notes;
import rhythmgame.core.Judgement;
import rhythmgame.core.Scorable;
import rhythmgame.exceptions.InvalidKeyException;
import rhythmgame.exceptions.InvalidTimingException;

public abstract class Note implements Scorable {

    private final double time;      
    private final char key;         
    private final int basePoints;   

    public Note(double time, char key, int basePoints) {
        if (time < 0) {
            throw new InvalidTimingException("Note time cannot be negative: " + time);
        }
        if (!Character.isLetter(key)) {
            throw new InvalidKeyException("Note key must be a letter: " + key);
        }
        this.time = time;
        this.key = Character.toUpperCase(key);
        this.basePoints = basePoints;
    }

    public double getTime() { return time; }
    public char getKey() { return key; }
    public int getBasePoints() { return basePoints; }



    @Override
    public String toString() {
        return "[" + key + "] at " + time + "s";
    }}