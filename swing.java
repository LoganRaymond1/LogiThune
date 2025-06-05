import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.Timer;
import javax.swing.border.Border;

public class swing {
    public static void main(String[] args) {
                //new Login(); 
                new Layout();
                
                /*Album tower = new Album("cn", "drake", new Song[]{
                    new Song("Song A", "3:45", "songA.wav"),
                    new Song("Song B", "4:20", "songB.wav"),
                    new Song("Song C", "2:50", "songC.wav"),
                    new Song("Song D", "5:10", "songD.wav"),
                    new Song("Song E", "3:30", "songE.wav"),
                    new Song("Song F", "4:00", "songF.wav"),
                    new Song("Song G", "3:15", "songG.wav"),
                    new Song("Song H", "4:05", "songH.wav"),
                    new Song("Song I", "3:50", "songI.wav"),
                    new Song("Song J", "4:25", "songJ.wav"),
                    new Song("Song K", "2:55", "songK.wav"),
                    new Song("Song L", "5:15", "songL.wav"),
                    new Song("Song M", "3:40", "songM.wav"),
                    new Song("Song N", "4:30", "songN.wav"),
                    new Song("Song O", "3:20", "songO.wav"),
                    new Song("Song P", "4:10", "songP.wav"),
                    new Song("Song Q", "3:55", "songQ.wav"),
                });
                tower.albumFrame();*/
                
                
                
    }
}

class Login {
    protected JFrame loginFrame;

    private JTextField username;
    private JPasswordField password;
    private String usernames[] = {"user1", "user2", "admin"};
    private String passwords[] = {"pass1", "pass2", "admin123"};

    public Login() {
        loginFrame = new JFrame("Login"); 

        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setSize(400, 250);
        loginFrame.setLocationRelativeTo(null);
        loginFrame.setLayout(null);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(50, 50, 100, 30);
        loginFrame.add(usernameLabel);

        username = new JTextField();
        username.setBounds(150, 50, 200, 30);
        loginFrame.add(username);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(50, 100, 100, 30);
        loginFrame.add(passwordLabel);

        password = new JPasswordField();
        password.setBounds(150, 100, 200, 30);
        loginFrame.add(password);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(150, 150, 100, 30);
        loginFrame.add(loginButton);

        loginButton.addActionListener(e -> checkLogin());
        loginFrame.getRootPane().setDefaultButton(loginButton); // Set the default button for Enter key

        loginFrame.setVisible(true);
    }

