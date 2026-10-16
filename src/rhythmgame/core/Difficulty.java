package rhythmgame.core;

/** How strict the timing is. Each level stores its two timing windows in seconds. */
public enum Difficulty {
    EASY(0.30, 0.60),     // very forgiving
    NORMAL(0.20, 0.40),
    HARD(0.10, 0.25);     // very strict

    private final double perfectWindow;   // max seconds off for PERFECT
    private final double goodWindow;      // max seconds off for GOOD

    Difficulty(double perfectWindow, double goodWindow) {
        this.perfectWindow = perfectWindow;
        this.goodWindow = goodWindow;
    }

    public double getPerfectWindow() {
        return perfectWindow;
    }

    public double getGoodWindow() {
        return goodWindow;
    }
}