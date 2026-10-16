package rhythmgame.core;

import java.util.Random;

import rhythmgame.data.Song;
import rhythmgame.notes.ChordNote;
import rhythmgame.notes.HoldNote;
import rhythmgame.notes.Note;
import rhythmgame.notes.TrapNote;
import rhythmgame.players.BotPlayer;
import rhythmgame.players.HumanPlayer;
import rhythmgame.players.Player;
import rhythmgame.powerups.ScoreBoost;
import rhythmgame.powerups.Shield;
import rhythmgame.powerups.SlowMotion;

/** Runs one round of the game: one song, one player. */
public class GameSession {

    // In the console we measure reaction time. A press this many seconds
    // after the note appears counts as perfectly on time.
    private static final double TARGET_REACTION = 0.8;

    private final Song song;
    private final Player player;
    private final Judge judge;
    private final Random random = new Random();

    private final ScoreBoost scoreBoost = new ScoreBoost();
    private final Shield shield = new Shield();
    private final SlowMotion slowMotion = new SlowMotion();

    private int hits = 0;         // notes the player hit
    private int totalNotes = 0;   // notes that had to be hit (traps not counted)

    public GameSession(Song song, Player player, Judge judge) {
        this.song = song;
        this.player = player;
        this.judge = judge;
    }

    public Player getPlayer() { return player; }

    /** Used by Practice mode: makes the timing easier. */
    public void enableSlowMotion() {
        if (!slowMotion.isActive()) {
            slowMotion.activate();
        }
    }

    /** Plays the whole song from the first note to the last. */
    public void play() {
        System.out.println("=== " + song + " ===");
        System.out.println("Player: " + player.getName());
        System.out.println("Type the key and press Enter. Traps: press Enter only.");
        shield.activate();                       // everyone starts with one free miss

        for (int i = 0; i < song.getChart().size(); i++) {
            playNote(song.getChart().getNote(i));
        }

        System.out.println("=== FINISHED ===");
        System.out.println(player);
        System.out.println("Best combo: " + player.getBestCombo() + " | Grade: " + getGrade());
    }

    private void playNote(Note note) {
        if (note instanceof TrapNote) {
            playTrap((TrapNote) note);
            return;
        }

        totalNotes++;
        System.out.println(describe(note) + " now!");

        double start = System.nanoTime();
        char pressed = player.nextInput(note);
        double elapsed = (System.nanoTime() - start) / 1_000_000_000.0;   // seconds
        double pressTime = reactionTime(elapsed);

        boolean rightKey = (pressed == note.getKey());
        if (note instanceof ChordNote && rightKey && player instanceof HumanPlayer) {
            System.out.println("   second key:");
            char second = player.nextInput(note);
            rightKey = (second == ((ChordNote) note).getSecondKey());
        }

        Judgement result;
        if (!rightKey) {
            result = Judgement.MISS;                         // wrong key or no press
        } else {
            // Slow motion halves the timing error (widen gives 2.0 when it is on)
            double scale = slowMotion.widen(1.0);
            double adjusted = TARGET_REACTION + (pressTime - TARGET_REACTION) / scale;
            result = judge.judge(TARGET_REACTION, adjusted);
        }
        applyResult(note, result);
    }

    /** A bomb: pressing it costs points and breaks the combo. */
    private void playTrap(TrapNote trap) {
        System.out.println(trap.getWarning() + " [" + trap.getKey() + "] - do NOT press it!");
        char pressed = player.nextInput(trap);
        if (pressed == trap.getKey()) {
            player.addPoints(trap.score(Judgement.PERFECT));   // -50
            player.resetCombo();
            System.out.println("   You hit the bomb! -50");
        } else {
            System.out.println("   Dodged it.");
        }
    }

    private void applyResult(Note note, Judgement result) {
        if (result == Judgement.MISS) {
            if (shield.absorbMiss()) {
                System.out.println("   MISS - but the Shield saved your combo!");
            } else {
                System.out.println("   MISS");
                player.resetCombo();
                if (scoreBoost.isActive()) {
                    scoreBoost.deactivate();
                }
            }
            return;
        }

        int points = scoreBoost.modifyScore(note.score(result));
        player.addPoints(points);
        player.increaseCombo();
        hits++;
        System.out.println("   " + result + " +" + points + " (combo " + player.getCombo() + ")");

        if (player.getCombo() == 10 && !scoreBoost.isActive()) {
            scoreBoost.activate();
            System.out.println("   Score Boost is ON!");
        }
    }

    /** A human's time is measured. A bot answers instantly, so its time is faked from its skill. */
    private double reactionTime(double elapsed) {
        if (player instanceof BotPlayer) {
            double skill = ((BotPlayer) player).getSkill();
            double wobble = (random.nextDouble() - 0.5) * (1.0 - skill) * 1.6;
            return TARGET_REACTION + wobble;
        }
        return elapsed;
    }

    private String describe(Note note) {
        if (note instanceof ChordNote) {
            return "[" + note.getKey() + " + " + ((ChordNote) note).getSecondKey() + "]";
        }
        if (note instanceof HoldNote) {
            return "[" + note.getKey() + "] (hold " + ((HoldNote) note).getDuration() + "s)";
        }
        return "[" + note.getKey() + "]";
    }

    public String getGrade() {
        if (totalNotes == 0) {
            return "-";
        }
        double accuracy = (double) hits / totalNotes;
        if (accuracy >= 0.9) return "A";
        if (accuracy >= 0.75) return "B";
        if (accuracy >= 0.5) return "C";
        return "D";
    }
}