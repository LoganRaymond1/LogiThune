import java.awt.*;
import java.io.*;
import javax.swing.*;


/**
 * The LayoutUI class represents the main user interface for the LogiThune music player.
 * It provides a layout for displaying albums, songs, and controls for playback.
 * It is the central hub/home screen of the music player.
 * It also handles sorting of albums and provides functionality for playing songs.
 * Allows playback of songs, shuffling, looping and pausing.
 */
public class LayoutUI implements DisplayableUI {

    private static JFrame layoutFrame; // The main frame for the layout interface.
    public static boolean songOn; // Track if a song is currently playing.
    public static File audioFile; // Audio file to be played.
    public static JSlider playbackSlider; // Slider for controlling playback position.
    public static JButton pauseButton; // Button to pause playback.
    public static Image scaledPauseImage; // Scaled image for the pause button.
    private static JPanel albumsPanel; // Panel to display albums.
    public static boolean shuffle; // Track if shuffle is enabled.
    public static JPanel rightPanel; // Panel for the right section of the layout.
    public static Album currentAlbum; // Stores the current album being played.
    public static Album currentAlbumFrame; // Stores the current album frame being played
    public static boolean playingPlaylist; // Track if a playlist is being played
    public static Playlist currentPlaylist; // Stores the current playlist being played
    public static boolean paused; // Track if a song is currently playing
    public static JPanel bottomPanel; // Panel for the bottom section of the layout.

    // Tracks state of sorting
    private boolean isSortButton1Active; 
    private boolean isSortButton2Active;
    private boolean isSortButton3Active;
    private boolean isSortButton4Active;


    // Array of songs for $ome $exy $ongs 4 U album
    static Song[] someSexySongs4USongs = {/* 
        new Song("CN TOWER", "4:01", "someSexySongs4U/01-PARTYNEXTDOOR-CN-TOWER-ft-Drake-(JustNaija.wav"),
        new Song("MOTH BALLS", "3:32", "someSexySongs4U/02-PARTYNEXTDOOR-MOTH-BALLS-ft-Drake-(JustNaija.wav"),
        new Song("SOMETHING ABOUT YOU", "3:38", "someSexySongs4U/03-PARTYNEXTDOOR-SOMETHING-ABOUT-YOU-ft-Drake-(JustNaija.wav"),
        new Song("CRYING IN CHANEL", "3:19", "someSexySongs4U/04-Drake-CRYING-IN-CHANEL-(JustNaija.wav"),
        new Song("SPIDER-MAN SUPERMAN", "3:30", "someSexySongs4U/05-PARTYNEXTDOOR-SPIDER-MAN-SUPERMAN-ft-Drake-(JustNaija.wav"),
        new Song("DEEPER", "3:23", "someSexySongs4U/06-PARTYNEXTDOOR-DEEPER-(JustNaija.wav"),
        new Song("SMALL TOWN FAME", "2:28", "someSexySongs4U/07-Drake-SMALL-TOWN-FAME-(JustNaija.wav"),
        new Song("PIMMIE'S DILEMMA", "1:58", "someSexySongs4U/08-Pim-PIMMIE-S-DILEMMA-ft-PARTYNEXTDOOR-Drake-(JustNaija.wav"),
        new Song("BRIAN STEEL", "1:51", "someSexySongs4U/09-Drake-BRIAN-STEEL-(JustNaija.wav"),
        new Song("GIMME A HUG", "3:13", "someSexySongs4U/10-Drake-GIMME-A-HUG-(JustNaija.wav"),
        new Song("RAINING IN HOUSTON", "4:04", "someSexySongs4U/11-Drake-RAINING-IN-HOUSTON-(JustNaija.wav"),
        new Song("LASERS", "3:18", "someSexySongs4U/12-PARTYNEXTDOOR-LASERS-ft-Drake-(JustNaija.wav"),
        new Song("MEET YOUR PADRE", "4:31", "someSexySongs4U/13-PARTYNEXTDOOR-MEET-YOUR-PADRE-ft-Drake-Chino-Pacas-(JustNaija.wav"),
        new Song("NOKIA", "4:01", "someSexySongs4U/14-Drake-NOKIA-(JustNaija.wav"),
        new Song("DIE TRYING", "3:15", "someSexySongs4U/15-PARTYNEXTDOOR-DIE-TRYING-ft-Drake-Yebba-(JustNaija.wav"),
        new Song("SOMEBODY LOVES ME", "3:02", "someSexySongs4U/16-PARTYNEXTDOOR-SOMEBODY-LOVES-ME-ft-Drake-(JustNaija.wav"),
        new Song("CELIBACY", "3:55", "someSexySongs4U/17-PARTYNEXTDOOR-CELIBACY-ft-Drake-(JustNaija.wav"),
        new Song("OMW", "3:53", "someSexySongs4U/18-PARTYNEXTDOOR-OMW-Ft-Drake-(JustNaija.wav"),
        new Song("GLORIOUS", "3:25", "someSexySongs4U/19-PARTYNEXTDOOR-GLORIOUS-ft-Drake-(JustNaija.wav"),
        new Song("WHEN HE'S GONE", "3:29", "someSexySongs4U/20-PARTYNEXTDOOR-WHEN-HE-S-GONE-ft-Drake-(JustNaija.wav"),
        new Song("GREEDY", "6:26", "someSexySongs4U/21-PARTYNEXTDOOR-GREEDY-Ft-Drake-(JustNaija.wav")
    */};

    // Array of songs for Starboy album
    static Song[] starboy = {/* 
        new Song("STARBOY", "3:51", "starboy/01-The-Weeknd-Starboy-ft-Daft-Punk-(JustNaija.wav"),
        new Song("PARTY MONSTER", "4:09", "starboy/02-The-Weeknd-Party-Monster-(JustNaija.wav"),
        new Song("FALSE ALARM", "3:39", "starboy/03-The-Weeknd-False-Alarm-(JustNaija.wav"),
        new Song("REMINDER", "3:40", "starboy/04-The-Weeknd-Reminder-(JustNaija.wav"),
        new Song("ROCKIN", "3:53", "starboy/05-The-Weeknd-Rockin-(JustNaija.wav"),
        new Song("SECRETS", "4:26", "starboy/06-The-Weeknd-Secrets-(JustNaija.wav"),
        new Song("TRUE COLORS", "3:26", "starboy/07-The-Weeknd-True-Colors-(JustNaija.wav"),
        new Song("STARGIRL INTERLUDE", "1:52", "starboy/08-The-Weeknd-Stargirl-Interlude-Ft-Lana-Del-Rey-(JustNaija.wav"),
        new Song("SIDEWALKS", "3:51", "starboy/09-The-Weeknd-Sidewalks-ft-Kendrick-Lamar-(JustNaija.wav"),
        new Song("SIX FEET UNDER", "3:58", "starboy/10-The-Weeknd-Six-Feet-Under-(JustNaija.wav"),
        new Song("LOVE TO LAY", "3:43", "starboy/11-The-Weeknd-Love-To-Lay-(JustNaija.wav"),
        new Song("A LONELY NIGHT", "3:40", "starboy/12-The-Weeknd-A-Lonely-Night-(JustNaija.wav"),
        new Song("ATTENTION", "3:18", "starboy/13-The-Weeknd-Attention-(JustNaija.wav"),
        new Song("ORDINARY LIFE", "3:42", "starboy/14-The-Weeknd-Ordinary-Life-(JustNaija.wav"),
        new Song("NOTHING WITHOUT YOU", "3:19", "starboy/15-The-Weeknd-Nothing-Without-You-(JustNaija.wav"),
        new Song("ALL I KNOW", "5:21", "starboy/16-The-Weeknd-All-I-Know-Ft-Future-(JustNaija.wav"),
        new Song("DIE FOR YOU", "4:20", "starboy/17-The-Weeknd-Die-For-You-(JustNaija.wav"),
        new Song("I FEEL IT COMING", "4:29", "starboy/18-The-Weeknd-I-Feel-It-Coming-ft-Daft-Punk-(JustNaija.wav")
    */};