    public void checkLogin() {
        String username = this.username.getText();
        String password = new String(this.password.getPassword());

        for (int i = 0; i < usernames.length; i++) {
            if (usernames[i].equals(username) && passwords[i].equals(password)) {
                JOptionPane.showMessageDialog(loginFrame, "Login successful!");
                loginFrame.dispose(); 
                new Decryption();
                return;
            }
        }
        JOptionPane.showMessageDialog(loginFrame, "Invalid username or password.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}

class Decryption{
    private JFrame decryptionFrame;
    private String encryptedWords[] = {"fdw"};
    private String randomEncryptWord;
    private int random = (int) (Math.random() * encryptedWords.length);
    private int key = (int) (Math.random() * 4 + 1); 
    private String decryptHelp = "To decrypt the word:\n" +
                "1. Use the key to shift each letter of the encrypted word backward in the alphabet.\n" +
                "2. For example, if the key is 3 and the encrypted word is 'fdw', shift each letter back by 3.\n" +
                "3. 'f' becomes 'c', 'd' becomes 'a', and 'w' becomes 't'.\n" +
                "4. Enter the decrypted word in the text field and click Submit.";

    public Decryption() {
        randomEncryptWord = encryptedWords[random];

        decryptionFrame = new JFrame("Are you a Robot?");
        decryptionFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        decryptionFrame.setSize(400, 300); 
        decryptionFrame.setLocationRelativeTo(null);
        decryptionFrame.setLayout(null);

        JLabel encryptedWordLabel = new JLabel(randomEncryptWord, SwingConstants.CENTER);
        encryptedWordLabel.setBounds(50, 20, 300, 50);
        encryptedWordLabel.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 24)); // Large, bold, italic font
        encryptedWordLabel.setBorder(BorderFactory.createEmptyBorder()); 
        decryptionFrame.add(encryptedWordLabel);

        JLabel keyLabel = new JLabel("Key: " + key);
        keyLabel.setBounds(50, 70, 300, 30); 
        decryptionFrame.add(keyLabel);

        JTextField textField = new JTextField();
        textField.setBounds(50, 100, 300, 30);
        decryptionFrame.add(textField);

        JButton decryptButton = new JButton("Submit");
        decryptButton.setBounds(150, 150, 100, 30);
        decryptionFrame.add(decryptButton);

        decryptButton.addActionListener(e -> {
            String userInput = textField.getText();
            String decryptedWord = decrypt(randomEncryptWord, key); 
            if (userInput.equalsIgnoreCase(decryptedWord)) {
                JOptionPane.showMessageDialog(decryptionFrame, "Correct! The decrypted word was: " + decryptedWord);
                decryptionFrame.dispose();
                new Layout();

            } else {
                JOptionPane.showMessageDialog(decryptionFrame, "Incorrect! Try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton helpButton = new JButton("Help");
        helpButton.setBounds(150, 200, 100, 30); 
        decryptionFrame.add(helpButton);

        helpButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(decryptionFrame, decryptHelp,"How to Decrypt", JOptionPane.INFORMATION_MESSAGE);
        });

        decryptionFrame.getRootPane().setDefaultButton(decryptButton); // Set the default button for Enter key
        decryptionFrame.setVisible(true);
    }
        
        private String decrypt(String text, int key) {
            StringBuilder decrypted = new StringBuilder();
            for (char c : text.toCharArray()) {
                if (Character.isLetter(c)) {
                    char base = Character.isLowerCase(c) ? 'a' : 'A';
                    decrypted.append((char) ((c - base - key + 26) % 26 + base)); 
                } else {
                    decrypted.append(c); 
                }
            }
            return decrypted.toString();
        }
}

class Layout{
    private static JFrame layoutFrame;
    public static boolean songOn; 
    public static File audioFile; // Declare audioFile as a static variable
    public static JSlider playbackSlider; // Declare playbackSlider as a static variable
    public static JButton pauseButton; // Declare pauseButton as a static variable
    public static Image scaledPauseImage; // Declare scaledPauseImage as a static variable

    
    static Song[] cnTowerSongs = {
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
    };

    static Album cnTower = new Album("$ome $exy $ongs 4 U", "Drake", cnTowerSongs);

    Object[][] albums = {
        {new ImageIcon("cnTower.png"), "$ome $exy Songs 4U", "Drake", cnTowerSongs},
        {new ImageIcon("albumCover2.png"), "Album Name 2", "Artist 2", new Song[]{
            new Song("Song A2", "3:50", "songA2.wav"),
            new Song("Song B2", "4:10", "songB2.wav")
        }},
        {new ImageIcon("albumCover3.png"), "Album Name 3", "Artist 3", new Song[]{
            new Song("Song A3", "3:30", "songA3.wav"),
            new Song("Song B3", "4:00", "songB3.wav")
        }},
        {new ImageIcon("albumCover4.png"), "Album Name 4", "Artist 4", new Song[]{
            new Song("Song A4", "3:40", "songA4.wav"),
            new Song("Song B4", "4:15", "songB4.wav")
        }},
        {new ImageIcon("albumCover5.png"), "Album Name 5", "Artist 5", new Song[]{
            new Song("Song A4", "3:40", "songA4.wav"),
            new Song("Song B4", "4:15", "songB4.wav")
        }}
    };

    public Layout() {
        audioFile = new File("someSexySongs4U/01-PARTYNEXTDOOR-CN-TOWER-ft-Drake-(JustNaija.wav"); // Declare audioFile as a static variable
        layoutFrame = new JFrame("LogiThune");
        layoutFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        layoutFrame.setSize(1000, 700);
        layoutFrame.setLocationRelativeTo(null);
        layoutFrame.setLayout(new BorderLayout());


        JPanel topPanel = homeSearchPanel(layoutFrame);        
        

        JPanel rightPanel = new JPanel();
        rightPanel.setPreferredSize(new Dimension(700, 700)); // Extend up to 700 pixels from the right
        rightPanel.setLayout(new BorderLayout()); // Use BorderLayout for structure
        rightPanel.setBackground(Color.LIGHT_GRAY);
    
        // Top panel for the "Your Section" label and sort buttons
        JPanel topSectionPanel = new JPanel();
        topSectionPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10)); // Align to the left
        topSectionPanel.setBackground(Color.LIGHT_GRAY);
    
        JLabel sectionLabel = new JLabel("For You");
        sectionLabel.setFont(new Font("Arial", Font.BOLD, 20));
    
        JButton sortButton1 = new JButton("Sort 1");
        JButton sortButton2 = new JButton("Sort 2");
        JButton sortButton3 = new JButton("Sort 3");
    
        sortButton1.setPreferredSize(new Dimension(80, 30));
        sortButton2.setPreferredSize(new Dimension(80, 30));
        sortButton3.setPreferredSize(new Dimension(80, 30));
    
        topSectionPanel.add(sectionLabel);
        topSectionPanel.add(sortButton1);
        topSectionPanel.add(sortButton2);
        topSectionPanel.add(sortButton3);
    
        // Center panel for albums in a square layout
        JPanel albumsPanel = new JPanel();
        albumsPanel.setLayout(new GridLayout(0, 2, 10, 10)); // 10 rows, 2 columns, with gaps
        albumsPanel.setBackground(Color.LIGHT_GRAY);
        
    
        for (Object[] albumData : albums) {
            Album album = new Album((String) albumData[1], (String) albumData[2], (Song[]) albumData[3]);

            JButton albumButton = new JButton();
            albumButton.setLayout(new BorderLayout());
            albumButton.setBackground(Color.WHITE);
            albumButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            albumButton.setContentAreaFilled(false);
            albumButton.setOpaque(true);

            ImageIcon albumIcon = (ImageIcon) albumData[0];
            Image scaledAlbumIcon = albumIcon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(scaledAlbumIcon);
            JLabel albumCover = new JLabel((Icon) albumData[0]);
            albumCover.setHorizontalAlignment(SwingConstants.CENTER);

            JLabel albumName = new JLabel(album.getTitle());
            albumName.setFont(new Font("Arial", Font.BOLD, 14));
            albumName.setHorizontalAlignment(SwingConstants.CENTER);

            JLabel albumArtist = new JLabel(album.getArtist());
            albumArtist.setFont(new Font("Arial", Font.PLAIN, 12));
            albumArtist.setHorizontalAlignment(SwingConstants.CENTER);

            albumButton.add(albumCover, BorderLayout.NORTH);
            albumButton.add(albumName, BorderLayout.CENTER);
            albumButton.add(albumArtist, BorderLayout.SOUTH);

            albumButton.addActionListener(e -> {
                AlbumFrame newAlbum = new AlbumFrame(album.getTitle(), album.getArtist(), album.getSongs());
                newAlbum.displayFrame();
                layoutFrame.dispose();
            });

            albumButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                albumButton.setBackground(Color.LIGHT_GRAY);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                albumButton.setBackground(Color.WHITE);
            }
            });

