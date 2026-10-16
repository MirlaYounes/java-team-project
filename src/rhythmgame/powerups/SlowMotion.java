package rhythmgame.powerups;

/** Makes the timing windows wider while active (notes feel slower). */
public class SlowMotion extends PowerUp {

    private final double factor = 0.5;   // 0.5 means windows are twice as wide

    public SlowMotion() {
        super("Slow Motion");
    }

    /** Takes a timing window in seconds and returns the widened one. */
    public double widen(double window) {
        if (isActive()) {
            return window / factor;
        }
        return window;
    }

    @Override
    public int modifyScore(int points) {
        return points;              // slow motion doesn't change points
    }
}