import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import rhythmgame.core.Difficulty;
import rhythmgame.data.Achievement;
import rhythmgame.data.Chart;
import rhythmgame.data.ChartEditor;
import rhythmgame.data.Leaderboard;
import rhythmgame.data.Song;
import rhythmgame.exceptions.InvalidChartException;
import rhythmgame.exceptions.SaveFileException;
import rhythmgame.exceptions.SongNotFoundException;
import rhythmgame.modes.ArcadeMode;
import rhythmgame.modes.GameMode;
import rhythmgame.modes.PracticeMode;
import rhythmgame.modes.VersusMode;
import rhythmgame.players.BotPlayer;
import rhythmgame.players.HumanPlayer;
import rhythmgame.players.Player;

/** Starts the program: shows the main menu and connects all the parts. */
public class Main {

    private static final String SCORES_FILE = "scores.txt";
    private static final String DEFAULT_SONG = "songs/test.txt";

    // One Scanner for the whole program, shared by everyone who reads the keyboard
    private static final Scanner scanner = new Scanner(System.in);

    private static Leaderboard leaderboard;
    private static final Achievement firstRun = new Achievement("First Run", "Finish a song in Arcade");
    private static final Achievement onFire = new Achievement("On Fire", "Reach a 10 combo (unlocks Hard)");

    public static void main(String[] args) {
        leaderboard = Leaderboard.load(SCORES_FILE);

        System.out.println("=== RHYTHM GAME ENGINE ===");
        System.out.print("Your name: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            name = "Player";
        }

        boolean running = true;
        while (running) {
            System.out.println();
            System.out.println("1) Arcade   2) Practice   3) Versus Bot   4) Versus Friend");
            System.out.println("5) Chart Editor   6) Leaderboard   7) Achievements   0) Quit");
            System.out.print("> ");
            String choice = scanner.nextLine().trim();

            if (choice.equals("1")) {
                playArcade(name);
            } else if (choice.equals("2")) {
                playPractice(name);
            } else if (choice.equals("3")) {
                playVersus(name, new BotPlayer("Bot", 0.8));
            } else if (choice.equals("4")) {
                System.out.print("Friend's name: ");
                String friend = scanner.nextLine().trim();
                if (friend.isEmpty()) {
                    friend = "Friend";
                }
                playVersus(name, new HumanPlayer(friend, scanner));
            } else if (choice.equals("5")) {
                new ChartEditor(scanner).run();
            } else if (choice.equals("6")) {
                leaderboard.show();
            } else if (choice.equals("7")) {
                System.out.println(firstRun);
                System.out.println(onFire);
            } else if (choice.equals("0")) {
                running = false;
            } else {
                System.out.println("Please choose a number from the menu.");
            }
        }
        System.out.println("Thanks for playing!");
    }

    private static void playArcade(String name) {
        Song song = askSong();
        if (song == null) {
            return;                                   // loading failed, back to the menu
        }
        Difficulty difficulty = askDifficulty();
        Player me = new HumanPlayer(name, scanner);

        GameMode mode = new ArcadeMode(song, me, difficulty);
        mode.start();                                 // Java runs the Arcade version

        // After the game: achievements and leaderboard
        firstRun.unlock();
        if (me.getBestCombo() >= 10) {
            onFire.unlock();
        }
        leaderboard.addScore(me.getName(), me.getScore());
        try {
            leaderboard.save(SCORES_FILE);
        } catch (SaveFileException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void playPractice(String name) {
        Song song = askSong();
        if (song == null) {
            return;
        }
        GameMode mode = new PracticeMode(song, new HumanPlayer(name, scanner));
        mode.start();                                 // same call, a different mode runs
    }

    private static void playVersus(String name, Player opponent) {
        Song song = askSong();
        if (song == null) {
            return;
        }
        Difficulty difficulty = askDifficulty();
        GameMode mode = new VersusMode(song, new HumanPlayer(name, scanner), opponent, difficulty);
        mode.start();
    }

    /** Lists the songs in the songs folder and loads the one the player picks. */
    private static Song askSong() {
        File[] allFiles = new File("songs").listFiles();
        ArrayList<File> songFiles = new ArrayList<>();

        if (allFiles != null) {
            for (File f : allFiles) {
                if (f.getName().endsWith(".txt")) {
                    songFiles.add(f);                 // keep only the .txt files
                }
            }
        }

        if (songFiles.isEmpty()) {
            System.out.println("No songs found in the songs folder.");
            return null;
        }

        System.out.println("Choose a song:");
        for (int i = 0; i < songFiles.size(); i++) {
            System.out.println((i + 1) + ") " + songFiles.get(i).getName().replace(".txt", ""));
        }
        System.out.print("> ");

        int index;
        try {
            index = Integer.parseInt(scanner.nextLine().trim()) - 1;
        } catch (NumberFormatException e) {
            index = -1;                               // not a number
        }
        if (index < 0 || index >= songFiles.size()) {
            System.out.println("That is not one of the songs.");
            return null;
        }

        File chosen = songFiles.get(index);
        try {
            Chart chart = Chart.load(chosen.getPath());
            String title = chosen.getName().replace(".txt", "");
            return new Song(title, "Unknown", 120, chart);
        } catch (SongNotFoundException | InvalidChartException e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    private static Difficulty askDifficulty() {
        System.out.println("Difficulty: 1) Easy   2) Normal   3) Hard");
        System.out.print("> ");
        String choice = scanner.nextLine().trim();

        if (choice.equals("1")) {
            return Difficulty.EASY;
        } else if (choice.equals("3")) {
            if (onFire.isUnlocked()) {
                return Difficulty.HARD;
            }
            System.out.println("Hard is locked! Unlock the 'On Fire' achievement first. Using Normal.");
        }
        return Difficulty.NORMAL;
    }
}