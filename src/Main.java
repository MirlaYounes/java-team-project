

import rhythmgame.core.Judgement;
import rhythmgame.notes.*;

public class Main {
    public static void main(String[] args) {
        Note[] notes = {
            new TapNote(1.0, 'A'),
            new HoldNote(2.0, 'S', 2.0),
            new ChordNote(3.0, 'D', 'F'),
            new TrapNote(4.0, 'J')
        };
        for (Note n : notes) {
            System.out.println(n + " -> " + n.score(Judgement.PERFECT));
            TrapNote t = new TrapNote(5.0, 'K');
            System.out.println(t.getWarning());   // BOMB!
            System.out.println(t.getWarning());   // Are you blind??
            System.out.println(t.getWarning());   // Seriously? It's literally a bomb.
        }
    }
}