            albumsPanel.add(albumButton);
        }
    
        // Add the top section and albums panel to the right panel
        rightPanel.add(topSectionPanel, BorderLayout.NORTH);
    
        // Wrap the albums panel in a JScrollPane
        JScrollPane scrollPane = new JScrollPane(albumsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    
        rightPanel.add(scrollPane, BorderLayout.CENTER);
        layoutFrame.add(rightPanel, BorderLayout.EAST);

        layoutFrame.add(topPanel, BorderLayout.NORTH);

        if(songOn) {
            System.out.println("SONGON" + audioFile);
            JPanel bottomPanel = musicPanel(audioFile);
            layoutFrame.add(bottomPanel, BorderLayout.SOUTH);
        }

        layoutFrame.setVisible(true);
    }

    public static JPanel homeSearchPanel(JFrame currentFrame) {
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.setBackground(Color.LIGHT_GRAY);
        topPanel.setPreferredSize(new Dimension(1000, 50));

        JPanel searchHomePanel = new JPanel();
        searchHomePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        searchHomePanel.setBackground(Color.LIGHT_GRAY);

        ImageIcon homeIcon = new ImageIcon("homeIcon.png");
        Image scaledImage = homeIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        JButton homeButton = new JButton(new ImageIcon(scaledImage));
        homeButton.setPreferredSize(new Dimension(30, 30));
        homeButton.setBorder(new RoundedBorder(10)); 
        homeButton.setBackground(Color.WHITE);
        homeButton.setContentAreaFilled(false);
        homeButton.setOpaque(true);
        homeButton.addActionListener(e -> {
            currentFrame.dispose(); 
            new Layout(); 
        });
        homeButton.setToolTipText("Go to Home");

        JTextField searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(300, 30));
        searchField.setToolTipText("Search for songs, albums, or artists");

        searchHomePanel.add(homeButton);
        searchHomePanel.add(searchField);
        
        JPanel accountMenuPanel = new JPanel();
        accountMenuPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        accountMenuPanel.setBackground(Color.LIGHT_GRAY);
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(Color.LIGHT_GRAY);
        JMenu menu = new JMenu();
        ImageIcon userIcon = new ImageIcon("userIcon.jpg");
        Image scaledImageUser = userIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        menu.setIcon(new ImageIcon(scaledImageUser)); 
        menu.setBorder(BorderFactory.createEmptyBorder());
        menu.setPreferredSize(new Dimension(30, 30));

        JMenuItem loginItem = new JMenuItem("Account");
        menu.add(loginItem);
        loginItem.addActionListener(e -> {
            JOptionPane.showMessageDialog(layoutFrame, "Account settings will be implemented soon.");
        });

        JMenuItem supportItem = new JMenuItem("Support");
        menu.add(supportItem);
        supportItem.addActionListener(e -> {
            JOptionPane.showMessageDialog(layoutFrame, "Support will be available soon.");
        });

        JMenuItem settingsItem = new JMenuItem("Settings");
        menu.add(settingsItem);
        settingsItem.addActionListener(e -> {
            JOptionPane.showMessageDialog(layoutFrame, "Settings will be available soon.");
        });

        JMenuItem logoutItem = new JMenuItem("Log out");
        menu.add(logoutItem);
        logoutItem.addActionListener(e -> {
            int response = JOptionPane.showConfirmDialog(layoutFrame, "Are you sure you want to log out?", "Log Out", JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.YES_OPTION) {
                layoutFrame.dispose(); 
                new Login(); 
            }
        });
        
        menuBar.add(menu);
        accountMenuPanel.add(menuBar);

        topPanel.add(searchHomePanel, BorderLayout.CENTER);
        topPanel.add(accountMenuPanel, BorderLayout.EAST);

        //layoutFrame.setVisible(true);

        return topPanel;
    }

    public static JPanel musicPanel(File audioFile) {
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.setPreferredSize(new Dimension(1000, 70));
        bottomPanel.setBackground(Color.LIGHT_GRAY);

        // Left panel for album cover and song title
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10)); // Align to the left
        leftPanel.setBackground(Color.LIGHT_GRAY);

        JLabel albumCover = new JLabel();
        albumCover.setPreferredSize(new Dimension(40, 40)); 
        ImageIcon albumImage = new ImageIcon("cnTower.png"); 
        Image scaledAlbumImage = albumImage.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        albumCover.setIcon(new ImageIcon(scaledAlbumImage));

        JLabel songTitle = new JLabel("Song Title");
        songTitle.setFont(new Font("Arial", Font.BOLD, 16));

        leftPanel.add(albumCover);
        leftPanel.add(songTitle);

        // Center panel for playback bar and play button
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10)); // Center-align
        centerPanel.setBackground(Color.LIGHT_GRAY);

        ImageIcon pauseIcon = new ImageIcon("pause.png");
        scaledPauseImage = pauseIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        pauseButton = new JButton(new ImageIcon(scaledPauseImage)); 
        pauseButton.setFocusPainted(false);
        centerPanel.add(pauseButton);
        
        
        playbackSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 0);
        playbackSlider.setBounds(350, 670, 650, 630);
        playbackSlider.setPreferredSize(new Dimension(400, 30));
        playbackSlider.setBackground(null);
        centerPanel.add(playbackSlider);


        pauseButton.addActionListener(e -> {
            if (AudioManager.isPlaying()) {
                AudioManager.stopAudio();
                ImageIcon playIcon = new ImageIcon("play.png");
                Image scaledPlayImage = playIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
                pauseButton.setIcon(new ImageIcon(scaledPlayImage)); // Change icon to play
            } else {
                AudioManager.startAudio();
                pauseButton.setIcon(new ImageIcon(scaledPauseImage)); // Change icon to pause
            }
        });
        System.out.println(Layout.audioFile);
        AudioManager.playAudio(Layout.audioFile, playbackSlider, pauseButton, scaledPauseImage); // Start playing the audio

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 10)); // Align to the right
        rightPanel.setBackground(Color.LIGHT_GRAY);

        ImageIcon prevIcon = new ImageIcon("prevSong.png");
        Image scaledPrevImage = prevIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        JButton prevButton = new JButton(new ImageIcon(scaledPrevImage));
        prevButton.setFocusPainted(false);
        rightPanel.add(prevButton);

        prevButton.addActionListener(e -> {
            // Logic to play the previous song
            int currentSongIndex = Layout.cnTower.checkSongIndex(Layout.audioFile.getPath().replace('\\', '/'));
            if(currentSongIndex > 0){
            Layout.audioFile = new File(Layout.cnTower.getSong(currentSongIndex - 1).getFilePath());
            }
            else{
                Layout.audioFile = new File(Layout.cnTower.getSong(Layout.cnTower.songs.length - 1).getFilePath());
            }
            System.out.println(Layout.audioFile);
            playSong(Layout.audioFile);

            
        });

        ImageIcon nextIcon = new ImageIcon("nextSong.png");
        Image scaledNextImage = nextIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        JButton nextButton = new JButton(new ImageIcon(scaledNextImage));
        nextButton.setFocusPainted(false);
        rightPanel.add(nextButton);

        nextButton.addActionListener(e -> {
            // Logic to play the previous song
            int currentSongIndex = Layout.cnTower.checkSongIndex(Layout.audioFile.getPath().replace('\\', '/'));
            if(currentSongIndex < Layout.cnTower.songs.length - 1){
            Layout.audioFile = new File(Layout.cnTower.getSong(currentSongIndex + 1).getFilePath());
            }
            else{
                Layout.audioFile = new File(Layout.cnTower.getSong(0).getFilePath());
            }
            playSong(Layout.audioFile);

            
        });

        ImageIcon shuffleIcon = new ImageIcon("shuffle.png");
        Image scaledShuffleImage = shuffleIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        JButton shuffleButton = new JButton(new ImageIcon(scaledShuffleImage));
        shuffleButton.setFocusPainted(false);
        rightPanel.add(shuffleButton);

        shuffleButton.addActionListener(e -> {
            // Logic to shuffle the songs
            Random random = new Random();
            int randomIndex = random.nextInt(Layout.cnTower.songs.length);
            Layout.audioFile = new File(Layout.cnTower.getSong(randomIndex).getFilePath());
            playSong(Layout.audioFile);
        });
        
        ImageIcon loopIcon = new ImageIcon("loop.png");
        Image scaledLoopImage = loopIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        JButton loopButton = new JButton(new ImageIcon(scaledLoopImage));
        loopButton.setFocusPainted(false);
        rightPanel.add(loopButton);

        bottomPanel.add(rightPanel, BorderLayout.EAST);
        bottomPanel.add(leftPanel, BorderLayout.WEST);
        bottomPanel.add(centerPanel, BorderLayout.CENTER);

        return bottomPanel;

        
    }

    public static void playSong(File songFile) {
        audioFile = songFile; // Update the current audio file
        System.out.println("Playing song: " + audioFile.getPath());
        AudioManager.playAudio(audioFile, playbackSlider, pauseButton, scaledPauseImage);
    }
}

