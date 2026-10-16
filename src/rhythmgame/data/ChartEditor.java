package rhythmgame.data;

import java.util.Scanner;
import rhythmgame.exceptions.InvalidKeyException;
import rhythmgame.exceptions.InvalidTimingException;
import rhythmgame.exceptions.SaveFileException;
import rhythmgame.notes.ChordNote;
import rhythmgame.notes.HoldNote;
import rhythmgame.notes.Note;
import rhythmgame.notes.TapNote;
import rhythmgame.notes.TrapNote;

/** A console menu where the player builds their own chart and saves it. */
public class ChartEditor {

    private final Chart chart = new Chart();
    private final Scanner scanner;

    public ChartEditor(Scanner scanner) {
        this.scanner = scanner;
    }

    public Chart getChart() {
        return chart;
    }

    /** Shows the menu until the player chooses 0. */
    public void run() {
        System.out.println("=== CHART EDITOR === (add notes in time order)");
        boolean running = true;

        while (running) {
            System.out.println("1) Add tap   2) Add hold   3) Add chord   4) Add trap");
            System.out.println("5) Remove last   6) Preview   7) Save   0) Back");
            System.out.print("> ");
            String choice = scanner.nextLine().trim();

            try {
                if (choice.equals("1")) {
                    double time = askNumber("Time (seconds): ");
                    char key = askKey("Key: ");
                    chart.addNote(new TapNote(time, key));
                } else if (choice.equals("2")) {
                    double time = askNumber("Time (seconds): ");
                    char key = askKey("Key: ");
                    double length = askNumber("Hold for (seconds): ");
                    chart.addNote(new HoldNote(time, key, length));
                } else if (choice.equals("3")) {
                    double time = askNumber("Time (seconds): ");
                    char first = askKey("First key: ");
                    char second = askKey("Second key: ");
                    chart.addNote(new ChordNote(time, first, second));
                } else if (choice.equals("4")) {
                    double time = askNumber("Time (seconds): ");
                    char key = askKey("Key: ");
                    chart.addNote(new TrapNote(time, key));
                } else if (choice.equals("5")) {
                    removeLast();
                } else if (choice.equals("6")) {
                    preview();
                } else if (choice.equals("7")) {
                    saveChart();
                } else if (choice.equals("0")) {
                    running = false;
                } else {
                    System.out.println("Please choose a number from the menu.");
                }
            } catch (NumberFormatException e) {
                System.out.println("That is not a valid number. Try again.");
            } catch (InvalidTimingException | InvalidKeyException e) {
                System.out.println("Could not add the note: " + e.getMessage());
            }
        }
    }

    private double askNumber(String prompt) {
        System.out.print(prompt);
        return Double.parseDouble(scanner.nextLine().trim());   // may throw NumberFormatException
    }

    private char askKey(String prompt) {
        System.out.print(prompt);
        String line = scanner.nextLine().trim();
        if (line.isEmpty()) {
            throw new InvalidKeyException("You did not type a key");
        }
        return line.charAt(0);
    }

    private void removeLast() {
        if (chart.size() == 0) {
            System.out.println("The chart is empty.");
        } else {
            chart.removeNote(chart.size() - 1);
            System.out.println("Last note removed.");
        }
    }

    private void preview() {
        if (chart.size() == 0) {
            System.out.println("The chart is empty.");
        }
        for (int i = 0; i < chart.size(); i++) {
            Note n = chart.getNote(i);
            System.out.println((i + 1) + ". " + n);
        }
    }

    private void saveChart() {
        System.out.print("File name (e.g. songs/mysong.txt): ");
        String filename = scanner.nextLine().trim();
        try {
            chart.save(filename);
            System.out.println("Saved to " + filename);
        } catch (SaveFileException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}