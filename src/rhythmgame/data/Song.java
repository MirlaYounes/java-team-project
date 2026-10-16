package rhythmgame.data;

import rhythmgame.exceptions.InvalidTimingException;

/** One playable song: its details and its chart of notes. */
public class Song {

    private final String title;
    private final String artist;
    private final int bpm;          // beats per minute (the song's tempo)
    private final Chart chart;

    public Song(String title, String artist, int bpm, Chart chart) {
        if (bpm <= 0) {
            throw new InvalidTimingException("BPM must be positive: " + bpm);
        }
        this.title = title;
        this.artist = artist;
        this.bpm = bpm;
        this.chart = chart;
    }

    public String getTitle() { return title; }
    public String getArtist() { return artist; }
    public int getBpm() { return bpm; }
    public Chart getChart() { return chart; }

    @Override
    public String toString() {
        return title + " by " + artist + " (" + bpm + " BPM, " + chart.size() + " notes)";
    }
}