    // Array of songs for SOS album
    static Song[] sos = {/*
        new Song("SOS", "1:58", "sos/16-SZA-SOS-(JustNaija.wav"),
        new Song("KILL BILL", "2:34", "sos/17-SZA-Kill-Bill-(JustNaija.wav"),
        new Song("SEEK & DESTROY", "3:24", "sos/18-SZA-Seek-Destroy-(JustNaija.wav"),
        new Song("LOW", "3:01", "sos/19-SZA-Low-(JustNaija.wav"),
        new Song("LOVE LANGUAGE", "3:04", "sos/20-SZA-Love-Language-(JustNaija.wav"),
        new Song("BLIND", "2:31", "sos/21-SZA-Blind-(JustNaija.wav"),
        new Song("USED", "2:27", "sos/22-SZA-Used-ft-Don-Toliver-(JustNaija.wav"),
        new Song("SNOOZE", "3:22", "sos/23-SZA-Snooze-(JustNaija.wav"),
        new Song("NOTICE ME", "2:41", "sos/24-SZA-Notice-Me-(JustNaija.wav"),
        new Song("GONE GIRL", "4:04", "sos/25-SZA-Gone-Girl-(JustNaija.wav"),
        new Song("SMOKING ON MY EX PACK", "1:24", "sos/26-SZA-Smoking-on-my-Ex-Pack-(JustNaija.wav"),
        new Song("GHOST IN THE MACHINE", "3:39", "sos/27-SZA-Ghost-in-the-Machine-(JustNaija.wav"),
        new Song("F2F", "3:05", "sos/28-SZA-F2F-(JustNaija.wav"),
        new Song("NOBODY GETS ME", "3:01", "sos/29-SZA-Nobody-Gets-Me-(JustNaija.wav"),
        new Song("CONCEITED", "2:31", "sos/30-SZA-Conceited-(JustNaija.wav"),
        new Song("SPECIAL", "2:39", "sos/31-SZA-Special-(JustNaija.wav"),
        new Song("TOO LATE", "2:45", "sos/32-SZA-Too-Late-(JustNaija.wav"),
        new Song("FAR", "3:01", "sos/33-SZA-Far-(JustNaija.wav"),
        new Song("SHIRT", "4:54", "sos/34-SZA-Shirt-(JustNaija.wav"),
        new Song("OPEN ARMS", "4:00", "sos/35-SZA-Open-Arms-Ft-Travis-Scott-(JustNaija.wav"),
        new Song("I HATE U", "3:19", "sos/36-SZA-I-Hate-U-(JustNaija.wav"),
        new Song("GOOD DAYS", "5:39", "sos/37-SZA-Good-Days-(JustNaija.wav"),
        new Song("FORGIVELESS", "2:22", "sos/38-SZA-Forgiveless-Ft-Ol-Dirty-Bastard-(JustNaija.wav")
    */};

    // Array of songs for Mozart Classical Music album
    static Song[] mozartClassical = {
        new Song("ALLA TURCA", "3:35", "mozart/Alla-Turca(chosic.wav"),
        new Song("SONATA B FLAT ALLEGRETTO GRAZIOSO", "6:16", "mozart/Brendan_Kinsella_-_Mozart_-_Piano_Sonata_in_B-flat_major_III_Allegretto_Grazioso(chosic.wav"),
        new Song("SONATA B FLAT ALLEGRO", "6:38", "mozart/Brendan_Kinsella_-_Mozart_-_Sonata_No_13_In_B_Flat_Major_K333_-_I_Allegro(chosic.wav"),
        new Song("SONATA B FLAT ANDANTE CANTABILE", "6:30", "mozart/Brendan_Kinsella_-_Mozart_-_Sonata_No_13_In_B_Flat_Major_K333_-_II_Andante_Cantabile(chosic.wav"),
        new Song("DON GIOVANNI OVERTURE", "5:33", "mozart/Don-Giovanni-K.wav"),
        new Song("SERENDADE IN G MAJOR", "4:13", "mozart/Mozart-Serenade-in-G-major(chosic.wav"),
        new Song("OVERTURE MARRIAGE", "5:59", "mozart/Overture-to-The-marriage-of-Figaro-K.wav"),
        new Song("PIANO CONCERTO ANDANTE", "4:21", "mozart/Piano-Concerto-no.wav"),
        new Song("SYMPHONY NO 40 ALLEGRO", "4:01", "mozart/Symphony-no.wav")
    };

    // Array of songs for Utopia album
    static Song[] utopia = { /*
        new Song("HYAENA", "3:42", "utopia/01-Travis-Scott-HYAENA-(JustNaija.wav"),
        new Song("THANK GOD", "3:05", "utopia/02-Travis-Scott-THANK-GOD-(JustNaija.wav"),
        new Song("MODERN JAM", "4:15", "utopia/03-Travis-Scott-MODERN-JAM-(JustNaija.wav"),
        new Song("MY EYES", "4:11", "utopia/04-Travis-Scott-MY-EYES-(JustNaija.wav"),
        new Song("GODS COUNTRY", "2:08", "utopia/05-Travis-Scott-GOD-S-COUNTRY-(JustNaija.wav"),
        new Song("SIRENS", "3:25", "utopia/06-Travis-Scott-SIRENS-(JustNaija.wav"),
        new Song("MELTDOWN", "4:06", "utopia/07-Travis-Scott-MELTDOWN-(JustNaija.wav"),
        new Song("FEIN", "3:12", "utopia/08-Travis-Scott-FE-N-(JustNaija.wav"),
        new Song("DELRESTO ECHOES", "4:34", "utopia/09-Travis-Scott-DELRESTO-ECHOES-(JustNaija.wav"),
        new Song("I KNOW", "3:32", "utopia/10-Travis-Scott-I-KNOW-(JustNaija.wav"),
        new Song("TOPIA TWINS", "3:43", "utopia/11-Travis-Scott-TOPIA-TWINS-(JustNaija.wav"),
        new Song("CIRCUS MAXIMUS", "4:19", "utopia/12-Travis-Scott-CIRCUS-MAXIMUS-(JustNaija.wav"),
        new Song("PARASAIL", "2:35", "utopia/13-Travis-Scott-PARASAIL-(JustNaija.wav"),
        new Song("SKITZO", "6:07", "utopia/14-Travis-Scott-SKITZO-(JustNaija.wav"),
        new Song("LOST FOREVER", "2:43", "utopia/15-Travis-Scott-LOST-FOREVER-(JustNaija.wav"),
        new Song("LOOOVE", "3:47", "utopia/16-Travis-Scott-LOOOVE-(JustNaija.wav"),
        new Song("K-POP", "3:05", "utopia/17-Travis-Scott-K-POP-(JustNaija.wav"),
        new Song("TELEKINESIS", "5:54", "utopia/18-Travis-Scott-TELEKINESIS-(JustNaija.wav"),
        new Song("TIL FURTHER NOTICE", "5:15", "utopia/19-Travis-Scott-TIL-FURTHER-NOTICE-(JustNaija.wav")
     */};

