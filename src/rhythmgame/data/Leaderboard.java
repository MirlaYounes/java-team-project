package rhythmgame.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import rhythmgame.exceptions.SaveFileException;

/** The top scores, best first. Can be saved to and loaded from a file. */
public class Leaderboard implements Saveable {

    private static final int MAX_ENTRIES = 5;

    // Two lists kept in step: names.get(i) belongs to scores.get(i)
    private final ArrayList<String> names = new ArrayList<>();
    private final ArrayList<Integer> scores = new ArrayList<>();

    /** Puts a score in its right place. Only the top 5 are kept. */
    public void addScore(String name, int score) {
        int position = 0;
        while (position < scores.size() && scores.get(position) >= score) {
            position++;                       // move past every score that is bigger or equal
        }
        names.add(position, name);
        scores.add(position, score);

        if (names.size() > MAX_ENTRIES) {
            names.remove(names.size() - 1);   // drop the lowest one
            scores.remove(scores.size() - 1);
        }
    }

    public int size() {
        return names.size();
    }

    public void show() {
        System.out.println("=== LEADERBOARD ===");
        if (names.isEmpty()) {
            System.out.println("No scores yet.");
        }
        for (int i = 0; i < names.size(); i++) {
            System.out.println((i + 1) + ". " + names.get(i) + " - " + scores.get(i));
        }
    }

    /** Writes one line per score: name,score */
    @Override
    public void save(String filename) throws SaveFileException {
        try (PrintWriter writer = new PrintWriter(filename)) {
            for (int i = 0; i < names.size(); i++) {
                writer.println(names.get(i) + "," + scores.get(i));
            }
        } catch (FileNotFoundException e) {
            throw new SaveFileException("Could not save the leaderboard to " + filename);
        }
    }

    /** Reads a saved leaderboard. If there is no file yet, returns an empty one. */
    public static Leaderboard load(String filename) {
        Leaderboard board = new Leaderboard();
        try (Scanner reader = new Scanner(new File(filename))) {
            while (reader.hasNextLine()) {
                String[] parts = reader.nextLine().split(",");
                if (parts.length == 2) {
                    try {
                        board.addScore(parts[0].trim(), Integer.parseInt(parts[1].trim()));
                    } catch (NumberFormatException e) {
                        // a broken line: skip it
                    }
                }
            }
        } catch (FileNotFoundException e) {
            // first run: no file yet, so start empty
        }
        return board;
    }
}
