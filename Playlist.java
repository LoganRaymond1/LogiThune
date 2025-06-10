import java.util.ArrayList;

/**
 * Represents a playlist with a name and a list of songs.
 */
public class Playlist {

    private String name;
    private ArrayList<Song> songs;


    /**
     * Constructor for the Playlist class.
     *
     * @param name The name of the playlist.
     */
    public Playlist(String name) {
        this.name = name;
        this.songs = new ArrayList<>();
    }

    /**
     * Gets the name of the playlist.
     *
     * @return The playlist name.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the songs in the playlist.
     *
     * @return A list of songs in the playlist.
     */
    public ArrayList<Song> getSongs() {
        return songs;
    }

    /**
     * Adds a song to the playlist.
     *
     * @param song The song to add.
     */
    public void addSong(Song song) {
        songs.add(song);
    }

}