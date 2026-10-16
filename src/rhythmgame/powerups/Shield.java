package rhythmgame.powerups;

/** Forgives one miss, then switches itself off. */
public class Shield extends PowerUp {

    private int missesLeft = 1;     // how many misses it can still forgive

    public Shield() {
        super("Shield");
    }

    @Override
    public void activate() {
        super.activate();           // runs PowerUp's version (including the exception check)
        missesLeft = 1;
    }

    /** Called on a miss. Returns true if the shield saved the player. */
    public boolean absorbMiss() {
        if (isActive() && missesLeft > 0) {
            missesLeft--;
            deactivate();           // the shield is used up
            return true;
        }
        return false;
    }

    @Override
    public int modifyScore(int points) {
        return points;              // a shield doesn't change points
    }
}