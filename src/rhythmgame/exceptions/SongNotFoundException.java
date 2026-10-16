package rhythmgame.exceptions;

/** Thrown when the chosen song or its file does not exist. Checked. */
public class SongNotFoundException extends Exception {
    public SongNotFoundException(String message) {
        super(message);
    }
}