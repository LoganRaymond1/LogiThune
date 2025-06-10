import javax.swing.*;


/**
 * Represents an album with a title, artist, songs, and album cover.
 */
public class Album {

    protected String title;
    protected String artist;
    protected Song[] songs;
    protected ImageIcon albumCover;


    /**
     * Constructor for the Album class.
     *
     * @param title The title of the album.
     * @param artist The artist of the album.
     * @param songs An array of songs in the album.
     * @param albumCover The cover image of the album.
     */
    public Album(String title, String artist, Song[] songs, ImageIcon albumCover) {
        this.title = title;
        this.artist = artist;
        this.songs = songs;
        this.albumCover = albumCover;
    }

    /**
     * Constructor for the Album class without an album cover.
     *
     * @param title The title of the album.
     * @param artist The artist of the album.
     * @param songs An array of songs in the album.
     */
    public Album(String title, String artist, Song[] songs) {
        this.title = title;
        this.artist = artist;
        this.songs = songs;
    }

    /**
     * Gets the title of the album.
     *
     * @return The album title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gets the artist of the album.
     *
     * @return The album artist.
     */
    public String getArtist() {
        return artist;
    }

    /**
     * Gets the songs in the album.
     *
     * @return An array of songs in the album.
     */
    public Song[] getSongs() {
        return songs;
    }

    /**
     * Gets a specific song in an album by index.
     *
     * @param index The index of the song.
     * @return The song at the specified index.
     */
    public Song getSong(int index) {
        return songs[index];
    }

    /**
     * Gets a specific song by title.
     *
     * @param name The title of the song.
     * @return The song with the specified title, or null if not found.
     */
    public Song getSong(String name) {
        for (Song song : songs) {
            if (song.getTitle().equals(name)) {
                return song;
            }
        }
        return null; 
    }

    /**
     * Gets the album cover image.
     *
     * @return The album cover image.
     */
    public ImageIcon getCover() {
        return albumCover;
    }

    /**
     * Uses the file path to check if a song is within the album
     *
     * @param filePath The file path of the song.
     * @return The index of the song, or 0 if not found.
     */
    public int checkSongIndex(String filePath) {
        for (int i = 0; i < songs.length; i++) {
            if (songs[i].getFilePath().equals(filePath)) {
                return i;
            }
        }
        return 0;
    }
}