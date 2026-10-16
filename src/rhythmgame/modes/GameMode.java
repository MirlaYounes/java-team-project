package rhythmgame.modes;

/** A promise that every game mode can be started and has a name. */
public interface GameMode {
    void start();
    String getName();
}