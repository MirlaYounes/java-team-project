package rhythmgame.powerups;

/** A promise that something can be switched on and off. */
public interface Activatable {
    void activate();
    void deactivate();
    boolean isActive();
}