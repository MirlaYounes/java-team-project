package rhythmgame.players;

import java.util.Scanner;
import rhythmgame.notes.Note;

/** A real person typing keys in the console. */
public class HumanPlayer extends Player {

    private final Scanner scanner;   // reads what the person types

    public HumanPlayer(String name, Scanner scanner) {
        super(name);
        this.scanner = scanner;
    }

    @Override
    public char nextInput(Note note) {
        System.out.print("> ");
        String line = scanner.nextLine().trim();
        if (line.isEmpty()) {
            return ' ';                          // pressed Enter only: no key
        }
        return Character.toUpperCase(line.charAt(0));   // first letter typed
    }
}