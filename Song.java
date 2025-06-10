import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.Timer;


/**
 * Represents a song with a title, duration, and file path.
 * Provides methods that songs can use to play audio, pause, stop, and loop
 */
public class Song {

    private String title;
    private String duration;
    private String filePath;
    private static Clip clip;
    private static Timer timer;
    private static boolean looping;


    /**
     * Constructor for the Song class.
     * @param title
     * @param duration
     * @param filePath
     */
    public Song(String title, String duration, String filePath) {
        this.title = title;
        this.duration = duration;
        this.filePath = filePath;
    }

    /**
     * Gets the title of the song.
     *
     * @return The title of the song.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gets the duration of the song.
     *
     * @return The duration of the song.
     */
    public String getDuration() {
        return duration;
    }

    /**
     * Gets the file path of the song.
     *
     * @return The file path of the song.
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * Plays the audio file associated with the song.
     * Updates the playback slider and handles playback controls.
     *
     * @param audioFile The audio file to play.
     * @param slider The slider to update during playback.
     * @param pauseButton The button to toggle pause/play.
     * @param scaledPauseImage The image to display for the pause button.
     */
    public static void playAudio(File audioFile, JSlider slider, JButton pauseButton, Image scaledPauseImage) {
        try {
            if (clip != null && clip.isRunning()) {
                clip.stop();
            }
            if (timer != null) {
                timer.stop(); // Stop the previous timer if it exists
            }
            // slider == null means the bottom bar hasn't been created yet so audio cannot be played
            if(slider == null){
                return;
            }

            // Create a new clip and audio input stream for the audio file
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            clip = AudioSystem.getClip();
            clip.open(audioStream);

            // Start playback and update the progress bar
            // Set slider values based on the amount of frames in the audio clip
            slider.setMaximum(clip.getFrameLength());
            slider.setValue(0);
            slider.revalidate();
            slider.repaint();

            slider.addMouseListener(new MouseAdapter() {
                @Override
                // As the mouse is held down and the user is seeking, pause the music
                public void mousePressed(MouseEvent e) {
                    if (clip.isRunning()) {
                        clip.stop();
                    }
                }

                @Override
                // Once the mouse has released, set the audio clip to the position of the slider bar
                public void mouseReleased(MouseEvent e) {
                    pauseButton.setIcon(new ImageIcon(scaledPauseImage)); // Change icon to pause
                    int newPosition = slider.getValue();
                    clip.setFramePosition(newPosition);
                    clip.start();
                    // If looping button has been pressed, set the clip to loop
                    if (looping) {
                        clip.loop(Clip.LOOP_CONTINUOUSLY);
                    }
                }
            });

            clip.start();

            if (looping) {
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }

            // Timer to update the playback bar as the song plays.
            timer = new Timer(0, e -> {
                // Update the slider on each timer tick to the current time of the song.
                if (clip.isOpen() && clip.isRunning()) {
                    slider.setValue(clip.getFramePosition());
                }
                // Once the songs ends set it back to 0 if looping, or update the pause button and play the next song.
                if (clip.getFramePosition() >= clip.getFrameLength()) {
                    if (looping) {
                        clip.setFramePosition(0);
                    } else {
                        pauseButton.setIcon(new ImageIcon(scaledPauseImage));
                        int currentSongIndex = 0;
                        // If a playlist is playing and not an album, get the next song in the playlist
                        if (LayoutUI.playingPlaylist) {
                            for (int i = 0; i < LayoutUI.currentPlaylist.getSongs().size(); i++) {
                                if (LayoutUI.currentPlaylist.getSongs().get(i).getFilePath().equals(LayoutUI.audioFile.getPath().replace('\\', '/'))) {
                                    currentSongIndex = i;
                                    break;
                                }
                            }
                        } else {
                            // If an album is playing, get the next song in the album
                            currentSongIndex = LayoutUI.currentAlbum.checkSongIndex(LayoutUI.audioFile.getPath().replace('\\', '/'));
                        }

                        int randomIndex = currentSongIndex;
                        // If shuffle is enabled, get a random song in the album / playlist that isn't the current song
                        if (LayoutUI.shuffle) {
                            while (randomIndex == currentSongIndex) {

                                if (LayoutUI.playingPlaylist) {
                                    randomIndex = (int) (Math.random() * LayoutUI.currentPlaylist.getSongs().size());
                                    LayoutUI.audioFile = new File(LayoutUI.currentPlaylist.getSongs().get(randomIndex).getFilePath());
                                } else {
                                    randomIndex = (int) (Math.random() * LayoutUI.currentAlbum.songs.length);
                                    LayoutUI.audioFile = new File(LayoutUI.currentAlbum.getSong(randomIndex).getFilePath());
                                }

                            }
                        } else if (LayoutUI.playingPlaylist) {
                            // Handle wrapping back to the first song in the playlist / album once the last song is finished
                            if (currentSongIndex < LayoutUI.currentPlaylist.getSongs().size() - 1) {
                                LayoutUI.audioFile = new File(LayoutUI.currentPlaylist.getSongs().get(currentSongIndex + 1).getFilePath());

                            } else {
                                LayoutUI.audioFile = new File(LayoutUI.currentPlaylist.getSongs().get(0).getFilePath());

                            }
                        } else if (currentSongIndex < LayoutUI.currentAlbum.songs.length - 1) {
                            LayoutUI.audioFile = new File(LayoutUI.currentAlbum.getSong(currentSongIndex + 1).getFilePath());

                        } else {
                            LayoutUI.audioFile = new File(LayoutUI.currentAlbum.getSong(0).getFilePath());

                        }
                        LayoutUI.playSong(LayoutUI.audioFile);
                    }
                }
            });
            timer.start();

        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks if the audio is currently playing.
     *
     * @return True if the audio is playing, false otherwise.
     */
    public static boolean isPlaying() {
        return clip != null && clip.isRunning();
    }

    /**
     * Stops the audio playback.
     */
    public static void stopAudio() {
        if (clip != null) {
            clip.stop();
        }
    }

    /**
     * Starts the audio playback.
     */
    public static void startAudio() {
        clip.start();
    }

    /**
     * Checks if the audio is set to loop.
     *
     * @return True if looping is enabled, false otherwise.
     */
    public static boolean isLooping() {

        return looping;
    }

    /**
     * Sets the looping state of the audio.
     *
     * @param loop True to enable looping, false to disable.
     */
    public static void setLooping(boolean loop) {
        if (clip != null && clip.isRunning()) {
            if (loop) {
                looping = true;
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            } else {
                looping = false;
                clip.loop(0);
            }
        }
    }

    /**
     * Gets the current frame position of the audio.
     *
     * @return The current frame position.
     */
    public static int getFrame() {
        if (clip != null) {
            return clip.getFramePosition();
        }
        return 0;
    }

    /**
     * Sets the frame position of the audio.
     *
     * @param frame The frame position to set.
     */
    public static void setFrame(int frame) {
        if (clip != null) {
            clip.setFramePosition(frame);
        }
    }

}