package rhythmgame.data;

import rhythmgame.exceptions.SaveFileException;

/** A promise that an object can write itself to a file. */
public interface Saveable {
    void save(String filename) throws SaveFileException;
}