class Song{
    private String title;
    private String duration;
    private String filePath;
    private boolean isPaused;
    private boolean isLooping;
    private Clip clip;


    public Song(String title, String duration, String filePath) {
        this.title = title;
        this.duration = duration;
        this.filePath = filePath;
    }

    public String getTitle() {
        return title;
    }

    public String getDuration() {
        return duration;
    }

    public String getFilePath() {
        return filePath;
    }
/* 
    private void playMusic(int songIndex) {
        File file = null;

        isPaused = false;
        pauseButton.setText("Pause");

        if (clip != null && clip.isRunning()) {
            clip.stop();
        }

        try {
            if(songNumber == 1) {
                file = new File("PARTYNEXTDOOR-CN-TOWER-Ft-DRAKE-(HipHopKit.com).wav");
            } else if(songNumber == 2) {
                file = new File("Spongebob Squarepants.wav");
            }
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(file);

            clip = AudioSystem.getClip();
            clip.open(audioIn);

            if (isLooping) {
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }

            clip.start();

        } catch (Exception e) {
            System.out.println(e);
        }
    }
        */
}

class RoundedBorder implements Border {
    private int radius;

    public RoundedBorder(int radius) {
        this.radius = radius;
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(radius + 1, radius + 1, radius + 1, radius + 1);
    }

