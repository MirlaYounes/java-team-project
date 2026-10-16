package rhythmgame.data;

/** A reward the player can unlock, like "100 combo". */
public class Achievement {

    private final String title;
    private final String description;
    private boolean unlocked;

    public Achievement(String title, String description) {
        this.title = title;
        this.description = description;
        this.unlocked = false;          // every achievement starts locked
    }

    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public boolean isUnlocked() { return unlocked; }

    /** Unlocks it and announces it. Does nothing if it is already unlocked. */
    public void unlock() {
        if (!unlocked) {
            unlocked = true;
            System.out.println("*** ACHIEVEMENT UNLOCKED: " + title + " - " + description);
        }
    }

    @Override
    public String toString() {
        return (unlocked ? "[X] " : "[ ] ") + title + " - " + description;
    }
}