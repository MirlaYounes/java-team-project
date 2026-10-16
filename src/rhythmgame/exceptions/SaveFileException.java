package rhythmgame.exceptions;

/** Thrown when saving something to a file fails. Checked. */
public class SaveFileException extends Exception {
    public SaveFileException(String message) {
        super(message);
    }
}