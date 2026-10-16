package rhythmgame.exceptions;

/** Thrown when a chart file is corrupted or badly formatted. Checked. */
public class InvalidChartException extends Exception {
    public InvalidChartException(String message) {
        super(message);
    }
}