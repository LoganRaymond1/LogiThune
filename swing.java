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
                new LayoutUI();  
    }
}

interface DisplayableUI {
    void display();
}

class LoginUI implements DisplayableUI {
    protected JFrame loginFrame;

    private JTextField username;
    private JPasswordField password;

    //13 key shift
    User user1 = new User("user1", "cnff1");
    User user2 = new User("user2", "cnff2");
    User admin = new User("admin", "nqzva123");

    User[] users = {user1, user2, admin};

    public LoginUI() {
        loginFrame = new JFrame("Login"); 

        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setSize(400, 250);
        loginFrame.setLocationRelativeTo(null);
        loginFrame.setLayout(null);
        loginFrame.setResizable(false);

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

        display();
    }

    public String decryptPassword(String encryptedPassword) {
        StringBuilder decrypted = new StringBuilder();
        for (char c : encryptedPassword.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = Character.isLowerCase(c) ? 'a' : 'A';
                decrypted.append((char) ((c - base - 13 + 26) % 26 + base)); // 13 key shift
            } else {
                decrypted.append(c); // Non-letter characters remain unchanged
            }
        }
        return decrypted.toString();
    }

    public void checkLogin() {
        String username = this.username.getText();
        String password = new String(this.password.getPassword());

        for (User user : users) {
            if (user.getUsername().equals(username) && decryptPassword(user.getPassword()).equals(password)) {
                JOptionPane.showMessageDialog(loginFrame, "Login successful!");
                loginFrame.dispose(); 
                new DecryptionUI();
                return;
            }
        }
        JOptionPane.showMessageDialog(loginFrame, "Invalid username or password.", "Error", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void display() {
        loginFrame.setVisible(true);
    }
}

class DecryptionUI implements DisplayableUI {
    private JFrame decryptionFrame;
    private String encryptedWords[] = {"fdw", "wkh", "zkhq", "vdpw", "qdph", "frph"};
    private String randomEncryptWord;
    private int random = (int) (Math.random() * encryptedWords.length);
    private int key = (int) (Math.random() * 4 + 1); 
    private String decryptHelp = "To decrypt the word:\n" +
                "1. Use the key to shift each letter of the encrypted word backward in the alphabet.\n" +
                "2. For example, if the key is 3 and the encrypted word is 'fdw', shift each letter back by 3.\n" +
                "3. 'f' becomes 'c', 'd' becomes 'a', and 'w' becomes 't'.\n" +
                "4. Enter the decrypted word in the text field and click Submit.";

    public DecryptionUI() {
        randomEncryptWord = encryptedWords[random];

        decryptionFrame = new JFrame("Are you a Robot?");
        decryptionFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        decryptionFrame.setSize(400, 300); 
        decryptionFrame.setLocationRelativeTo(null);
        decryptionFrame.setLayout(null);
        decryptionFrame.setResizable(false);

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
                LayoutUI.songOn = false;
                new LayoutUI();

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
        display();
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

        @Override
    public void display() {
        decryptionFrame.setVisible(true);
    }
}

class LayoutUI implements DisplayableUI {
    private static JFrame layoutFrame;
    public static boolean songOn; 
    public static File audioFile; // Declare audioFile as a static variable
    public static JSlider playbackSlider; // Declare playbackSlider as a static variable
    public static JButton pauseButton; // Declare pauseButton as a static variable
    public static Image scaledPauseImage; // Declare scaledPauseImage as a static variable
    private static JPanel albumsPanel;
    private boolean isSortButton1Active = false;
    private boolean isSortButton2Active = false;
    private boolean isSortButton3Active = false;
    private boolean isSortButton4Active = false;
    public static boolean shuffle = false;
    public static JPanel rightPanel;
    public static Album currentAlbum;
    public static Album currentAlbumFrame;
    public static boolean playingPlaylist = false; // Variable to track if a playlist is being played
    public static Playlist currentPlaylist; // Variable to store the current playlist being played
    public static boolean paused = false; // Variable to track if a song is currently playing
    public static JPanel bottomPanel; // Declare bottomPanel as a static variable

    
    static Song[] someSexySongs4USongs = {
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

    static Song[] starboy = {
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
    };

    static Song[] sos = {
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
    };

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

    static Song[] utopia = {
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
    };

    static Song[] chromakopia = {
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
    };

    static Album cnTower = new Album("$ome $exy $ongs 4 U", "Drake", someSexySongs4USongs);

    static Object[][] albums = {
        {new ImageIcon("cnTower.png"), "$ome $exy Songs 4U", "Drake", someSexySongs4USongs},
        {new ImageIcon("starboyAlbum.png"), "Starboy", "The Weeknd", starboy},
        {new ImageIcon("SOSAlbum.png"), "SOS", "SZA", sos},
        {new ImageIcon("mozartMusic.png"), "Mozart Classical Music", "Mozart", mozartClassical},
        {new ImageIcon("utopiaAlbum.png"), "Utopia", "Travis Scott", utopia}, 
        {new ImageIcon("chromakopiaAlbum.jpg"), "Chromakopia", "Tyler, The Creator", chromakopia}
    };

    public LayoutUI() {
        audioFile = new File("someSexySongs4U/01-PARTYNEXTDOOR-CN-TOWER-ft-Drake-(JustNaija.wav"); // Declare audioFile as a static variable
        layoutFrame = new JFrame("LogiThune");
        layoutFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        layoutFrame.setSize(1100, 700);
        layoutFrame.setLocationRelativeTo(null);
        layoutFrame.setLayout(new BorderLayout());
        layoutFrame.setResizable(false);


        JPanel topPanel = homeSearchPanel(layoutFrame);        
        

        rightPanel = new JPanel();
        rightPanel.setPreferredSize(new Dimension(800, 700)); // Extend up to 700 pixels from the right
        rightPanel.setLayout(new BorderLayout()); // Use BorderLayout for structure
        rightPanel.setBackground(Color.BLACK);
    
        // Top panel for the "Your Section" label and sort buttons
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
        

        sortButton1.addActionListener(e -> {
            if (!isSortButton1Active) {
                // Sort the albums array by album name (field index 1)
                insertion(albums, 1, true);
                sortButton1.setBackground(Color.GRAY);
                // Highlight the button
                isSortButton1Active = true;
            } else {
                // Reset the albums array to its original order (unsorted)
                resetSort();
                sortButton1.setBackground(Color.WHITE);
                // Reset the button color
                isSortButton1Active = false;
            }
            
            resetPanelSort();
        });

        sortButton2.addActionListener(e -> {
            if (!isSortButton2Active) {
                // Sort the albums array by album name (field index 1)
                insertion(albums, 1, false);
        
                // Highlight the button
                sortButton2.setBackground(Color.GRAY);
                isSortButton2Active = true;
            } else {
                // Reset the albums array to its original order (unsorted)
                resetSort();
        
                // Reset the button color
                sortButton2.setBackground(Color.WHITE);
                isSortButton2Active = false;
            }
            
            resetPanelSort();
        });

        sortButton3.addActionListener(e -> {
            if (!isSortButton3Active) {
                // Sort the albums array by album name (field index 1)
                insertion(albums, 2, true);
        
                // Highlight the button
                sortButton3.setBackground(Color.GRAY);
                isSortButton3Active = true;
            } else {
                // Reset the albums array to its original order (unsorted)
                resetSort();
        
                // Reset the button color
                sortButton3.setBackground(Color.WHITE);
                isSortButton3Active = false;
            }
        
            resetPanelSort();
        });

        sortButton4.addActionListener(e -> {
            if (!isSortButton4Active) {
                // Sort the albums array by album name (field index 1)
                insertion(albums, 2, false);
        
                // Highlight the button
                sortButton4.setBackground(Color.GRAY);
                isSortButton4Active = true;
            } else {
                // Reset the albums array to its original order (unsorted)
                resetSort();
        
                // Reset the button color
                sortButton4.setBackground(Color.WHITE);
                isSortButton4Active = false;
            }
        
            resetPanelSort();
        });
        
    
        JPanel sortingPanel = new JPanel();
        sortingPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10)); // Horizontal gap: 20px, Vertical gap: 10px
        sortingPanel.setBackground(Color.BLACK);

        // Add "Sort Album Name" label and buttons
        sortingPanel.add(sortLabel); // Add "Sort Album Name" label
        sortingPanel.add(sortButton1); // Add ascending button
        sortingPanel.add(sortButton2); // Add descending button

        // Add spacing between the two sorting sections
        sortingPanel.add(Box.createHorizontalStrut(50)); // Add horizontal spacing (50px)

        // Add "Sort Artist Name" label and buttons
        sortingPanel.add(sortLabel2); // Add "Sort Artist Name" label
        sortingPanel.add(sortButton3); // Add ascending button
        sortingPanel.add(sortButton4); // Add descending button

        topSectionPanel.add(sectionLabel);
        topSectionPanel.add(sortingPanel, BorderLayout.CENTER);

    
        // Center panel for albums in a square layout
        albumsPanel = new JPanel();
        albumsPanel.setLayout(new GridLayout(0, 2, 10, 10)); // 10 rows, 2 columns, with gaps
        albumsPanel.setBackground(Color.BLACK);
        
    
        for (Object[] albumData : albums) {
            Album album = new Album((String) albumData[1], (String) albumData[2], (Song[]) albumData[3], (ImageIcon) albumData[0]);

            JButton albumButton = new JButton();
            albumButton.setLayout(new BorderLayout());
            albumButton.setBackground(Color.DARK_GRAY);
            albumButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1)); 
            albumButton.setContentAreaFilled(false);
            albumButton.setOpaque(true);
            albumButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
            albumButton.setMaximumSize(new Dimension(200, 200)); // Set maximum size for square layout

            ImageIcon albumIcon = (ImageIcon) albumData[0];
            Image scaledAlbumIcon = albumIcon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(scaledAlbumIcon);
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

            albumButton.addActionListener(e -> {
                AlbumFrame newAlbum = new AlbumFrame(album.getTitle(), album.getArtist(), album.getSongs(), (ImageIcon) albumData[0]);
                newAlbum.setLayoutFrame(layoutFrame); // Set the layout frame for the new album
                if(currentAlbum == null){
                    System.out.println("assigned");
                    System.out.println(currentAlbum == null);
                    currentAlbum = album;
                    System.out.println(currentAlbum);
                } else {
                    currentAlbumFrame = album;
                }
                
                File currentAudioFile = LayoutUI.audioFile; // Store the current audio file
                int audioFrame = Song.getFrame();
                LayoutUI.audioFile = currentAudioFile; // Restore the audio file in the new layout
                newAlbum.display();
                if(!Song.isPlaying()) {
                    if(audioFrame != 0){
                        System.out.println("PAUSED");
                        Song.stopAudio(); // Stop the current audio if playing
                    } 
                } else {
                    playSong(LayoutUI.audioFile); // Play the current audio file
                    Song.setFrame(audioFrame); // Restore the audio frame
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

            LayoutUI.albumsPanel.add(albumButton);
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
            bottomPanel = musicPanel(audioFile);
            layoutFrame.add(bottomPanel, BorderLayout.SOUTH);
        }

        // Create a panel for playlists
        JPanel playlistsPanel = new JPanel();
        playlistsPanel.setLayout(new BoxLayout(playlistsPanel, BoxLayout.Y_AXIS));
        playlistsPanel.setPreferredSize(new Dimension(300, 600));
        playlistsPanel.setBackground(Color.BLACK);

        JLabel playlistsLabel = new JLabel("Playlists");
        playlistsLabel.setFont(new Font("Arial", Font.BOLD, 16));
        playlistsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        playlistsPanel.add(playlistsLabel);
        playlistsLabel.setForeground(Color.WHITE); 

        // Adjust the layout and size of playlist buttons
        playlistsPanel.setLayout(new BoxLayout(playlistsPanel, BoxLayout.Y_AXIS));
        playlistsPanel.setAlignmentY(Component.BOTTOM_ALIGNMENT); // Align buttons lower down

        for (Playlist playlist : User.playlists) {
            JButton playlistButton = new JButton(playlist.getName());
            playlistButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            playlistButton.setBackground(Color.WHITE); 
            playlistButton.setBorder(new RoundedBorder(10)); 
            playlistButton.setContentAreaFilled(false);
            playlistButton.setOpaque(true);
            playlistButton.setPreferredSize(new Dimension(250, 50)); // Make buttons wider
            playlistButton.setMaximumSize(new Dimension(250, 50)); // Ensure consistent size
            playlistButton.addActionListener(e -> {
                // Create a new frame to display the playlist's songs
                JFrame playlistSongsFrame = new JFrame("Playlist: " + playlist.getName());
                playlistSongsFrame.setSize(400, 600);
                playlistSongsFrame.setLocationRelativeTo(null);
                playlistSongsFrame.setLayout(new BorderLayout());

                // Create a panel to list songs
                JPanel songsPanel = new JPanel();
                songsPanel.setBackground(Color.BLACK);
                songsPanel.add(Box.createVerticalStrut(10)); // Add spacing between buttons
                songsPanel.setLayout(new BoxLayout(songsPanel, BoxLayout.Y_AXIS));

                // Add each song in the playlist to the panel
                for (Song song : playlist.getSongs()) {
                    JButton songButton = new JButton(song.getTitle() + " (" + song.getDuration() + ")");
                    songButton.setBackground(Color.WHITE); // Set text color to white for better visibility
                    songButton.setBorder(new RoundedBorder(10)); // Add rounded border
                    songButton.setContentAreaFilled(false);
                    songButton.setOpaque(true);
                    songButton.setPreferredSize(new Dimension(300, 40)); // Make buttons wider
                    songButton.setMaximumSize(new Dimension(300, 40)); // Ensure consistent size
                    songButton.setAlignmentX(Component.CENTER_ALIGNMENT);
                    songButton.addActionListener(songEvent -> {
                        if(!songOn){
                            bottomPanel = musicPanel(audioFile);
                            layoutFrame.add(bottomPanel, BorderLayout.SOUTH);
                            songOn = true;
                        }
                        // Play the selected song
                        LayoutUI.audioFile = new File(song.getFilePath());
                        LayoutUI.playSong(LayoutUI.audioFile);
                        playingPlaylist = true;
                        LayoutUI.currentPlaylist = playlist;
                    });
                    songsPanel.add(songButton);
                }

                // Add the panel to a scroll pane
                JScrollPane playlistScrollPane = new JScrollPane(songsPanel);
                playlistSongsFrame.add(playlistScrollPane, BorderLayout.CENTER);

                // Show the playlist songs frame
                playlistSongsFrame.setVisible(true);
            });
            playlistsPanel.add(Box.createVerticalStrut(10)); // Add spacing between buttons
            playlistsPanel.add(playlistButton);
        }

        // Add playlistsPanel to the layout beside the album panel
        layoutFrame.add(playlistsPanel, BorderLayout.WEST);

        display();
        }

    public static JPanel homeSearchPanel(JFrame currentFrame) {
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
        homeButton.addActionListener(e -> {
            File currentAudioFile = LayoutUI.audioFile; // Store the current audio file
            int audioFrame = Song.getFrame();
            new LayoutUI(); 
            LayoutUI.audioFile = currentAudioFile; // Restore the audio file in the new layout
            ImageIcon playIcon;
            if((!Song.isPlaying())) {
                Song.stopAudio();
                playIcon = new ImageIcon("play.png");
            } else {
                playIcon = new ImageIcon("pause.png");
                playSong(LayoutUI.audioFile); // Play the current audio file
                Song.setFrame(audioFrame); // Restore the audio frame

            }
            Image scaledPlayImage = playIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
            if(pauseButton != null){
                pauseButton.setIcon(new ImageIcon(scaledPlayImage)); // Change icon to play
            }

            currentFrame.dispose(); 


        });

        homeButton.setToolTipText("Go to Home");

        JTextField searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(300, 30));
        searchField.setToolTipText("Search for songs, albums, or artists");

        JButton searchButton = new JButton("Search");
        searchButton.setPreferredSize(new Dimension(80, 30));
        searchButton.setBackground(Color.WHITE);
        searchButton.setBorder(new RoundedBorder(10));
        searchButton.setContentAreaFilled(false);
        searchButton.setOpaque(true);

        searchField.addActionListener(e -> {
            handleSearch(albums, searchField.getText().trim());
        });

        searchButton.addActionListener(e -> {
            handleSearch(albums, searchField.getText().trim());
        });

        searchHomePanel.add(homeButton);
        searchHomePanel.add(searchField);
        searchHomePanel.add(searchButton);
        
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

        JMenuItem loginItem = new JMenuItem("Account");
        menu.add(loginItem);
        loginItem.addActionListener(e -> {
            JOptionPane.showMessageDialog(layoutFrame, "Account settings will be implemented soon.");
        });

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
                    + "</body></html>";
        
            JEditorPane editorPane = new JEditorPane("text/html", supportMessage);
            editorPane.setEditable(false); // Make the editor pane non-editable
            editorPane.setFont(new Font("Arial", Font.PLAIN, 14)); // Set font for better readability
        
            JScrollPane scrollPane = new JScrollPane(editorPane);
            scrollPane.setPreferredSize(new Dimension(400, 300)); // Set the size of the scroll pane
        
            JOptionPane.showMessageDialog(layoutFrame, scrollPane, "Support", JOptionPane.INFORMATION_MESSAGE);
        });

        JMenuItem settingsItem = new JMenuItem("Settings");
        menu.add(settingsItem);
        settingsItem.addActionListener(e -> {
            
        });

        JMenuItem logoutItem = new JMenuItem("Log out");
        menu.add(logoutItem);
        logoutItem.addActionListener(e -> {
            int response = JOptionPane.showConfirmDialog(layoutFrame, "Are you sure you want to log out?", "Log Out", JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.YES_OPTION) {
                Song.stopAudio();
                layoutFrame.dispose(); 
                new LoginUI(); 
            }
        });
        
        menuBar.add(menu);
        accountMenuPanel.add(menuBar);

        topPanel.add(searchHomePanel, BorderLayout.CENTER);
        topPanel.add(accountMenuPanel, BorderLayout.EAST);

        return topPanel;
    }

    public static JPanel musicPanel(File audioFile) {
        bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.setPreferredSize(new Dimension(1100, 70));
        bottomPanel.setBackground(Color.BLACK);

        // Left panel for album cover and song title
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

        // Center panel for playback bar and play button
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10)); // Center-align
        centerPanel.setBackground(Color.BLACK);

        ImageIcon pauseIcon = new ImageIcon("pause.png");
        scaledPauseImage = pauseIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        pauseButton = new JButton(new ImageIcon(scaledPauseImage)); 
        pauseButton.setBackground(Color.WHITE);
        pauseButton.setFocusPainted(false);
        centerPanel.add(pauseButton);
        
        
        playbackSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 0);
        playbackSlider.setBounds(350, 670, 650, 630);
        playbackSlider.setPreferredSize(new Dimension(400, 30));
        playbackSlider.setForeground(Color.DARK_GRAY);
        playbackSlider.setBackground(Color.BLACK);
        playbackSlider.setOpaque(true);
        centerPanel.add(playbackSlider);


        pauseButton.addActionListener(e -> {
            if (Song.isPlaying()) {
                Song.stopAudio();
                ImageIcon playIcon = new ImageIcon("play.png");
                Image scaledPlayImage = playIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
                pauseButton.setIcon(new ImageIcon(scaledPlayImage)); // Change icon to play
                paused = true;
            } else {
                Song.startAudio();
                pauseButton.setIcon(new ImageIcon(scaledPauseImage)); // Change icon to pause
                paused = false;
            }
        });
        if(!Song.isPlaying()) {
            Song.playAudio( LayoutUI.audioFile, playbackSlider, pauseButton, scaledPauseImage); // Start playing the audio
        }

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 10)); // Align to the right
        rightPanel.setBackground(Color.BLACK);

        ImageIcon prevIcon = new ImageIcon("prevSong.png");
        Image scaledPrevImage = prevIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        JButton prevButton = new JButton(new ImageIcon(scaledPrevImage));
        prevButton.setBackground(Color.WHITE);
        prevButton.setFocusPainted(false);
        rightPanel.add(prevButton);

        prevButton.addActionListener(e -> {
            // Logic to play the previous song
            pauseButton.setIcon(new ImageIcon(scaledPauseImage)); // Ensure pause icon is set
            int currentSongIndex = 0;
            System.out.println("Playing playlist: " + playingPlaylist);
            if(playingPlaylist){
                for (int i = 0; i < LayoutUI.currentPlaylist.getSongs().size(); i++) {
                    if ( LayoutUI.currentPlaylist.getSongs().get(i).getFilePath().equals( LayoutUI.audioFile.getPath().replace('\\', '/'))) {
                        currentSongIndex = i;
                        break;
                    }
                }
            } else {
                currentSongIndex = LayoutUI.currentAlbum.checkSongIndex( LayoutUI.audioFile.getPath().replace('\\', '/'));
            }
            
            int randomIndex = currentSongIndex;
            System.out.println("Current song index: " + currentSongIndex);
            if(shuffle){
                while(randomIndex == currentSongIndex ){
                    if(playingPlaylist && currentPlaylist.getSongs().size() > 1){
                        randomIndex = (int) (Math.random() * LayoutUI.currentPlaylist.getSongs().size());
                        LayoutUI.audioFile = new File( LayoutUI.currentPlaylist.getSongs().get(randomIndex).getFilePath());
                    } else {
                        randomIndex = (int) (Math.random() * LayoutUI.currentAlbum.songs.length);
                        LayoutUI.audioFile = new File( LayoutUI.currentAlbum.getSong(randomIndex).getFilePath());
                    }
                }
            } else if(playingPlaylist){
                if(currentSongIndex > 0){
                    LayoutUI.audioFile = new File( LayoutUI.currentPlaylist.getSongs().get(currentSongIndex - 1).getFilePath());

                } else{
                    LayoutUI.audioFile = new File( LayoutUI.currentPlaylist.getSongs().get( LayoutUI.currentPlaylist.getSongs().size() - 1).getFilePath());

                }
            } else if(currentSongIndex > 0){
                LayoutUI.audioFile = new File( LayoutUI.currentAlbum.getSong(currentSongIndex - 1).getFilePath());

            } else{
                LayoutUI.audioFile = new File( LayoutUI.currentAlbum.getSong(currentAlbum.songs.length - 1).getFilePath());

            }
            playSong( LayoutUI.audioFile);
            
        });

        ImageIcon nextIcon = new ImageIcon("nextSong.png");
        Image scaledNextImage = nextIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        JButton nextButton = new JButton(new ImageIcon(scaledNextImage));
        nextButton.setBackground(Color.WHITE);
        nextButton.setFocusPainted(false);
        rightPanel.add(nextButton);

        nextButton.addActionListener(e -> {
            // Logic to play the previous song
            pauseButton.setIcon(new ImageIcon(scaledPauseImage)); // Ensure pause icon is set
            int currentSongIndex = 0;
            System.out.println("Playing playlist: " + playingPlaylist);
            if(playingPlaylist){
                for (int i = 0; i < LayoutUI.currentPlaylist.getSongs().size(); i++) {
                    if ( LayoutUI.currentPlaylist.getSongs().get(i).getFilePath().equals( LayoutUI.audioFile.getPath().replace('\\', '/'))) {
                        currentSongIndex = i;
                        break;
                    }
                }
            } else {
                currentSongIndex = LayoutUI.currentAlbum.checkSongIndex( LayoutUI.audioFile.getPath().replace('\\', '/'));
            }
            
            int randomIndex = currentSongIndex;
            System.out.println("Current song index: " + currentSongIndex);
            if(shuffle){
                while(randomIndex == currentSongIndex ){
                    if(playingPlaylist && currentPlaylist.getSongs().size() > 1){
                        randomIndex = (int) (Math.random() * LayoutUI.currentPlaylist.getSongs().size());
                        LayoutUI.audioFile = new File( LayoutUI.currentPlaylist.getSongs().get(randomIndex).getFilePath());
                    } else {
                        randomIndex = (int) (Math.random() * LayoutUI.currentAlbum.songs.length);
                        LayoutUI.audioFile = new File( LayoutUI.currentAlbum.getSong(randomIndex).getFilePath());
                    }
                }
            } else if(playingPlaylist){
                if(currentSongIndex < LayoutUI.currentPlaylist.getSongs().size() - 1){
                    LayoutUI.audioFile = new File( LayoutUI.currentPlaylist.getSongs().get(currentSongIndex + 1).getFilePath());

                } else{
                    LayoutUI.audioFile = new File( LayoutUI.currentPlaylist.getSongs().get(0).getFilePath());

                }
            } else if(currentSongIndex < LayoutUI.currentAlbum.songs.length - 1){
                LayoutUI.audioFile = new File( LayoutUI.currentAlbum.getSong(currentSongIndex + 1).getFilePath());

            } else{
                LayoutUI.audioFile = new File( LayoutUI.currentAlbum.getSong(0).getFilePath());

            }
            playSong( LayoutUI.audioFile);
        });

        ImageIcon shuffleIcon = new ImageIcon("shuffle.png");
        Image scaledShuffleImage = shuffleIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        JButton shuffleButton = new JButton(new ImageIcon(scaledShuffleImage));
        shuffleButton.setBackground(Color.WHITE);
        shuffleButton.setFocusPainted(false);
        rightPanel.add(shuffleButton);

        shuffleButton.addActionListener(e -> {
            // Logic to shuffle the songs
            if(shuffle){
                shuffleButton.setBackground(Color.WHITE);
                shuffle = false;
            } else {
                shuffleButton.setBackground(Color.GRAY);
                shuffle = true;
            }
        });
        
        ImageIcon loopIcon = new ImageIcon("loop.png");
        Image scaledLoopImage = loopIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        JButton loopButton = new JButton(new ImageIcon(scaledLoopImage));
        loopButton.setBackground(Color.WHITE);
        loopButton.setFocusPainted(false);
        rightPanel.add(loopButton);

        loopButton.addActionListener(e -> {
            // Logic to toggle loop
            if(Song.isLooping()){
                Song.setLooping(false);
                loopButton.setBackground(Color.WHITE);

            } else {
                Song.setLooping(true);
                loopButton.setBackground(Color.GRAY);

            }
        });

        bottomPanel.add(rightPanel, BorderLayout.EAST);
        bottomPanel.add(leftPanel, BorderLayout.WEST);
        bottomPanel.add(centerPanel, BorderLayout.CENTER);

        return bottomPanel;
    }

    public static void playSong(File songFile) {
        audioFile = songFile; // Update the current audio file
        Song.playAudio(audioFile, playbackSlider, pauseButton, scaledPauseImage);
        // Update the album cover in the bottom panel based on the current song's album
        JLabel albumCover = (JLabel) ((JPanel) LayoutUI.bottomPanel.getComponent(1)).getComponent(0); // Assuming the album cover is the first component in the left panel
        // Find the album that contains the current song
        if(playingPlaylist){
            for (Object[] albumData : LayoutUI.albums) {
                Song[] songs = (Song[]) albumData[3];
                for (Song song : songs) {
                    if (song.getFilePath().equals( LayoutUI.audioFile.getPath().replace('\\', '/'))) {
                        LayoutUI.currentAlbum  = new Album((String) albumData[1], (String) albumData[2], songs, (ImageIcon) albumData[0]);
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
        JPanel songTitle = (JPanel) LayoutUI.bottomPanel.getComponent(1); // Assuming the song title is the second component in leftPanel
        JLabel songTitleLabel = (JLabel) songTitle.getComponent(1); // Assuming the song title is the second component in leftPanel
        String songTitleText = LayoutUI.currentAlbum.getSong( LayoutUI.currentAlbum.checkSongIndex( LayoutUI.audioFile.getPath().replace('\\', '/'))).getTitle();
        if(songTitleText.length() > 15) {
            songTitleText = songTitleText.substring(0, 12) + "..."; // Truncate to 15 characters and add ellipsis
        }
        songTitleLabel.setText(songTitleText);
        songTitleLabel.revalidate();
        songTitleLabel.repaint();
    }

    private static void insertion(Object[][] albums, int index, boolean ascending) {
        for (int i = 1; i < albums.length; i++) {
            Object[] key = albums[i];
            String keyField = (String) key[index]; // Extract the field to sort by
            int j = i - 1;
    
            // Compare the field values and shift elements based on the sorting direction
            while (j >= 0) {
                String sortItem = (String) albums[j][index];
                if (ascending) {
                    // Ascending order comparison
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

    private static void handleSearch(Object[][] albums, String search) {
        if (search.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter a search term.");
            return;
        }
        
        insertion(albums, 1, true);

        int left = 0;
        int right = albums.length - 1;

        while(left <= right) {
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

        while(left <= right) {
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

        for(Object[] albumData : albums) {
            Song[] songs = (Song[]) albumData[3];
            for(Song song : songs) {
                if(song.getTitle().equalsIgnoreCase(search)) {
                    Album album = new Album((String) albumData[1], (String) albumData[2], songs, (ImageIcon) albumData[0]);
                    AlbumFrame newAlbum = new AlbumFrame(album.getTitle(), album.getArtist(), album.getSongs(), (ImageIcon) albumData[0]);
                    newAlbum.display();
                    layoutFrame.dispose();
                    return;
                }
            }
        }

        JOptionPane.showMessageDialog(layoutFrame, "No results found for: " + search, "Search Result", JOptionPane.INFORMATION_MESSAGE);
    }

    private static void resetSort() {
        albums = new Object[][] {
            {new ImageIcon("cnTower.png"), "$ome $exy Songs 4U", "Drake", someSexySongs4USongs},
            {new ImageIcon("starboyAlbum.png"), "Starboy", "The Weeknd", starboy},
            {new ImageIcon("SOSAlbum.png"), "SOS", "SZA", sos},
            {new ImageIcon("mozartMusic.png"), "Mozart Classical Music", "Mozart", mozartClassical},
            {new ImageIcon("utopiaAlbum.png"), "Utopia", "Travis Scott", utopia},
            {new ImageIcon("chromakopiaAlbum.jpg"), "Chromakopia", "Tyler, The Creator", chromakopia}
        };
    }

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

        // Load and scale the album cover image
        ImageIcon albumIcon = (ImageIcon) albumData[0];
        Image scaledAlbumIcon = albumIcon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
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

        albumButton.addActionListener(event -> {
            AlbumFrame newAlbum = new AlbumFrame(album.getTitle(), album.getArtist(), album.getSongs(), (ImageIcon) albumData[0]);
            if(currentAlbum == null) {
                currentAlbum = album; // Update the current album
            } else {
                currentAlbumFrame = album; // Update the current album frame
            }
            System.out.println("Current Album: " + currentAlbum.getTitle());
            File currentAudioFile = LayoutUI.audioFile;
            int audioFrame = Song.getFrame();
            LayoutUI.audioFile = currentAudioFile;
            newAlbum.display();
            if(!Song.isPlaying()) {
                if(audioFrame != 0) {
                    Song.stopAudio();
                }
            }
            else {
                playSong( LayoutUI.audioFile);
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
    
        // Refresh the albums panel
        albumsPanel.revalidate();
        albumsPanel.repaint();
    }

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
                ImageIcon albumIcon = (ImageIcon) albumData[0];
                Image scaledAlbumIcon = albumIcon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
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
        
                albumButton.addActionListener(event -> {
                    AlbumFrame newAlbum = new AlbumFrame(album.getTitle(), album.getArtist(), album.getSongs(), (ImageIcon) albumData[0]);
                    if(currentAlbum == null) {
                        currentAlbum = album; // Update the current album
                    } else {
                        currentAlbumFrame = album; // Update the current album frame
                    }
                    System.out.println("Current Album: " + currentAlbum.getTitle());
                    File currentAudioFile = LayoutUI.audioFile;
                    int audioFrame = Song.getFrame();
                    LayoutUI.audioFile = currentAudioFile;
                    newAlbum.display();
                    if(!Song.isPlaying()) {
                        if(audioFrame != 0) {
                            Song.stopAudio();
                        }
                    }
                    else {
                        playSong( LayoutUI.audioFile);
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
        }    }

class Song{
    private String title;
    private String duration;
    private String filePath;
    private boolean isPaused;
    private boolean isLooping;
    private static Clip clip;
    private static Timer timer;
    private static boolean looping = false;


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
                    if(looping) {
                        clip.loop(Clip.LOOP_CONTINUOUSLY);
                    }
                }
            });
            clip.start();
            if(looping) {
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }

            timer = new Timer(0, e -> {
                if(clip.isOpen() && clip.isRunning()){
                    slider.setValue(clip.getFramePosition());
                }
                if(clip.getFramePosition() >= clip.getFrameLength()) {
                    if(looping) {
                        clip.setFramePosition(0); // Reset to the beginning if looping
                    } else {
                        // Logic to play the previous song
                        pauseButton.setIcon(new ImageIcon(scaledPauseImage)); // Ensure pause icon is set
                        int currentSongIndex = 0;
                        if(LayoutUI.playingPlaylist){
                            for (int i = 0; i < LayoutUI.currentPlaylist.getSongs().size(); i++) {
                                if ( LayoutUI.currentPlaylist.getSongs().get(i).getFilePath().equals( LayoutUI.audioFile.getPath().replace('\\', '/'))) {
                                    currentSongIndex = i;
                                    break;
                                }
                            }
                        } else {
                            currentSongIndex = LayoutUI.currentAlbum.checkSongIndex( LayoutUI.audioFile.getPath().replace('\\', '/'));
                        }
                        
                        int randomIndex = currentSongIndex;
                        System.out.println("Current song index: " + currentSongIndex);
                        if(LayoutUI.shuffle){
                            while(randomIndex == currentSongIndex){
                                if(LayoutUI.playingPlaylist){
                                    randomIndex = (int) (Math.random() * LayoutUI.currentPlaylist.getSongs().size());
                                    LayoutUI.audioFile = new File( LayoutUI.currentPlaylist.getSongs().get(randomIndex).getFilePath());
                                } else {
                                    randomIndex = (int) (Math.random() * LayoutUI.currentAlbum.songs.length);
                                    LayoutUI.audioFile = new File( LayoutUI.currentAlbum.getSong(randomIndex).getFilePath());
                                }
                            }
                        } else if(LayoutUI.playingPlaylist){
                            if(currentSongIndex < LayoutUI.currentPlaylist.getSongs().size() - 1){
                                LayoutUI.audioFile = new File( LayoutUI.currentPlaylist.getSongs().get(currentSongIndex + 1).getFilePath());

                            } else{
                                LayoutUI.audioFile = new File( LayoutUI.currentPlaylist.getSongs().get(0).getFilePath());

                            }
                        } else if(currentSongIndex < LayoutUI.currentAlbum.songs.length - 1){
                            LayoutUI.audioFile = new File( LayoutUI.currentAlbum.getSong(currentSongIndex + 1).getFilePath());

                        } else{
                            LayoutUI.audioFile = new File( LayoutUI.currentAlbum.getSong(0).getFilePath());

                        }
                        LayoutUI.playSong( LayoutUI.audioFile);
                    }
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
        if(clip != null){
        clip.stop();
        }
    }

    public static void startAudio(){
        clip.start();
    }

    public static boolean isLooping() {
        return looping;
    }
    public static void setLooping(boolean loop){
        if(clip != null && clip.isRunning()){
            if(loop) {
                looping = true;
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            } else {
                looping = false;
                clip.loop(0);
            }
        }
    }

    public static int getFrame(){
        if(clip != null) {
            return clip.getFramePosition();
        }
        return 0;
    }
    public static void setFrame(int frame) {
        if(clip != null) {
            clip.setFramePosition(frame);
        }
    }

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
    protected ImageIcon albumCover;

    public Album(String title, String artist, Song[] songs, ImageIcon albumCover) {
        this.title = title;
        this.artist = artist;
        this.songs = songs;
        this.albumCover = albumCover;
    }

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

    public ImageIcon getCover(){
        return albumCover;
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

class AlbumFrame extends Album implements DisplayableUI {

    private ImageIcon albumCoverImage;
    private JFrame layoutFrame; // Add a field to store the layout frame

    public AlbumFrame(String title, String artist, Song[] songs, ImageIcon albumCoverImage) {
        super(title, artist, songs);
        this.albumCoverImage = albumCoverImage;
    }

    public void setLayoutFrame(JFrame layoutFrame) {
        this.layoutFrame = layoutFrame; // Store the layout frame
    }

    public void display() {
        JFrame album = new JFrame(title);
        album.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        album.setSize(1100, 700);
        album.setLocationRelativeTo(null);
        album.setLayout(new BorderLayout());
        album.setResizable(false);

        JPanel topPanel = LayoutUI.homeSearchPanel(album);
        album.add(topPanel, BorderLayout.NORTH); 

        // Create a panel for the album cover, title, and artist
        JPanel albumPanel = new JPanel();
        albumPanel.setPreferredSize(new Dimension(500, 600));
        albumPanel.setLayout(new BoxLayout(albumPanel, BoxLayout.Y_AXIS)); // Use vertical layout
        albumPanel.setBackground(Color.BLACK); // Set background color for the album panel

        // Album cover
        JLabel albumCover = new JLabel();
        albumCover.setHorizontalAlignment(SwingConstants.CENTER);
        albumCover.setPreferredSize(new Dimension(300, 300));
        ImageIcon albumImage = albumCoverImage; // Cast albumCoverImage to String before creating ImageIcon
        albumCover.setIcon(albumImage);
        albumCover.setAlignmentX(Component.CENTER_ALIGNMENT); // Center-align within the panel
        albumCover.setBorder(BorderFactory.createLineBorder(Color.WHITE, 10)); // Add top and bottom padding

        // Album title
        JLabel albumTitle = new JLabel(title, SwingConstants.CENTER);
        albumTitle.setFont(new Font("Arial", Font.BOLD, 30));
        albumTitle.setAlignmentX(Component.CENTER_ALIGNMENT); // Center-align within the panel
        albumTitle.setForeground(Color.WHITE); // Set text color to white for better visibility

        // Album artist
        JLabel albumArtist = new JLabel(artist, SwingConstants.CENTER);
        albumArtist.setFont(new Font("Arial", Font.ITALIC, 18));
        albumArtist.setAlignmentX(Component.CENTER_ALIGNMENT); // Center-align within the panel
        albumArtist.setForeground(Color.LIGHT_GRAY); // Set text color to light gray for better visibility

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
        songPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 40)); 
        songPanel.setPreferredSize(new Dimension(600, 600)); 
        songPanel.setBackground(Color.LIGHT_GRAY); // Set background color for the song panel

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
                // Create a new frame for the playlist options
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

                    // Add each playlist to the panel
                    for (Playlist playlist : User.playlists) {
                        JButton playlistButton = new JButton(playlist.getName());
                        playlistButton.setAlignmentX(Component.CENTER_ALIGNMENT);
                        playlistButton.setBackground(Color.WHITE); 
                        playlistButton.setBorder(new RoundedBorder(10)); 
                        playlistButton.setContentAreaFilled(false);
                        playlistButton.setOpaque(true);
                        playlistButton.setPreferredSize(new Dimension(250, 50)); // Make buttons wider
                        playlistButton.setMaximumSize(new Dimension(250, 50)); // Ensure consistent size
                        playlistButton.addActionListener(playlistEvent -> {
                            // Logic to handle playlist selection
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
                    // Prompt the user to enter a playlist name
                    String playlistName = JOptionPane.showInputDialog(playlistFrame, "Enter Playlist Name:", "New Playlist", JOptionPane.PLAIN_MESSAGE);
                    if (playlistName != null && !playlistName.trim().isEmpty()) {
                        // Logic to create a new playlist
                        User.createPlaylist(playlistName);
                        User.addSong(User.playlists.get(User.playlists.size() - 1), song); // Add the song to the new playlist
                        JOptionPane.showMessageDialog(playlistFrame, "Playlist '" + playlistName + "' created.", "Success", JOptionPane.INFORMATION_MESSAGE);
                        playlistFrame.dispose(); // Close the playlist frame after creation
                    } else {
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
            
            songButton.addActionListener(e -> {
                LayoutUI.playingPlaylist = false;

                if( LayoutUI.currentAlbum != LayoutUI.currentAlbumFrame && LayoutUI.currentAlbumFrame != null) {
                    LayoutUI.currentAlbum = LayoutUI.currentAlbumFrame; // Update the current album
                }
                // Revert the button's appearance after a short delay
                Timer timer = new Timer(1000, event -> {
                    songButton.setBackground(Color.WHITE); 
                });
                timer.setRepeats(false); // Ensure the timer only runs once
                timer.start();
                LayoutUI.audioFile = new File(song.getFilePath());
                if(!LayoutUI.songOn){
                    LayoutUI.bottomPanel = LayoutUI.musicPanel( LayoutUI.audioFile);
                    album.add(LayoutUI.bottomPanel, BorderLayout.SOUTH);
                    LayoutUI.playSong( LayoutUI.audioFile);
                } else {
                    
                    LayoutUI.pauseButton.setIcon(new ImageIcon(LayoutUI.scaledPauseImage)); 
                    LayoutUI.playSong( LayoutUI.audioFile);
                }
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
            
            songListPanel.add(songButton); // Add the button to the songListPanel
        }

        // Add songListPanel to songPanel
        songPanel.add(songListPanel);

        // Add songPanel to the frame
        album.add(songPanel, BorderLayout.EAST);

        if(LayoutUI.songOn) {
            JPanel bottomPanel = LayoutUI.musicPanel( LayoutUI.audioFile);
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



abstract class Account{
    private String username;
    private String encryptedPassword;

    public Account(String username, String encryptedPassword){
        this.username = username;
        this.encryptedPassword = encryptedPassword;
    }

    public String getPassword() {
        return encryptedPassword;
    }
    public String getUsername() {
        return username;
    }
}

class User extends Account {
    static ArrayList<Playlist> playlists = new ArrayList<>();

    public User(String username, String encryptedPassword){
        super(username, encryptedPassword);
    }


    public static void createPlaylist(String playlistName){
        Playlist newPlaylist = new Playlist(playlistName);
        playlists.add(newPlaylist);

    }
    public static void addSong(Playlist playlist, Song song) {
            playlist.addSong(song);
    }
}
