import java.awt.*;
import java.io.*;
import javax.swing.*;



/**
 * Represents the UI for displaying an album.
 */
public class AlbumFrameUI extends Album implements DisplayableUI {
    private JFrame album;
    private JPanel topPanel;
    private JPanel albumPanel;
    private JLabel albumCover;
    private JLabel albumTitle;
    private JLabel albumArtist;
    private JPanel songPanel;
    private JPanel songListPanel;
    private ImageIcon albumImage;
    private ImageIcon albumCoverImage;
    private JFrame layoutFrame;


    /**
     * Constructor for the AlbumFrameUI class.
     * Initializes the album frame with the given title, artist, songs, and album cover image.
     *
     * @param title The title of the album.
     * @param artist The artist of the album.
     * @param songs An array of songs in the album.
     * @param albumCoverImage The cover image of the album.
     */
    public AlbumFrameUI(String title, String artist, Song[] songs, ImageIcon albumCoverImage) {
        super(title, artist, songs);
        this.albumCoverImage = albumCoverImage;
    }

    /**
     * Sets the layout frame for the album UI.
     *
     * @param layoutFrame The layout frame to set.
     */
    public void setLayoutFrame(JFrame layoutFrame) {
        this.layoutFrame = layoutFrame;
    }

    /**
     * Displays the album UI.
     */
    public void display() {
        album = new JFrame(title);
        album.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        album.setSize(1100, 700);
        album.setLocationRelativeTo(null);
        album.setLayout(new BorderLayout());
        album.setResizable(false);

        topPanel = LayoutUI.homeSearchPanel(album);
        ((Container) topPanel.getComponent(0)).getComponent(1).setVisible(false);
        ((Container) topPanel.getComponent(0)).getComponent(2).setVisible(false);
        album.add(topPanel, BorderLayout.NORTH);

        // Create a panel for the album cover, title, and artist
        albumPanel = new JPanel();
        albumPanel.setPreferredSize(new Dimension(500, 600));
        albumPanel.setLayout(new BoxLayout(albumPanel, BoxLayout.Y_AXIS));
        albumPanel.setBackground(Color.BLACK); 

        // Album cover
        albumCover = new JLabel();
        albumCover.setHorizontalAlignment(SwingConstants.CENTER);
        albumCover.setPreferredSize(new Dimension(300, 300));
        albumImage = albumCoverImage; 
        albumCover.setIcon(albumImage);
        albumCover.setAlignmentX(Component.CENTER_ALIGNMENT); 
        albumCover.setBorder(BorderFactory.createLineBorder(Color.WHITE, 10)); // Add a border around the album cover

        // Album title
        albumTitle = new JLabel(title, SwingConstants.CENTER);
        albumTitle.setFont(new Font("Arial", Font.BOLD, 30));
        albumTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        albumTitle.setForeground(Color.WHITE);

        // Album artist
        albumArtist = new JLabel(artist, SwingConstants.CENTER);
        albumArtist.setFont(new Font("Arial", Font.ITALIC, 18));
        albumArtist.setAlignmentX(Component.CENTER_ALIGNMENT);
        albumArtist.setForeground(Color.LIGHT_GRAY);

        // Add spacing and components to the album panel
        albumPanel.add(Box.createVerticalGlue()); // Add flexible space at the top
        albumPanel.add(albumCover);
        albumPanel.add(Box.createVerticalStrut(25)); // Add space between the cover and title
        albumPanel.add(albumTitle);
        albumPanel.add(Box.createVerticalStrut(10)); // Add space between the title and artist
        albumPanel.add(albumArtist);
        albumPanel.add(Box.createVerticalGlue()); // Add flexible space at the bottom

        // Add albumPanel to the frame
        album.add(albumPanel, BorderLayout.WEST);

        // Song panel for the list of songs
        songPanel = new JPanel();
        songPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 40));
        songPanel.setPreferredSize(new Dimension(600, 600));
        songPanel.setBackground(Color.LIGHT_GRAY); // Set background color for the song panel

        // Song list panel
        songListPanel = new JPanel();
        songListPanel.setPreferredSize(new Dimension(500, 500));
        songListPanel.setLayout(new GridLayout(0, 2, 5, 5));
        songListPanel.setOpaque(false);


        // Add all the songs within the album frame
        for (int i = 0; i < songs.length; i++) {
            Song song = songs[i];
            String songNumber = (i + 1) + ". ";
            String songTitle = song.getTitle();
            String songDuration = song.getDuration();
            

            // Song details including title, duration, and number in album
            JPanel buttonContent = new JPanel(new BorderLayout());
            buttonContent.setOpaque(false);

            JLabel numberLabel = new JLabel(songNumber);
            numberLabel.setFont(new Font("Arial", Font.BOLD, 16));

            JLabel titleLabel = new JLabel(songTitle);
            titleLabel.setFont(new Font("Arial", Font.PLAIN, 16));

            JLabel durationLabel = new JLabel(songDuration);
            durationLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            durationLabel.setHorizontalAlignment(SwingConstants.RIGHT);

            ImageIcon playIcon = new ImageIcon("play.png");
            Image scaledPlayImage = playIcon.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH);
            JLabel playLabel = new JLabel(new ImageIcon(scaledPlayImage));

            ImageIcon addToPlaylistIcon = new ImageIcon("addToPlaylist.png");
            Image scaledAddToPlaylistImage = addToPlaylistIcon.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH);
            JButton addToPlaylistButton = new JButton(new ImageIcon(scaledAddToPlaylistImage));

            addToPlaylistButton.setFocusPainted(false);
            addToPlaylistButton.setBackground(Color.LIGHT_GRAY);
            addToPlaylistButton.setOpaque(true);
            addToPlaylistButton.setBorder(new RoundedBorder(10));

            // Logic for adding a song to a playlist using the playlist button
            addToPlaylistButton.addActionListener(e -> {
                JFrame playlistFrame = new JFrame("Add to Playlist");
                playlistFrame.setSize(300, 200);
                playlistFrame.setLocationRelativeTo(null);
                playlistFrame.setLayout(new FlowLayout());
                playlistFrame.setResizable(false);

                // Button to display all playlists
                JButton viewPlaylistsButton = new JButton("View Playlists");
                viewPlaylistsButton.setBackground(Color.WHITE);
                viewPlaylistsButton.setBorder(new RoundedBorder(10));
                viewPlaylistsButton.setContentAreaFilled(false);
                viewPlaylistsButton.setOpaque(true);


                viewPlaylistsButton.addActionListener(event -> {
                    // Create a new frame to display playlists
                    JFrame playlistsFrame = new JFrame("User Playlists");
                    playlistsFrame.setSize(300, 400);
                    playlistsFrame.setLocationRelativeTo(null);
                    playlistsFrame.setLayout(new BorderLayout());

                    // Create a panel to list playlists
                    JPanel playlistsPanel = new JPanel();
                    playlistsPanel.setLayout(new BoxLayout(playlistsPanel, BoxLayout.Y_AXIS));

                    // Add each of the users playlist into the frame
                    for (Playlist playlist : User.playlists) {
                        JButton playlistButton = new JButton(playlist.getName());
                        playlistButton.setAlignmentX(Component.CENTER_ALIGNMENT);
                        playlistButton.setBackground(Color.WHITE);
                        playlistButton.setBorder(new RoundedBorder(10));
                        playlistButton.setContentAreaFilled(false);
                        playlistButton.setOpaque(true);
                        playlistButton.setPreferredSize(new Dimension(250, 50));
                        playlistButton.setMaximumSize(new Dimension(250, 50)); 


                        playlistButton.addActionListener(playlistEvent -> {
                            // Logic to handle playlist selection
                            // Once the user selects a playlist, add the song to the playlist and close the playlist frames
                            User.addSong(playlist, song);
                            playlistFrame.dispose();
                            JOptionPane.showMessageDialog(playlistFrame, "Added " + song.getTitle() + " to playlist " + playlist.getName() + ".", "Success", JOptionPane.INFORMATION_MESSAGE);
                            playlistsFrame.dispose();

                        });
                        playlistsPanel.add(playlistButton);
                    }

                    // Add the panel to a scroll pane
                    JScrollPane scrollPane = new JScrollPane(playlistsPanel);
                    playlistsFrame.add(scrollPane, BorderLayout.CENTER);

                    // Show the playlists frame
                    playlistsFrame.setVisible(true);
                });

                // Button to create a new playlist
                JButton createPlaylistButton = new JButton("Create New Playlist");
                createPlaylistButton.setBackground(Color.WHITE);
                createPlaylistButton.setBorder(new RoundedBorder(10));
                createPlaylistButton.setContentAreaFilled(false);
                createPlaylistButton.setOpaque(true);


                createPlaylistButton.addActionListener(event -> {
                    // Get the playlist name from the user
                    String playlistName = JOptionPane.showInputDialog(playlistFrame, "Enter Playlist Name:", "New Playlist", JOptionPane.PLAIN_MESSAGE);

                    // If the user provided a valid name, create a new playlist
                    if (playlistName != null && !playlistName.trim().isEmpty()) {
                        // Logic to create a new playlist
                        User.createPlaylist(playlistName);
                        User.addSong(User.playlists.get(User.playlists.size() - 1), song); // Add the song to the new playlist
                        JOptionPane.showMessageDialog(playlistFrame, "Playlist '" + playlistName + "' created.", "Success", JOptionPane.INFORMATION_MESSAGE);
                        playlistFrame.dispose(); // Close the playlist frame after creation
                    } else {
                        // If the user did not provide a valid name, show an error message
                        JOptionPane.showMessageDialog(playlistFrame, "Playlist name cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                });

                // Add buttons to the frame
                playlistFrame.add(viewPlaylistsButton);
                playlistFrame.add(createPlaylistButton);

                // Make the frame visible
                playlistFrame.setVisible(true);
            });

            buttonContent.add(numberLabel, BorderLayout.WEST);
            buttonContent.add(titleLabel, BorderLayout.CENTER);
            buttonContent.add(durationLabel, BorderLayout.EAST);

            JButton songButton = new JButton();
            songButton.setLayout(new BorderLayout());
            songButton.setBackground(Color.WHITE);
            songButton.setBorder(new RoundedBorder(10));
            songButton.setContentAreaFilled(false);
            songButton.setOpaque(true);
            songButton.add(buttonContent, BorderLayout.CENTER);

            // Play the song when the song button is clicked
            songButton.addActionListener(e -> {
                LayoutUI.playingPlaylist = false;

                // Update the current album once the user clicks on a song in the album
                if (LayoutUI.currentAlbum != LayoutUI.currentAlbumFrame && LayoutUI.currentAlbumFrame != null) {
                    LayoutUI.currentAlbum = LayoutUI.currentAlbumFrame; // Update the current album
                }

                // Revert the button's appearance after a short delay
                Timer timer = new Timer(1000, event -> {
                    songButton.setBackground(Color.WHITE);
                });
                timer.setRepeats(false); // Ensure the timer only runs once
                timer.start();

                // Set the current song to the one that was clicked
                LayoutUI.audioFile = new File(song.getFilePath());

                // If a song has never been played, create the bottom panel and play the song
                if (!LayoutUI.songOn) {
                    LayoutUI.bottomPanel = LayoutUI.musicPanel(LayoutUI.audioFile);
                    album.add(LayoutUI.bottomPanel, BorderLayout.SOUTH);
                    LayoutUI.playSong(LayoutUI.audioFile);
                } else {
                    // Otherwise ensure the pause button shows the play symbol and play the song
                    LayoutUI.pauseButton.setIcon(new ImageIcon(LayoutUI.scaledPauseImage));
                    LayoutUI.playSong(LayoutUI.audioFile);
                }
                // Flag that a song has been played before, meaning the bottom panel is visible
                LayoutUI.songOn = true;

                album.revalidate();
                album.repaint();

                albumCover.revalidate();
                albumCover.repaint();
            });

            songButton.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseEntered(java.awt.event.MouseEvent e) {
                    buttonContent.remove(numberLabel);
                    buttonContent.remove(durationLabel);
                    buttonContent.add(playLabel, BorderLayout.WEST);
                    buttonContent.add(addToPlaylistButton, BorderLayout.EAST);
                    buttonContent.revalidate();
                    buttonContent.repaint();
                    songButton.setBackground(Color.LIGHT_GRAY);
                }

                @Override
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    if (!buttonContent.getBounds().contains(evt.getPoint())) {
                        buttonContent.remove(playLabel);
                        buttonContent.remove(addToPlaylistButton);
                        buttonContent.add(numberLabel, BorderLayout.WEST);
                        buttonContent.add(durationLabel, BorderLayout.EAST);
                        buttonContent.revalidate();
                        buttonContent.repaint();
                        songButton.setBackground(Color.WHITE);
                    }
                }
            });

            songListPanel.add(songButton);
        }

        songPanel.add(songListPanel);

        album.add(songPanel, BorderLayout.EAST);

        if (LayoutUI.songOn) {
            JPanel bottomPanel = LayoutUI.musicPanel(LayoutUI.audioFile);
            album.add(bottomPanel, BorderLayout.SOUTH);
        }

        album.setVisible(true);
    }

}