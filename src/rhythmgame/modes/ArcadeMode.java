package rhythmgame.modes;

import rhythmgame.core.Difficulty;
import rhythmgame.core.GameSession;
import rhythmgame.core.Judge;
import rhythmgame.data.Song;
import rhythmgame.players.Player;

/** The normal game: one player, the chosen difficulty. */
public class ArcadeMode implements GameMode {

    private final Song song;
    private final Player player;
    private final Difficulty difficulty;

    public ArcadeMode(Song song, Player player, Difficulty difficulty) {
        this.song = song;
        this.player = player;
        this.difficulty = difficulty;
    }

    @Override
    public String getName() {
        return "Arcade";
    }

    @Override
    public void start() {
        new GameSession(song, player, new Judge(difficulty)).play();
    }
}