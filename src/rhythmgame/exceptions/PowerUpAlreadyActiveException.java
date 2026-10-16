package rhythmgame.exceptions;

/** Thrown when a power-up is activated while it is already active. Unchecked. */
public class PowerUpAlreadyActiveException extends RuntimeException {
    public PowerUpAlreadyActiveException(String message) {
        super(message);
    }
}