    // Array of songs for Chromakopia album
    static Song[] chromakopia = {/*
        new Song("ST CHROMA", "3:42", "chromakopia/01-Tyler-St-Chroma-Ft-The-Creator-Daniel-Caesar-(JustNaija.wav"),
        new Song("RAH TAH TAH", "3:05", "chromakopia/02-Tyler-Rah-Tah-Tah-ft-The-Creator-(JustNaija.wav"),
        new Song("NOID", "4:15", "chromakopia/03-Tyler-Noid-Ft-The-Creator-(JustNaija.wav"),
        new Song("DARLING, I", "4:11", "chromakopia/04-Tyler-The-Creator-Darling-I-ft-Teezo-Touchdown-(JustNaija.wav"),
        new Song("HEY JANE", "2:08", "chromakopia/05-Tyler-The-Creator-Hey-Jane-(JustNaija.wav"),
        new Song("I KILLED YOU", "3:25", "chromakopia/06-Tyler-The-Creator-I-Killed-You-(JustNaija.wav"),
        new Song("JUDGE JUDY", "4:06", "chromakopia/07-Tyler-The-Creator-Judge-Judy-(JustNaija.wav"),
        new Song("STICKY", "3:12", "chromakopia/08-Tyler-The-Creator-Sticky-ft-GloRilla-Sexyy-Red-Lil-Wayne-(JustNaija.wav"),
        new Song("TAKE YOUR MASK OFF", "4:34", "chromakopia/09-Tyler-The-Creator-Take-Your-Mask-Off-ft-Daniel-Caesar-LaToiya-Williams-(JustNaija.wav"),
        new Song("TOMORROW", "3:32", "chromakopia/10-Tyler-The-Creator-Tomorrow-(JustNaija.wav"),
        new Song("THOUGHT I WAS DEAD", "3:43", "chromakopia/11-Tyler-The-Creator-Thought-I-Was-Dead-ft-ScHoolboy-Q-Santigold-(JustNaija.wav"),
        new Song("LIKE HIM", "4:19", "chromakopia/12-Tyler-The-Creator-Like-Him-Ft-Lola-Young-(JustNaija.wav"),
        new Song("BALLOON", "2:35", "chromakopia/13-Tyler-The-Creator-Balloon-ft-Doechii-(JustNaija.wav"),
        new Song("I HOPE YOU FIND YOUR WAY HOME", "6:07", "chromakopia/14-Tyler-The-Creator-I-Hope-You-Find-Your-Way-Home-(JustNaija.wav")
    */};

    // Array of albums with their cover images, titles, artists, and songs
    static Object[][] albums = {
        {new ImageIcon("cnTower.png"), "$ome $exy Songs 4U", "Drake", someSexySongs4USongs},
        {new ImageIcon("starboyAlbum.png"), "Starboy", "The Weeknd", starboy},
        {new ImageIcon("SOSAlbum.png"), "SOS", "SZA", sos},
        {new ImageIcon("mozartMusic.png"), "Mozart Classical Music", "Mozart", mozartClassical},
        {new ImageIcon("utopiaAlbum.png"), "Utopia", "Travis Scott", utopia},
        {new ImageIcon("chromakopiaAlbum.jpg"), "Chromakopia", "Tyler, The Creator", chromakopia}
    };

