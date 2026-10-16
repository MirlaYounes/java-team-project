package rhythmgame.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import rhythmgame.exceptions.InvalidChartException;
import rhythmgame.exceptions.InvalidKeyException;
import rhythmgame.exceptions.InvalidTimingException;
import rhythmgame.exceptions.SaveFileException;
import rhythmgame.exceptions.SongNotFoundException;
import rhythmgame.notes.ChordNote;
import rhythmgame.notes.HoldNote;
import rhythmgame.notes.Note;
import rhythmgame.notes.TapNote;
import rhythmgame.notes.TrapNote;

/** The list of notes of one song. Can load from and save to a text file. */
public class Chart implements Saveable {

    private final ArrayList<Note> notes = new ArrayList<>();

    public void addNote(Note note) { notes.add(note); }
    public void removeNote(int index) { notes.remove(index); }
    public Note getNote(int index) { return notes.get(index); }
    public int size() { return notes.size(); }

    /** Reads a chart file. Each line is one note (formats below). */
    public static Chart load(String filename)
            throws SongNotFoundException, InvalidChartException {
        Chart chart = new Chart();
        int lineNumber = 0;

        try (Scanner reader = new Scanner(new File(filename))) {
            while (reader.hasNextLine()) {
                String line = reader.nextLine().trim();
                lineNumber++;
                if (line.isEmpty()) {
                    continue;                      // skip blank lines
                }
                chart.addNote(parseLine(line, lineNumber));
            }
        } catch (FileNotFoundException e) {
            throw new SongNotFoundException("Chart file not found: " + filename);
        }
        return chart;
    }

    /** Turns one text line into a note, or throws InvalidChartException. */
    private static Note parseLine(String line, int lineNumber) throws InvalidChartException {
        String[] parts = line.split(",");
        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new InvalidChartException("Line " + lineNumber + ": needs a time and a key");
        }

        try {
            double time = Double.parseDouble(parts[0].trim());
            char key = parts[1].trim().charAt(0);
            String type = "";
            if (parts.length > 2) {
                type = parts[2].trim().toUpperCase();
            }

            if (type.equals("")) {
                return new TapNote(time, key);
            } else if (type.equals("H") && parts.length >= 4) {
                return new HoldNote(time, key, Double.parseDouble(parts[3].trim()));
            } else if (type.equals("C") && parts.length >= 4 && !parts[3].trim().isEmpty()) {
                return new ChordNote(time, key, parts[3].trim().charAt(0));
            } else if (type.equals("T")) {
                return new TrapNote(time, key);
            } else {
                throw new InvalidChartException("Line " + lineNumber + ": bad note type or missing value");
            }
        } catch (NumberFormatException e) {
            throw new InvalidChartException("Line " + lineNumber + ": a number is not valid");
        } catch (InvalidTimingException | InvalidKeyException e) {
            throw new InvalidChartException("Line " + lineNumber + ": " + e.getMessage());
        }
    }

    /** Writes the chart to a file, one note per line. */
    @Override
    public void save(String filename) throws SaveFileException {
        try (PrintWriter writer = new PrintWriter(filename)) {
            for (Note n : notes) {
                writer.println(toLine(n));
            }
        } catch (FileNotFoundException e) {
            throw new SaveFileException("Could not save the chart to " + filename);
        }
    }

    private String toLine(Note n) {
        String base = n.getTime() + "," + n.getKey();
        if (n instanceof HoldNote) {
            return base + ",H," + ((HoldNote) n).getDuration();
        } else if (n instanceof ChordNote) {
            return base + ",C," + ((ChordNote) n).getSecondKey();
        } else if (n instanceof TrapNote) {
            return base + ",T";
        }
        return base;                                  // a plain tap note
    }
}