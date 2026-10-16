package rhythmgame.modes;

import rhythmgame.core.Difficulty;
import rhythmgame.core.GameSession;
import rhythmgame.core.Judge;
import rhythmgame.data.Song;
import rhythmgame.players.Player;

/** Learn a song: the most forgiving timing and Slow Motion always on. */
public class PracticeMode implements GameMode {

    private final Song song;
    private final Player player;

    public PracticeMode(Song song, Player player) {
        this.song = song;
        this.player = player;
    }

    @Override
    public String getName() {
        return "Practice";
    }

    @Override
    public void start() {
        GameSession session = new GameSession(song, player, new Judge(Difficulty.EASY));
        session.enableSlowMotion();
        session.play();
    }
}