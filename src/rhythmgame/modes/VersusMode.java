package rhythmgame.modes;

import rhythmgame.core.Difficulty;
import rhythmgame.core.GameSession;
import rhythmgame.core.Judge;
import rhythmgame.data.Song;
import rhythmgame.players.Player;

/** Two players play the same song one after the other. The higher score wins. */
public class VersusMode implements GameMode {

    private final Song song;
    private final Player first;
    private final Player second;
    private final Difficulty difficulty;

    public VersusMode(Song song, Player first, Player second, Difficulty difficulty) {
        this.song = song;
        this.first = first;
        this.second = second;
        this.difficulty = difficulty;
    }

    @Override
    public String getName() {
        return "Versus";
    }

    @Override
    public void start() {
        System.out.println(">>> " + first.getName() + " plays first <<<");
        new GameSession(song, first, new Judge(difficulty)).play();

        System.out.println(">>> " + second.getName() + " plays next <<<");
        new GameSession(song, second, new Judge(difficulty)).play();

        System.out.println("=== RESULT ===");
        System.out.println(first.getName() + ": " + first.getScore());
        System.out.println(second.getName() + ": " + second.getScore());

        if (first.getScore() > second.getScore()) {
            System.out.println(first.getName() + " wins!");
        } else if (second.getScore() > first.getScore()) {
            System.out.println(second.getName() + " wins!");
        } else {
            System.out.println("It's a tie!");
        }
    }
}