    @Override
    public boolean isBorderOpaque() {
        return true;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        g.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
    }
}

class Album{
    protected String title;
    protected String artist;
    protected Song[] songs;

    public Album(String title, String artist, Song[] songs) {
        this.title = title;
        this.artist = artist;
        this.songs = songs;
    }
    
    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public Song[] getSongs() {
        return songs;
    }

    public Song getSong(int index){
        return songs[index];
    }

    public int checkSongIndex(String filePath){
        for(int i = 0; i < songs.length; i++) {
            if(songs[i].getFilePath().equals(filePath)) {
                return i;
            }
        }
        return 0;
    }
}

class AlbumFrame extends Album {

    public AlbumFrame(String title, String artist, Song[] songs) {
        super(title, artist, songs);
    }

    public void displayFrame() {
        JFrame album = new JFrame(title);
        album.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        album.setSize(1000, 700);
        album.setLocationRelativeTo(null);
        album.setLayout(new BorderLayout());

        JPanel topPanel = Layout.homeSearchPanel(album);
        album.add(topPanel, BorderLayout.NORTH); 

        // Create a panel for the album cover, title, and artist
        JPanel albumPanel = new JPanel();
        albumPanel.setPreferredSize(new Dimension(400, 600));
        albumPanel.setLayout(new BoxLayout(albumPanel, BoxLayout.Y_AXIS)); // Use vertical layout
        albumPanel.setBackground(Color.DARK_GRAY); // Set background color for the album panel

        // Album cover
        JLabel albumCover = new JLabel();
        albumCover.setHorizontalAlignment(SwingConstants.CENTER);
        albumCover.setPreferredSize(new Dimension(300, 300));
        ImageIcon albumImage = new ImageIcon("cnTower.png"); // Replace with actual album cover image
        albumCover.setIcon(albumImage);
        albumCover.setAlignmentX(Component.CENTER_ALIGNMENT); // Center-align within the panel

        // Album title
        JLabel albumTitle = new JLabel(title, SwingConstants.CENTER);
        albumTitle.setFont(new Font("Arial", Font.BOLD, 30));
        albumTitle.setAlignmentX(Component.CENTER_ALIGNMENT); // Center-align within the panel

        // Album artist
        JLabel albumArtist = new JLabel(artist, SwingConstants.CENTER);
        albumArtist.setFont(new Font("Arial", Font.ITALIC, 18));
        albumArtist.setAlignmentX(Component.CENTER_ALIGNMENT); // Center-align within the panel

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
        JPanel songPanel = new JPanel();
        songPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 90)); 
        songPanel.setPreferredSize(new Dimension(600, 600)); 

        // Song list panel
        JPanel songListPanel = new JPanel();
        songListPanel.setPreferredSize(new Dimension(500, 500)); 
        songListPanel.setLayout(new GridLayout(0, 2, 5, 5)); 
        songListPanel.setOpaque(false);
        
        for (int i = 0; i < songs.length; i++) { 
            Song song = songs[i];
            String songNumber = (i + 1) + ". ";
            String songTitle = song.getTitle();
            String songDuration = song.getDuration();
        
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
            // Add ActionListener to the button
            addToPlaylistButton.addActionListener(e -> {
                Playlist playlist = new Playlist("My Playlist"); // Example playlist
                playlist.addSong(song); // Add the current song to the playlist
                playlist.displayPlaylist(); // Display the playlist in the console
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
            
            songButton.addActionListener(e -> {
        
                // Revert the button's appearance after a short delay
                Timer timer = new Timer(1000, event -> {
                    songButton.setBackground(Color.WHITE); 
                });
                timer.setRepeats(false); // Ensure the timer only runs once
                timer.start();

                Layout.audioFile = new File(song.getFilePath());
                if(!Layout.songOn){
                JPanel bottomPanel = Layout.musicPanel(Layout.audioFile);
                album.add(bottomPanel, BorderLayout.SOUTH);
                } else {
                    Layout.playSong(Layout.audioFile);
                }
                Layout.songOn = true; 
                album.revalidate(); 
                album.repaint();
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
            
            songListPanel.add(songButton); // Add the button to the songListPanel
        }

        // Add songListPanel to songPanel
        songPanel.add(songListPanel);

        // Add songPanel to the frame
        album.add(songPanel, BorderLayout.EAST);

        if(Layout.songOn) {
            System.out.println("SONGON2" + Layout.audioFile);
            JPanel bottomPanel = Layout.musicPanel(Layout.audioFile);
            album.add(bottomPanel, BorderLayout.SOUTH);
        }

        album.setVisible(true);
    }
}

class Playlist {
    private String name;
    private ArrayList<Song> songs;

    public Playlist(String name) {
        this.name = name;
        this.songs = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public ArrayList<Song> getSongs() {
        return songs;
    }

    public void addSong(Song song) {
        songs.add(song);
        System.out.println("Added " + song.getTitle() + " to playlist: " + name);
    }

    public void removeSong(Song song) {
        songs.remove(song);
        System.out.println("Removed " + song.getTitle() + " from playlist: " + name);
    }

    public void displayPlaylist() {
        System.out.println("Playlist: " + name);
        for (Song song : songs) {
            System.out.println("- " + song.getTitle() + " (" + song.getDuration() + ")");
        }
    }
}

class AudioManager {
    private static Clip clip;
   // private static JSlider playbackSlider;
    private static Timer timer;

    public static void playAudio(File audioFile, JSlider slider, JButton pauseButton, Image scaledPauseImage) {
        try {
            if (clip != null && clip.isRunning()) {
                clip.stop();
            }
            if(timer != null) {
                timer.stop(); // Stop the timer if it's already running
            }

           // playbackSlider = externalSlider; // Assign the slider for playback updates
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            clip = AudioSystem.getClip();
            clip.open(audioStream);

            // Start playback and update the progress bar
                        
            slider.setMaximum(clip.getFrameLength());
            slider.setValue(0);
            slider.revalidate();
            slider.repaint();
            slider.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e){
                    if(clip.isRunning()){
                        clip.stop();
                    }
                }

                @Override
                public void mouseReleased(MouseEvent e){
                    pauseButton.setIcon(new ImageIcon(scaledPauseImage)); // Change icon to pause
                    int newPosition = slider.getValue();
                    clip.setFramePosition(newPosition);
                    clip.start();
                }
            });
            clip.start();

            timer = new Timer(0, e -> {
                if(clip.isOpen() && clip.isRunning()){
                    slider.setValue(clip.getFramePosition());
                }
            });
            timer.start();
            
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
    }

    public static boolean isPlaying() {
        return clip != null && clip.isRunning();
    }

    public static void stopAudio() {
        clip.stop();
    }

    public static void startAudio(){
        clip.start();
    }
}