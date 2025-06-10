import java.util.*;

/**
 * Represents a user with playlists.
 */
public class User extends Account {

    static ArrayList<Playlist> playlists = new ArrayList<>();

    /**
     * Constructor for the User class.
     *
     * @param username The username of the user.
     * @param encryptedPassword The encrypted password of the user.
     */
    public User(String username, String encryptedPassword) {
        super(username, encryptedPassword);
    }

    /**
     * Creates a new playlist for the user.
     * Add the new playlist to the user's list of playlists.
     *
     * @param playlistName The name of the new playlist.
     */
    public static void createPlaylist(String playlistName) {
        Playlist newPlaylist = new Playlist(playlistName);
        playlists.add(newPlaylist);

    }

    /**
     * Adds a song to a user's playlist.
     *
     * @param playlist The playlist to add the song to.
     * @param song The song to add.
     */
    public static void addSong(Playlist playlist, Song song) {
        playlist.addSong(song);
    }
}