package rhythmgame.powerups;

import rhythmgame.exceptions.PowerUpAlreadyActiveException;

/** Base class for all power-ups. Tracks the name and whether it is switched on. */
public abstract class PowerUp implements Activatable {

    private final String name;
    private boolean active;

    public PowerUp(String name) {
        this.name = name;
        this.active = false;      // starts switched off
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public void activate() {
        if (active) {
            throw new PowerUpAlreadyActiveException(name + " is already active!");
        }
        active = true;
    }

    @Override
    public void deactivate() {
        active = false;
    }

    /** Each power-up changes the points in its own way. */
    public abstract int modifyScore(int points);

    @Override
    public String toString() {
        return name + (active ? " (ON)" : " (off)");
    }
}