package rhythmgame.players;

import rhythmgame.notes.Note;

/** Base class for every player. Keeps the name, score and combo. */
public abstract class Player {

    private final String name;
    private int score;
    private int combo;        // current streak of hits
    private int bestCombo;    // longest streak this game

    public Player(String name) {
        this.name = name;
        this.score = 0;
        this.combo = 0;
        this.bestCombo = 0;
    }

    public String getName() { return name; }
    public int getScore() { return score; }
    public int getCombo() { return combo; }
    public int getBestCombo() { return bestCombo; }

    /** The longer the streak, the bigger the multiplier. */
    public int getMultiplier() {
        if (combo >= 30) {
            return 4;
        } else if (combo >= 20) {
            return 3;
        } else if (combo >= 10) {
            return 2;
        }
        return 1;
    }

    /** Adds points. Gains are multiplied by the combo; penalties are not. */
    public void addPoints(int points) {
        if (points > 0) {
            score = score + points * getMultiplier();
        } else {
            score = score + points;
        }
    }

    public void increaseCombo() {
        combo++;
        if (combo > bestCombo) {
            bestCombo = combo;
        }
    }

    public void resetCombo() {
        combo = 0;
    }

    /** Which key does this player press for this note? Returns ' ' for "no press". */
    public abstract char nextInput(Note note);

    @Override
    public String toString() {
        return name + " | score " + score + " | combo " + combo;
    }
}