    /**
     * Constructor for LayoutUI.
     * This constructor creates the main layout for the music player application.
     */
    public LayoutUI() {
        audioFile = new File("someSexySongs4U/01-PARTYNEXTDOOR-CN-TOWER-ft-Drake-(JustNaija.wav"); // Initial audio file
        layoutFrame = new JFrame("LogiThune");
        layoutFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        layoutFrame.setSize(1100, 700);
        layoutFrame.setLocationRelativeTo(null);
        layoutFrame.setLayout(new BorderLayout());
        layoutFrame.setResizable(false);

        JPanel topPanel = homeSearchPanel(layoutFrame); // Creates the top panel

        // Right panel for sorting and scroll bar
        rightPanel = new JPanel();
        rightPanel.setPreferredSize(new Dimension(800, 700)); // Extend up to 700 pixels from the right
        rightPanel.setLayout(new BorderLayout()); // Use BorderLayout for structure
        rightPanel.setBackground(Color.BLACK);

        // Top panel for the sorting
        JPanel topSectionPanel = new JPanel();
        topSectionPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10)); // Align to the left
        topSectionPanel.setBackground(Color.BLACK);

        JLabel sectionLabel = new JLabel("For You");
        sectionLabel.setFont(new Font("Arial", Font.BOLD, 20));
        sectionLabel.setForeground(Color.WHITE);

        JLabel sortLabel = new JLabel("Sort Album Name:");
        sortLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        sortLabel.setPreferredSize(new Dimension(120, 30));
        sortLabel.setHorizontalAlignment(SwingConstants.LEFT);
        sortLabel.setForeground(Color.WHITE);

        JLabel sortLabel2 = new JLabel("Sort Artist Name:");
        sortLabel2.setFont(new Font("Arial", Font.PLAIN, 14));
        sortLabel2.setPreferredSize(new Dimension(120, 30));
        sortLabel2.setHorizontalAlignment(SwingConstants.LEFT);
        sortLabel2.setForeground(Color.WHITE);


        // Create sort buttons with icons
        ImageIcon sortAscendIcon = new ImageIcon("upArrow.png");
        Image scaledSortAscendIcon = sortAscendIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        ImageIcon sortDescendIcon = new ImageIcon("downArrow.png");
        Image scaledSortDescendIcon = sortDescendIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        JButton sortButton1 = new JButton(new ImageIcon(scaledSortAscendIcon));
        JButton sortButton2 = new JButton(new ImageIcon(scaledSortDescendIcon));
        JButton sortButton3 = new JButton(new ImageIcon(scaledSortAscendIcon));
        JButton sortButton4 = new JButton(new ImageIcon(scaledSortDescendIcon));
        sortButton1.setToolTipText("Sort by Album Name Ascending");
        sortButton2.setToolTipText("Sort by Album Name Descending");
        sortButton3.setToolTipText("Sort by Artist Name Ascending");
        sortButton4.setToolTipText("Sort by Artist Name Descending");

        sortButton1.setPreferredSize(new Dimension(30, 30));
        sortButton2.setPreferredSize(new Dimension(30, 30));
        sortButton3.setPreferredSize(new Dimension(30, 30));
        sortButton4.setPreferredSize(new Dimension(30, 30));

        sortButton1.setBackground(Color.WHITE);
        sortButton2.setBackground(Color.WHITE);
        sortButton3.setBackground(Color.WHITE);
        sortButton4.setBackground(Color.WHITE);

        // Add action listeners for sort buttons, sorting based on album name or artist name
        sortButton1.addActionListener(e -> {
            if (!isSortButton1Active) {
                // Sort the albums array by ascending album name
                insertion(albums, 1, true);
                isSortButton1Active = true;
            } else {
                // Reset the albums array to its original order
                resetSort();
                isSortButton1Active = false;
            }

            resetPanelSort(); // Reset the panel to show sorted albums
        });

        sortButton2.addActionListener(e -> {
            if (!isSortButton2Active) {
                // Sort the albums array by descending album name
                insertion(albums, 1, false);
                isSortButton2Active = true;
            } else {
                // Reset the albums array to its original order 
                resetSort();
                isSortButton2Active = false;
            }

            resetPanelSort(); // Reset the panel to show sorted albums
        });

        sortButton3.addActionListener(e -> {
            if (!isSortButton3Active) {
                // Sort the albums array by ascending artist name
                insertion(albums, 2, true);
                isSortButton3Active = true;
            } else {
                // Reset the albums array to its original order 
                resetSort();
                isSortButton3Active = false;
            }

            resetPanelSort(); // Reset the panel to show sorted albums
        });

        sortButton4.addActionListener(e -> {
            if (!isSortButton4Active) {
                // Sort the albums array by descending artist name
                insertion(albums, 2, false);
                isSortButton4Active = true;
            } else {
                // Reset the albums array to its original order 
                resetSort();
                isSortButton4Active = false;
            }

            resetPanelSort(); // Reset the panel to show sorted albums
        });

        // Panel for sorting buttons
        JPanel sortingPanel = new JPanel();
        sortingPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10)); // Horizontal gap: 20px, Vertical gap: 10px
        sortingPanel.setBackground(Color.BLACK);

        sortingPanel.add(sortLabel); 
        sortingPanel.add(sortButton1); 
        sortingPanel.add(sortButton2);

        // Add spacing between the two sorting sections
        sortingPanel.add(Box.createHorizontalStrut(50)); 

        sortingPanel.add(sortLabel2); 
        sortingPanel.add(sortButton3); 
        sortingPanel.add(sortButton4); 

        topSectionPanel.add(sectionLabel);
        topSectionPanel.add(sortingPanel, BorderLayout.CENTER);

        // Center panel for albums in a square layout
        albumsPanel = new JPanel();
        albumsPanel.setLayout(new GridLayout(0, 2, 10, 10)); 
        albumsPanel.setBackground(Color.BLACK);

        // Iterate through the albums array and create buttons for each album.
        for (Object[] albumData : albums) { 
            Album album = new Album((String) albumData[1], (String) albumData[2], (Song[]) albumData[3], (ImageIcon) albumData[0]); // Create an Album object with the data. 

            JButton albumButton = new JButton();
            albumButton.setLayout(new BorderLayout());
            albumButton.setBackground(Color.DARK_GRAY);
            albumButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1)); // Set border for album button
            albumButton.setContentAreaFilled(false); 
            albumButton.setOpaque(true); 
            albumButton.setMaximumSize(new Dimension(200, 200)); 

            ImageIcon albumIcon = (ImageIcon) albumData[0];
            Image scaledAlbumIcon = albumIcon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(scaledAlbumIcon);
            JLabel albumCover = new JLabel((Icon) albumData[0]); 
            albumCover.setHorizontalAlignment(SwingConstants.CENTER);

            JLabel albumName = new JLabel(album.getTitle()); // Creates a JLabel for the album name with get method.
            albumName.setFont(new Font("Arial", Font.BOLD, 14));
            albumName.setHorizontalAlignment(SwingConstants.CENTER);
            albumName.setForeground(Color.WHITE); 

            JLabel albumArtist = new JLabel(album.getArtist()); // Creates a JLabel for the album artist with get method.
            albumArtist.setFont(new Font("Arial", Font.PLAIN, 12));
            albumArtist.setHorizontalAlignment(SwingConstants.CENTER);
            albumArtist.setForeground(Color.LIGHT_GRAY); 

            albumButton.add(albumCover, BorderLayout.NORTH); // Adds the album cover to the top of the button.
            albumButton.add(albumName, BorderLayout.CENTER); // Adds the album name to the center of the button.
            albumButton.add(albumArtist, BorderLayout.SOUTH); // Adds the album artist to the bottom of the button. 

            albumButton.addActionListener(e -> {
                AlbumFrameUI newAlbum = new AlbumFrameUI(album.getTitle(), album.getArtist(), album.getSongs(), (ImageIcon) albumData[0]); // Create a new AlbumFrameUI with the album data.
                newAlbum.setLayoutFrame(layoutFrame); 
                if (currentAlbum == null) { 
                    currentAlbum = album; // If no current album, set the current album to the clicked album.
                } else {
                    currentAlbumFrame = album; // If there is a current album, set the current album frame to the clicked album.
                }

                File currentAudioFile = LayoutUI.audioFile; // Store the current audio file
                int audioFrame = Song.getFrame(); 
                LayoutUI.audioFile = currentAudioFile; 
                newAlbum.display(); // Display the new album frame.

                // If the song is paused, restore the audio frame when the song is played again.
                if (paused) { 
                    if (audioFrame != 0) { // Check if the audio frame is not at zero.
                        ImageIcon playIcon = new ImageIcon("play.png");
                        Image scaledPlayIcon = playIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
                        LayoutUI.pauseButton.setIcon(new ImageIcon(scaledPlayIcon)); 

                        playSong(LayoutUI.audioFile); // Play the current audio file
                        Song.setFrame(audioFrame); // Restore the audio frame
                        Song.stopAudio(); // Stop the current audio if playing
                    }
                } else {
                    if (audioFrame != 0) {
                        playSong(LayoutUI.audioFile); 
                        Song.setFrame(audioFrame); 
                    }
                }
                layoutFrame.dispose(); // Dispose the layout frame after opening the album frame.

            });

            // Hover affects for the album button. 
            albumButton.addMouseListener(new java.awt.event.MouseAdapter() {
                // Change appearance when mouse enters the button.
                @Override
                public void mouseEntered(java.awt.event.MouseEvent e) {
                    albumButton.setBackground(Color.LIGHT_GRAY);
                    albumName.setForeground(Color.BLACK);
                    albumArtist.setForeground(Color.BLACK);
                }

                // Change appearance when mouse exits the button.
                @Override
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    albumButton.setBackground(Color.DARK_GRAY);
                    albumName.setForeground(Color.WHITE);
                    albumArtist.setForeground(Color.LIGHT_GRAY);
                }
            });

            LayoutUI.albumsPanel.add(albumButton);
        }

        
        rightPanel.add(topSectionPanel, BorderLayout.NORTH);

        // Wrap the albums panel in a JScrollPane
        JScrollPane scrollPane = new JScrollPane(albumsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        rightPanel.add(scrollPane, BorderLayout.CENTER);
        layoutFrame.add(rightPanel, BorderLayout.EAST);

        layoutFrame.add(topPanel, BorderLayout.NORTH);

        // Create a bottom panel for playback controls if a song is playing
        if (songOn) { 
            bottomPanel = musicPanel(audioFile);
            layoutFrame.add(bottomPanel, BorderLayout.SOUTH);
        }

        // Create a panel for playlists.
        JPanel playlistsPanel = new JPanel();
        playlistsPanel.setLayout(new BoxLayout(playlistsPanel, BoxLayout.Y_AXIS));
        playlistsPanel.setPreferredSize(new Dimension(300, 600));
        playlistsPanel.setBackground(Color.BLACK);

        // Add a label for the playlists section.
        JLabel playlistsLabel = new JLabel("Playlists");
        playlistsLabel.setFont(new Font("Arial", Font.BOLD, 16));
        playlistsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        playlistsPanel.add(playlistsLabel);
        playlistsLabel.setForeground(Color.WHITE);

        playlistsPanel.setLayout(new BoxLayout(playlistsPanel, BoxLayout.Y_AXIS));
        playlistsPanel.setAlignmentY(Component.BOTTOM_ALIGNMENT); // Aligns buttons lower down.

        // Iterate through the user's playlists and create buttons for each playlist.
        for (Playlist playlist : User.playlists) {
            JButton playlistButton = new JButton(playlist.getName());
            playlistButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            playlistButton.setBackground(Color.WHITE);
            playlistButton.setBorder(new RoundedBorder(10));
            playlistButton.setContentAreaFilled(false);
            playlistButton.setOpaque(true);
            playlistButton.setPreferredSize(new Dimension(250, 50)); 
            playlistButton.setMaximumSize(new Dimension(250, 50)); 
            
            // Playlist buttons to display the songs in the playlist.
            playlistButton.addActionListener(e -> {
                // Create a new frame to display the playlist's songs.
                JFrame playlistSongsFrame = new JFrame("Playlist: " + playlist.getName());
                playlistSongsFrame.setSize(400, 600);
                playlistSongsFrame.setLocationRelativeTo(null);
                playlistSongsFrame.setLayout(new BorderLayout());

                // Create a panel to list songs.
                JPanel songsPanel = new JPanel();
                songsPanel.setBackground(Color.BLACK);
                songsPanel.add(Box.createVerticalStrut(10)); 
                songsPanel.setLayout(new BoxLayout(songsPanel, BoxLayout.Y_AXIS)); // Using a BoxLayout for vertical alignment.

                // Add each song in the playlist to the panel.
                for (Song song : playlist.getSongs()) {
                    JButton songButton = new JButton(song.getTitle() + " (" + song.getDuration() + ")"); // Gets song title and duration with getter methods
                    songButton.setBackground(Color.WHITE); 
                    songButton.setBorder(new RoundedBorder(10)); // Rounded border for the button.
                    songButton.setContentAreaFilled(false);
                    songButton.setOpaque(true);
                    songButton.setPreferredSize(new Dimension(300, 40));
                    songButton.setMaximumSize(new Dimension(300, 40)); 
                    songButton.setAlignmentX(Component.CENTER_ALIGNMENT);
                    
                    // Song button to play the selected song.
                    songButton.addActionListener(songEvent -> {
                        // Check if the song is already playing.
                        if (!songOn) {
                            bottomPanel = musicPanel(audioFile);
                            layoutFrame.add(bottomPanel, BorderLayout.SOUTH);
                            songOn = true;
                        }
                        // Play the selected song.
                        LayoutUI.audioFile = new File(song.getFilePath());
                        LayoutUI.playSong(LayoutUI.audioFile);
                        playingPlaylist = true;
                        LayoutUI.currentPlaylist = playlist;
                    });
                    songsPanel.add(songButton);
                }

                // Add the panel to a scroll pane to scroll through songs.
                JScrollPane playlistScrollPane = new JScrollPane(songsPanel);
                playlistSongsFrame.add(playlistScrollPane, BorderLayout.CENTER);

                playlistSongsFrame.setVisible(true);
            });
            playlistsPanel.add(Box.createVerticalStrut(10)); // Add spacing between buttons
            playlistsPanel.add(playlistButton);
        }

        // Add playlistsPanel to the layout beside the album panel
        layoutFrame.add(playlistsPanel, BorderLayout.WEST);

        display();
    }


    /**
     * Creates a search panel at the top of the home screen.
     * This allows for searching through albums by title or artist.
     * Home button is also included to return to the home screen.
     *    
     * @param currentFrame the current frame that the user sees.
     * @return JPanel the panel containing the search bar and home button.
     */
    public static JPanel homeSearchPanel(JFrame currentFrame) {
        // Create the top panel with a search bar and home button.
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.setBackground(Color.BLACK);
        topPanel.setPreferredSize(new Dimension(1100, 50));

        JPanel searchHomePanel = new JPanel();
        searchHomePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        searchHomePanel.setBackground(Color.BLACK);

        ImageIcon homeIcon = new ImageIcon("homeIcon.png");
        Image scaledImage = homeIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        JButton homeButton = new JButton(new ImageIcon(scaledImage));
        homeButton.setPreferredSize(new Dimension(30, 30));
        homeButton.setBorder(new RoundedBorder(10));
        homeButton.setBackground(Color.WHITE);
        homeButton.setContentAreaFilled(false);
        homeButton.setOpaque(true);
        
        // Home button action listener to return to the home screen with audio still able to play from current position.
        homeButton.addActionListener(e -> {
            File currentAudioFile = LayoutUI.audioFile; 
            int audioFrame = Song.getFrame();
            new LayoutUI();
            LayoutUI.audioFile = currentAudioFile; 
            ImageIcon playIcon;
            if (paused) {
                playIcon = new ImageIcon("play.png");
                playSong(LayoutUI.audioFile); // Play the current audio file
                Song.setFrame(audioFrame); // Restore the audio frame
                Song.stopAudio(); // Stop the current audio if playing
            } else {
                playIcon = new ImageIcon("pause.png");
                playSong(LayoutUI.audioFile); 
                Song.setFrame(audioFrame);

            }
            Image scaledPlayImage = playIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
            if (pauseButton != null) {
                pauseButton.setIcon(new ImageIcon(scaledPlayImage)); // Change icon to play
            }

            currentFrame.dispose(); // Close the current frame.

        });

        homeButton.setToolTipText("Go to Home"); // Set tooltip for the home button.

        // Search bar
        JTextField searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(300, 30));
        searchField.setToolTipText("Search for songs, albums, or artists");

        JButton searchButton = new JButton("Search");
        searchButton.setPreferredSize(new Dimension(80, 30));
        searchButton.setBackground(Color.WHITE);
        searchButton.setBorder(new RoundedBorder(10));
        searchButton.setContentAreaFilled(false);
        searchButton.setOpaque(true);

        // Search field and button which allows users to search for albums by title or artist.
        searchField.addActionListener(e -> {
            handleSearch(albums, searchField.getText().trim());
        });

        searchButton.addActionListener(e -> {
            handleSearch(albums, searchField.getText().trim());
        });

        searchHomePanel.add(homeButton);
        searchHomePanel.add(searchField);
        searchHomePanel.add(searchButton);

        // Account menu panel with support and logout options.
        JPanel accountMenuPanel = new JPanel();
        accountMenuPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        accountMenuPanel.setBackground(Color.BLACK);

        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(Color.LIGHT_GRAY);

        JMenu menu = new JMenu();
        ImageIcon userIcon = new ImageIcon("userIcon.jpg");
        Image scaledImageUser = userIcon.getImage().getScaledInstance(23, 23, Image.SCALE_SMOOTH);
        menu.setIcon(new ImageIcon(scaledImageUser));
        menu.setBorder(BorderFactory.createEmptyBorder());
        menu.setPreferredSize(new Dimension(30, 30));

        // Menu items for the account menu.

        // Support which provides instructions on how to use Logithune.
        JMenuItem supportItem = new JMenuItem("Support");
        menu.add(supportItem);
        supportItem.addActionListener(e -> {
            String supportMessage = "<html><body style='font-family: Arial; font-size: 14px;'>"
                    + "<h2>How to use Logithune</h2>"
                    + "<b>1.</b> Use the search bar at the top to quickly find an album. Just type in the album name (or artist), and matching results will appear.<br><br>"
                    + "<b>2.</b> You can organize the album list by using the sort buttons. These may let you sort alphabetically, by artist, or by album name.<br><br>"
                    + "<b>3.</b> To return to the default album view and clear any search or sorting, just click the Home button. This will reset the page to show all albums in their original order.<br><br>"
                    + "<b>4.</b> To play a song, click on the album cover. This will take you to the album page where you can see all the songs.<br><br>"
                    + "<b>5.</b> To add a song to a playlist, simply hover over the song, and an “Add” button will appear. Click it to add the song to your selected playlist or create a playlist.<br><br>"
                    + "If extra support is needed, please contact us at <b>logithune@gmail.com</b>"
                    + "</body></html>"; // HTML formatted message for support instructions to add headers and bolded words.

            // Create a JEditorPane to display the support message in HTML format.
            JEditorPane editorPane = new JEditorPane("text/html", supportMessage);
            editorPane.setEditable(false); 
            editorPane.setFont(new Font("Arial", Font.PLAIN, 14)); 

            JScrollPane scrollPane = new JScrollPane(editorPane);
            scrollPane.setPreferredSize(new Dimension(400, 300)); 

            JOptionPane.showMessageDialog(layoutFrame, scrollPane, "Support", JOptionPane.INFORMATION_MESSAGE);
        });

        // Log out which allows the user to log out of their account.
        JMenuItem logoutItem = new JMenuItem("Log out");
        menu.add(logoutItem);
        logoutItem.addActionListener(e -> {
            int response = JOptionPane.showConfirmDialog(layoutFrame, "Are you sure you want to log out?", "Log Out", JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.YES_OPTION) {
                Song.stopAudio(); // Stop the audio if playing.
                layoutFrame.dispose(); // Dispose the current frame.
                new LoginUI(); // Open the login UI.
            }
        });

        menuBar.add(menu);
        accountMenuPanel.add(menuBar);

        topPanel.add(searchHomePanel, BorderLayout.CENTER);
        topPanel.add(accountMenuPanel, BorderLayout.EAST);

        return topPanel; // Returns the top panel with the search bar and home button.
    }


    /**
     * Creates a music panel at the bottom of the home screen.
     * This allows for playback controls, including play/pause, next, and previous buttons.
     * It also displays the album cover and song title.
     * 
     * @param audioFile the audio file to be played in the music panel.
     * @return JPanel the panel containing all features.
     */
    public static JPanel musicPanel(File audioFile) {
        // Create the bottom panel for playback controls.
        bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.setPreferredSize(new Dimension(1100, 70));
        bottomPanel.setBackground(Color.BLACK);

        // Left panel for album cover and song title.
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10)); // Align to the left
        leftPanel.setBackground(Color.BLACK);

        JLabel albumCover = new JLabel();
        albumCover.setPreferredSize(new Dimension(40, 40));
        ImageIcon albumImage = currentAlbum.getCover();
        Image scaledAlbumImage = albumImage.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        albumCover.setIcon(new ImageIcon(scaledAlbumImage));

        JLabel songTitle = new JLabel("Song Title");
        songTitle.setFont(new Font("Arial", Font.BOLD, 16));
        songTitle.setForeground(Color.WHITE);

        leftPanel.add(albumCover);
        leftPanel.add(songTitle);

        // Center panel for playback bar and play button.
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10)); // Center-align
        centerPanel.setBackground(Color.BLACK);

        ImageIcon pauseIcon = new ImageIcon("pause.png");
        scaledPauseImage = pauseIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        pauseButton = new JButton(new ImageIcon(scaledPauseImage));
        pauseButton.setBackground(Color.WHITE);
        pauseButton.setFocusPainted(false);
        centerPanel.add(pauseButton);

        // Playback slider to view and move song play time.
        playbackSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 0);
        playbackSlider.setBounds(350, 670, 650, 630);
        playbackSlider.setPreferredSize(new Dimension(400, 30));
        playbackSlider.setForeground(Color.DARK_GRAY);
        playbackSlider.setBackground(Color.BLACK);
        playbackSlider.setOpaque(true);
        centerPanel.add(playbackSlider);

        // Pause button to play or pause the current song.
        pauseButton.addActionListener(e -> {
            // Toggle play/pause functionality.
            if (Song.isPlaying()) {
                Song.stopAudio();
                ImageIcon playIcon = new ImageIcon("play.png");
                Image scaledPlayImage = playIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
                pauseButton.setIcon(new ImageIcon(scaledPlayImage)); 
                paused = true;
            } else {
                Song.startAudio();
                pauseButton.setIcon(new ImageIcon(scaledPauseImage)); 
                paused = false;
            }
        });
        // If the song is not playing, start playing it.
        if (!Song.isPlaying()) {
            Song.playAudio(LayoutUI.audioFile, playbackSlider, pauseButton, scaledPauseImage); 
        }

        // Right panel for playback controls (next, previous, shuffle).
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 10)); 
        rightPanel.setBackground(Color.BLACK);

        ImageIcon prevIcon = new ImageIcon("prevSong.png");
        Image scaledPrevImage = prevIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        JButton prevButton = new JButton(new ImageIcon(scaledPrevImage));
        prevButton.setBackground(Color.WHITE);
        prevButton.setFocusPainted(false);
        rightPanel.add(prevButton);

        prevButton.addActionListener(e -> {
            // Plays previous song in the playlist or album.
            pauseButton.setIcon(new ImageIcon(scaledPauseImage));
            int currentSongIndex = 0;
            if (playingPlaylist) { // Checks if playlist song is playing.
                for (int i = 0; i < LayoutUI.currentPlaylist.getSongs().size(); i++) {
                    if (LayoutUI.currentPlaylist.getSongs().get(i).getFilePath().equals(LayoutUI.audioFile.getPath().replace('\\', '/'))) {
                        currentSongIndex = i;
                        break;
                    }
                }
            } else { // Checks if album song is playing.
                currentSongIndex = LayoutUI.currentAlbum.checkSongIndex(LayoutUI.audioFile.getPath().replace('\\', '/'));
            }

            int randomIndex = currentSongIndex;
            // If shuffle is enabled, select a random song that is not the current song.
            if (shuffle) {
                while (randomIndex == currentSongIndex) {
                    // Select random song from the playlist or album.
                    if (playingPlaylist && currentPlaylist.getSongs().size() > 1) { 
                        randomIndex = (int) (Math.random() * LayoutUI.currentPlaylist.getSongs().size());
                        LayoutUI.audioFile = new File(LayoutUI.currentPlaylist.getSongs().get(randomIndex).getFilePath());
                    } else {
                        randomIndex = (int) (Math.random() * LayoutUI.currentAlbum.songs.length);
                        LayoutUI.audioFile = new File(LayoutUI.currentAlbum.getSong(randomIndex).getFilePath());
                    }
                }
            } else if (playingPlaylist) { // Play the previous song in the playlist.
                if (currentSongIndex > 0) {
                    LayoutUI.audioFile = new File(LayoutUI.currentPlaylist.getSongs().get(currentSongIndex - 1).getFilePath());

                } else { // If at the start of the playlist, play the last song.
                    LayoutUI.audioFile = new File(LayoutUI.currentPlaylist.getSongs().get(LayoutUI.currentPlaylist.getSongs().size() - 1).getFilePath());

                }
            } else if (currentSongIndex > 0) { // Play the previous song in the album.
                LayoutUI.audioFile = new File(LayoutUI.currentAlbum.getSong(currentSongIndex - 1).getFilePath());

            } else { // If at the start of the album, play the last song.
                LayoutUI.audioFile = new File(LayoutUI.currentAlbum.getSong(currentAlbum.songs.length - 1).getFilePath());

            }
            playSong(LayoutUI.audioFile); // Play the selected song.

        });



        ImageIcon nextIcon = new ImageIcon("nextSong.png");
        Image scaledNextImage = nextIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        JButton nextButton = new JButton(new ImageIcon(scaledNextImage));
        nextButton.setBackground(Color.WHITE);
        nextButton.setFocusPainted(false);
        rightPanel.add(nextButton);

        // Next button to play the next song in the playlist or album.
        nextButton.addActionListener(e -> {
            // Plays next song in the playlist or album.
            pauseButton.setIcon(new ImageIcon(scaledPauseImage)); 
            int currentSongIndex = 0;
            if (playingPlaylist) {
                for (int i = 0; i < LayoutUI.currentPlaylist.getSongs().size(); i++) {
                    if (LayoutUI.currentPlaylist.getSongs().get(i).getFilePath().equals(LayoutUI.audioFile.getPath().replace('\\', '/'))) {
                        currentSongIndex = i;
                        break;
                    }
                }
            } else {
                currentSongIndex = LayoutUI.currentAlbum.checkSongIndex(LayoutUI.audioFile.getPath().replace('\\', '/'));
            }

            int randomIndex = currentSongIndex;
            // If shuffle is enabled, select a random song that is not the current song, otherwise select next song in playlist or album.
            if (shuffle) { 
                while (randomIndex == currentSongIndex) {
                    if (playingPlaylist && currentPlaylist.getSongs().size() > 1) {
                        randomIndex = (int) (Math.random() * LayoutUI.currentPlaylist.getSongs().size());
                        LayoutUI.audioFile = new File(LayoutUI.currentPlaylist.getSongs().get(randomIndex).getFilePath());
                    } else {
                        randomIndex = (int) (Math.random() * LayoutUI.currentAlbum.songs.length);
                        LayoutUI.audioFile = new File(LayoutUI.currentAlbum.getSong(randomIndex).getFilePath());
                    }
                }
            } else if (playingPlaylist) {
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
            playSong(LayoutUI.audioFile); // Play the selected song.
        });

        
        ImageIcon shuffleIcon = new ImageIcon("shuffle.png");
        Image scaledShuffleImage = shuffleIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        JButton shuffleButton = new JButton(new ImageIcon(scaledShuffleImage));
        shuffleButton.setBackground(Color.WHITE);
        shuffleButton.setFocusPainted(false);
        rightPanel.add(shuffleButton);

        shuffleButton.addActionListener(e -> {
            // Logic to shuffle the songs
            if (shuffle) {
                shuffleButton.setBackground(Color.WHITE);
                shuffle = false;
            } else {
                shuffleButton.setBackground(Color.GRAY);
                shuffle = true;
            }
        });

        /**
         * Create a loop button to loop the current song
         */
        ImageIcon loopIcon = new ImageIcon("loop.png");
        Image scaledLoopImage = loopIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        JButton loopButton = new JButton(new ImageIcon(scaledLoopImage));
        loopButton.setBackground(Color.WHITE);
        loopButton.setFocusPainted(false);
        rightPanel.add(loopButton);


        // Loop button to toggle looping of the current song.
        loopButton.addActionListener(e -> {
            // If song is already looping, make it stop.
            if (Song.isLooping()) {
                Song.setLooping(false);
                loopButton.setBackground(Color.WHITE);

            } else { // If song is not looping, make it loop.
                Song.setLooping(true);
                loopButton.setBackground(Color.GRAY);

            }
        });

        bottomPanel.add(rightPanel, BorderLayout.EAST);
        bottomPanel.add(leftPanel, BorderLayout.WEST);
        bottomPanel.add(centerPanel, BorderLayout.CENTER);

        return bottomPanel; // Returns the bottom panel with playback controls.
    }

    /**
     * Plays the selected song from the given audio file.
     * Called when a new song is selected or when frames are switched with the home button.
     *
     * @param songFile The audio file of the song to play.
     */
    public static void playSong(File songFile) {
        // If no song has been played and the bottom bar isn't created yet, return
        if(!songOn && bottomPanel == null) {
            return;
             }
        audioFile = songFile; // Update the class level audioFile to the current song
        Song.playAudio(audioFile, playbackSlider, pauseButton, scaledPauseImage);
        // Update the album cover in the bottom panel based on the current song's album
        JLabel albumCover = (JLabel) ((JPanel) LayoutUI.bottomPanel.getComponent(1)).getComponent(0); 
        // Find the album that contains the current song and set it to be the current album
        // This is done to ensure that the album cover is updated correctly when playing through a playlist with many different albums
        if (playingPlaylist) {
            for (Object[] albumData : LayoutUI.albums) {
                Song[] songs = (Song[]) albumData[3];
                for (Song song : songs) {
                    if (song.getFilePath().equals(LayoutUI.audioFile.getPath().replace('\\', '/'))) {
                        LayoutUI.currentAlbum = new Album((String) albumData[1], (String) albumData[2], songs, (ImageIcon) albumData[0]);
                        break;
                    }
                }
            }
        }

        Image scaledAlbumImage = currentAlbum.getCover().getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        albumCover.setIcon(new ImageIcon(scaledAlbumImage));
        albumCover.revalidate();
        albumCover.repaint();

        // Update the song title in the bottom panel
        // Get the text component for the song title
        JPanel songTitle = (JPanel) LayoutUI.bottomPanel.getComponent(1); 
        JLabel songTitleLabel = (JLabel) songTitle.getComponent(1);
        String songTitleText = LayoutUI.currentAlbum.getSong(LayoutUI.currentAlbum.checkSongIndex(LayoutUI.audioFile.getPath().replace('\\', '/'))).getTitle();
        // Truncate the song's title past 12 characters
        if (songTitleText.length() > 15) {
            songTitleText = songTitleText.substring(0, 12) + "..."; // Truncate to 15 characters and add ellipsis
        }
        songTitleLabel.setText(songTitleText);
        songTitleLabel.revalidate();
        songTitleLabel.repaint();
    }

    /**
     * Sorts the albums depending on the sorting button that was pressed
     *
     * @param albums The 2D array of albums to be sorted.
     * @param index  The index of the field to be sorted
     * @param ascending True for ascending order, false for descending order.
     */
    private static void insertion(Object[][] albums, int index, boolean ascending) {
        for (int i = 1; i < albums.length; i++) {
            Object[] key = albums[i];
            String keyField = (String) key[index];
            int j = i - 1;

            // Sort albums based on the specified index and order
            while (j >= 0) {
                String sortItem = (String) albums[j][index];
                if (ascending) {
                    // Ascending order comparison
                    // CompareToIgnoreCase is used to compare alphabetically without case sensitivity
                    if (sortItem.compareToIgnoreCase(keyField) > 0) {
                        albums[j + 1] = albums[j];
                        j--;
                    } else {
                        break;
                    }
                } else {
                    // Descending order comparison
                    if (sortItem.compareToIgnoreCase(keyField) < 0) {
                        albums[j + 1] = albums[j];
                        j--;
                    } else {
                        break;
                    }
                }
            }
            albums[j + 1] = key; // Insert the key element into the correct position
        }
    }

    /**
     * Handles the search functionality for albums, artists, and songs.
     * It sorts the items and then performs a binary search.
     * Checks album, artist, and songs to find a match for the search term.
     *
     * @param albums The 2D array of albums to search through.
     * @param search The searched term entered by the user.
     */
    private static void handleSearch(Object[][] albums, String search) {
        // If nothing has been entered by the user return
        if (search.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter a search term.");
            return;
        }

        insertion(albums, 1, true);

        int left = 0;
        int right = albums.length - 1;

        while (left <= right) {
            int mid = (left + right) / 2;
            String album = (String) albums[mid][1]; // Compare with album name

            if (album.equalsIgnoreCase(search)) {
                resetSort();
                updatePanelSearch(albums[mid]);
                return;
            } else if (album.compareToIgnoreCase(search) < 0) {
                left = mid + 1; // Search in the right half
            } else {
                right = mid - 1; // Search in the left half
            }
        }

        insertion(albums, 2, true);

        left = 0;
        right = albums.length - 1;

        while (left <= right) {
            int mid = (left + right) / 2;
            String artist = (String) albums[mid][2]; // Compare with artist name

            if (artist.equalsIgnoreCase(search)) {
                resetSort();
                updatePanelSearch(albums[mid]);
                return;
            } else if (artist.compareToIgnoreCase(search) < 0) {
                left = mid + 1; // Search in the right half
            } else {
                right = mid - 1; // Search in the left half
            }
        }

        // Linear search for songs in the albums
        for (Object[] albumData : albums) {
            Song[] songs = (Song[]) albumData[3];
            for (Song song : songs) {
                if (song.getTitle().equalsIgnoreCase(search)) {
                    //Create the album and display it through the album frame
                    Album album = new Album((String) albumData[1], (String) albumData[2], songs, (ImageIcon) albumData[0]);
                    AlbumFrameUI newAlbum = new AlbumFrameUI(album.getTitle(), album.getArtist(), album.getSongs(), (ImageIcon) albumData[0]);
                    playSong(LayoutUI.audioFile);
                    newAlbum.display();
                    layoutFrame.dispose();
                    return;
                }
            }
        }

        JOptionPane.showMessageDialog(layoutFrame, "No results found for: " + search, "Search Result", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Resets the albums array to its original state.
     * This is called when the user clicks the sorting button once again to reset results
     * Used for displaying all the albums once again
     */
    private static void resetSort() {
        albums = new Object[][]{
            {new ImageIcon("cnTower.png"), "$ome $exy Songs 4U", "Drake", someSexySongs4USongs},
            {new ImageIcon("starboyAlbum.png"), "Starboy", "The Weeknd", starboy},
            {new ImageIcon("SOSAlbum.png"), "SOS", "SZA", sos},
            {new ImageIcon("mozartMusic.png"), "Mozart Classical Music", "Mozart", mozartClassical},
            {new ImageIcon("utopiaAlbum.png"), "Utopia", "Travis Scott", utopia},
            {new ImageIcon("chromakopiaAlbum.jpg"), "Chromakopia", "Tyler, The Creator", chromakopia}
        };
    }

    /**
     * Updates the albums panel with the search results.
     * This method is called when a search is performed and displays the album that matches the search term.
     *
     * @param albumData The data of the album to be displayed.
     */
    private static void updatePanelSearch(Object[] albumData) {
        albumsPanel.removeAll();

        Album album = new Album((String) albumData[1], (String) albumData[2], (Song[]) albumData[3], (ImageIcon) albumData[0]);

        JButton albumButton = new JButton();
        albumButton.setLayout(new BorderLayout());
        albumButton.setBackground(Color.DARK_GRAY);
        albumButton.setPreferredSize(new Dimension(100, 00));
        albumButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        albumButton.setContentAreaFilled(false);
        albumButton.setOpaque(true);
        albumButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));

        // Get the album cover from the album data
        JLabel albumCover = new JLabel((Icon) albumData[0]);
        albumCover.setHorizontalAlignment(SwingConstants.CENTER);

        // Get the album's title
        JLabel albumName = new JLabel(album.getTitle());
        albumName.setFont(new Font("Arial", Font.BOLD, 14));
        albumName.setHorizontalAlignment(SwingConstants.CENTER);
        albumName.setForeground(Color.WHITE); 

        // Get the artist of the album
        JLabel albumArtist = new JLabel(album.getArtist());
        albumArtist.setFont(new Font("Arial", Font.PLAIN, 12));
        albumArtist.setHorizontalAlignment(SwingConstants.CENTER);
        albumArtist.setForeground(Color.LIGHT_GRAY); 

        // Add the album cover, name, and artist to the album button
        albumButton.add(albumCover, BorderLayout.NORTH);
        albumButton.add(albumName, BorderLayout.CENTER);
        albumButton.add(albumArtist, BorderLayout.SOUTH);

        albumButton.addActionListener(event -> {
            AlbumFrameUI newAlbum = new AlbumFrameUI(album.getTitle(), album.getArtist(), album.getSongs(), (ImageIcon) albumData[0]);
            
            // Update the currentAlbum on the first click of the album button
            if (currentAlbum == null) {
                currentAlbum = album; 
            } else {
                // Otherwise update the current album frame because we only want the current album to be updated
                // when the user clicks on a song in that album. 
                currentAlbumFrame = album;
            }

            int audioFrame = Song.getFrame();

            newAlbum.display();

            // Pause the song if it isnt playing and the audio frame is not 0
            if (!Song.isPlaying()) {
                if (audioFrame != 0) {
                    Song.stopAudio();
                }
            } else {
                // If the song is playing, start it again and restore the current audio frame
                playSong(LayoutUI.audioFile);
                Song.setFrame(audioFrame);
            }

            layoutFrame.dispose();

        });

        albumButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            // Change button colour when hovering over it
            public void mouseEntered(java.awt.event.MouseEvent e) {
                albumButton.setBackground(Color.LIGHT_GRAY);
                albumName.setForeground(Color.BLACK);
                albumArtist.setForeground(Color.BLACK);
            }

            @Override
            // Reset button colour when not hovering over it
            public void mouseExited(java.awt.event.MouseEvent evt) {
                albumButton.setBackground(Color.DARK_GRAY);
                albumName.setForeground(Color.WHITE);
                albumArtist.setForeground(Color.LIGHT_GRAY);
            }
        });

        albumsPanel.add(albumButton);

        // Refresh the albums panel
        albumsPanel.revalidate();
        albumsPanel.repaint();
    }

    /**
     * Resets the albums panel to its original state.
     * This is used when the user clicks the home button after searching or sorting.
     * Re-adds the albums in their original order.
     */
    private void resetPanelSort() {
        albumsPanel.removeAll();

        // Re-add the sorted albums to the panel
        for (Object[] albumData : albums) {
            Album album = new Album((String) albumData[1], (String) albumData[2], (Song[]) albumData[3], (ImageIcon) albumData[0]);

            JButton albumButton = new JButton();
            albumButton.setLayout(new BorderLayout());
            albumButton.setBackground(Color.DARK_GRAY);
            albumButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            albumButton.setContentAreaFilled(false);
            albumButton.setOpaque(true);
            albumButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));

            // Load and scale the album cover image
            JLabel albumCover = new JLabel((Icon) albumData[0]);
            albumCover.setHorizontalAlignment(SwingConstants.CENTER);

            JLabel albumName = new JLabel(album.getTitle());
            albumName.setFont(new Font("Arial", Font.BOLD, 14));
            albumName.setHorizontalAlignment(SwingConstants.CENTER);
            albumName.setForeground(Color.WHITE); // Set text color to white for better visibility

            JLabel albumArtist = new JLabel(album.getArtist());
            albumArtist.setFont(new Font("Arial", Font.PLAIN, 12));
            albumArtist.setHorizontalAlignment(SwingConstants.CENTER);
            albumArtist.setForeground(Color.LIGHT_GRAY); // Set text color to light gray for better visibility

            albumButton.add(albumCover, BorderLayout.NORTH);
            albumButton.add(albumName, BorderLayout.CENTER);
            albumButton.add(albumArtist, BorderLayout.SOUTH);


            //Restore album button functionality
            albumButton.addActionListener(event -> {
                AlbumFrameUI newAlbum = new AlbumFrameUI(album.getTitle(), album.getArtist(), album.getSongs(), (ImageIcon) albumData[0]);

                if (currentAlbum == null) {
                    currentAlbum = album; // Update the current album
                } else {
                    currentAlbumFrame = album; // Update the current album frame
                }

                int audioFrame = Song.getFrame();

                newAlbum.display();

                if (!Song.isPlaying()) {
                    if (audioFrame != 0) {
                        Song.stopAudio();
                    }
                } else {
                    playSong(LayoutUI.audioFile);
                    Song.setFrame(audioFrame);
                }
                layoutFrame.dispose();
            });

            albumButton.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseEntered(java.awt.event.MouseEvent e) {
                    albumButton.setBackground(Color.LIGHT_GRAY);
                    albumName.setForeground(Color.BLACK);
                    albumArtist.setForeground(Color.BLACK);
                }

                @Override
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    albumButton.setBackground(Color.DARK_GRAY);
                    albumName.setForeground(Color.WHITE);
                    albumArtist.setForeground(Color.LIGHT_GRAY);
                }
            });

            albumsPanel.add(albumButton);
        }

        // Refresh the albums panel
        albumsPanel.revalidate();
        albumsPanel.repaint();
    }

    @Override
    public void display() {
        layoutFrame.setVisible(true);
    }
}