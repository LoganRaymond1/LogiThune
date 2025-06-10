import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.Timer;
import javax.swing.border.Border;





/** 
 * The LogiThune class serves as the running point for the LogiThune music player.
 * It initializes the login interface to start the application.
*/
public class LogiThune {


    /**
     * The main method initializes the LoginUI, which is the entry to the music player.
     * The login interface is initalized to run the application.
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        new LoginUI(); 
    }
}






/**
 * DisplayableUI is an interface that defines a method for displaying user interfaces.
 * Classes implementing this interface provide an implementation to display its UI.
 */
interface DisplayableUI {


    /**
     * Displays the user interface.
     */
    void display();
}





/**
 * The LoginUI class represents the login interface for LogiThune music player.
 * It provides users with a frame to enter their username and password.
 * With a successful login (with provided logins), it transitions to the DecryptionUI for further security.
 */
class LoginUI implements DisplayableUI {
    

    private JFrame loginFrame; // The main frame for the login interface.
    private JTextField username; // Text field for entering the username.
    private JPasswordField password; // Password field for entering the password.
    private JLabel usernameLabel; // Label for the username field.
    private JLabel passwordLabel; // Label for the password field.
    private JButton loginButton; // Button to submit the login credentials.


    /**
     * Predefined user credentials for login.
     * The passwords are encrypted using a simple Caesar cipher with a key of 13.
     */
    User user1 = new User("user1", "cnff1"); // password is pass1
    User user2 = new User("user2", "cnff2"); // password is pass2
    User admin = new User("admin", "nqzva123"); // password is admin123
    User[] users = {user1, user2, admin}; //Array of predefined users.


    /**
     * Constructor for LoginUI.
     * Initializes the login frame, labels, text fields, and buttons.
     */
    public LoginUI() {
        loginFrame = new JFrame("Login"); 
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //E xits the application when the frame is closed
        loginFrame.setSize(400, 250); 
        loginFrame.setLocationRelativeTo(null); // Centers the frame on the screen
        loginFrame.setLayout(null); // No layout manager, using absolute positioning
        loginFrame.setResizable(false); // Prevents the frame from being resized


        usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(50, 50, 100, 30); 
        loginFrame.add(usernameLabel); 


        username = new JTextField();
        username.setBounds(150, 50, 200, 30);
        loginFrame.add(username);


        passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(50, 100, 100, 30);
        loginFrame.add(passwordLabel);


        password = new JPasswordField();
        password.setBounds(150, 100, 200, 30);
        loginFrame.add(password);


        loginButton = new JButton("Login");
        loginButton.setBounds(150, 150, 100, 30);
        loginFrame.add(loginButton);


        loginButton.addActionListener(e -> checkLogin()); // When the login button is clicked, login is checked in checkLogin()
        loginFrame.getRootPane().setDefaultButton(loginButton); // Set the default button for Enter key


        display(); // Display the login frame
    }



    /**
     * Decrypts the password using a Caesar cipher with a key of 13.
     * Method is used to compare the entered password with the stored encrypted passwords.
     * 
     * @param encryptedPassword The encrypted password to decrypt.
     * @return The decrypted password as a String.
     */
    public String decryptPassword(String encryptedPassword) {
        StringBuilder decrypted = new StringBuilder(); 
        for (char c : encryptedPassword.toCharArray()) { // Iterates through each character in the encrypted password
            if (Character.isLetter(c)) {
                char base = Character.isLowerCase(c) ? 'a' : 'A'; // Determines the base character being lowercase or uppercase letters
                decrypted.append((char) ((c - base - 13 + 26) % 26 + base)); // 13 key shift
            } else {
                decrypted.append(c); 
            }
        }
        return decrypted.toString();
    }


    /**
     * Checks the entered username and password with the predefined credentials.
     * If a match is found, success message appears and transitions to the DecryptionUI.
     * If no match is found, error message appears.
     */
    private void checkLogin() {
        String username = this.username.getText(); // Gets the text from the username field.
        String password = new String(this.password.getPassword()); // Gets the text from the password field.

        // Iterates through the predefined users to check for a match.
        for (User user : users) { 
            if (user.getUsername().equals(username) && decryptPassword(user.getPassword()).equals(password)) { // Checks if the entered username and decrypted password match a predefined user.
                JOptionPane.showMessageDialog(loginFrame, "Login successful!");
                loginFrame.dispose(); // Closes the login frame.
                new DecryptionUI(); // Opens the DecryptionUI if correct login.
                return;
            }
        }
        JOptionPane.showMessageDialog(loginFrame, "Invalid username or password.", "Error", JOptionPane.ERROR_MESSAGE); // Error message
    }

    /**
     * Displays the login frame.
     * This method is called to make the login frame visible.
     */
    @Override
    public void display() {
        loginFrame.setVisible(true);
    }
}





/**
 * The DecryptionUI class represents the decryption interface for extra security.
 * Users must enter the decrypted letter sequence based on a randomly selected encrypted sequence and key.
 */
class DecryptionUI implements DisplayableUI {

    private JFrame decryptionFrame; // The main frame for the decryption interface.
    private String encryptedWords[] = {"fdw", "wkh", "zkhq", "vdpw", "qdph", "frph"}; // Array of encrypted words.
    private String randomEncryptWord; // Randomly selected encrypted word from the array.
    private JLabel encryptedWordLabel; // Label to display the encrypted word.
    private JLabel keyLabel; // Label to display the decryption key.
    private JTextField textField; // Text field for user input to enter the decrypted word.
    private JButton decryptButton; // Button to submit the decrypted word.
    private JButton helpButton; // Button to show help instructions for decryption.
    private int random = (int) (Math.random() * encryptedWords.length); // Randomly selects an index for the encryptedWords array.
    private int key = (int) (Math.random() * 4 + 1); // Randomly generates a key between 1 and 4 for decryption.


    /**
     * Constructor for DecryptionUI.
     * Initializes the decryption frame, labels, text fields, and buttons.
     */
    public DecryptionUI() {
        randomEncryptWord = encryptedWords[random]; // Selects a random encrypted word from the array.

        decryptionFrame = new JFrame("Are you a Robot?");
        decryptionFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        decryptionFrame.setSize(400, 300);
        decryptionFrame.setLocationRelativeTo(null);
        decryptionFrame.setLayout(null);
        decryptionFrame.setResizable(false);

        encryptedWordLabel = new JLabel(randomEncryptWord, SwingConstants.CENTER); // randomEncryptWord is displayed in the center of the label.
        encryptedWordLabel.setBounds(50, 20, 300, 50);
        encryptedWordLabel.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 24)); 
        encryptedWordLabel.setBorder(BorderFactory.createEmptyBorder()); // Creates an empty border around the label.
        decryptionFrame.add(encryptedWordLabel);

        keyLabel = new JLabel("Key: " + key);
        keyLabel.setBounds(50, 70, 300, 30); 
        decryptionFrame.add(keyLabel);

        textField = new JTextField();
        textField.setBounds(50, 100, 300, 30);
        decryptionFrame.add(textField);

        decryptButton = new JButton("Submit");
        decryptButton.setBounds(150, 150, 100, 30);
        decryptionFrame.add(decryptButton);

        // User submits and it checks if the user input matches the decrypted word
        decryptButton.addActionListener(e -> {
            String userInput = textField.getText(); // Gets the text from the text field.
            String decryptedWord = decrypt(randomEncryptWord, key);
            if (userInput.equalsIgnoreCase(decryptedWord)) {
                JOptionPane.showMessageDialog(decryptionFrame, "Correct! The decrypted word was: " + decryptedWord);
                decryptionFrame.dispose(); // Closes the decryption frame.
                LayoutUI.songOn = false; 
                new LayoutUI(); // If decyption is correct, LayoutUI opens.

            } else {
                JOptionPane.showMessageDialog(decryptionFrame, "Incorrect! Try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        helpButton = new JButton("Help");
        helpButton.setBounds(150, 200, 100, 30); 
        decryptionFrame.add(helpButton);


        // Help button provides instructions on how to decrypt the word
        helpButton.addActionListener(e -> {
            String decryptHelp = "To decrypt the word:\n" +
                "1. Use the key to shift each letter of the encrypted word backward in the alphabet.\n" +
                "2. For example, if the key is 3 and the encrypted word is 'fdw', shift each letter back by 3.\n" +
                "3. 'f' becomes 'c', 'd' becomes 'a', and 'w' becomes 't'.\n" +
                "4. Enter the decrypted word in the text field and click Submit.";
            JOptionPane.showMessageDialog(decryptionFrame, decryptHelp,"How to Decrypt", JOptionPane.INFORMATION_MESSAGE);
        });

        decryptionFrame.getRootPane().setDefaultButton(decryptButton); // Set the default button for Enter key
        display();
    }


    /**
     * Decrypts the given text using a Caesar cipher with the specified key.
     * This method shifts each letter backward in the alphabet by the key value.
     * 
     * @param text The encrypted text to decrypt.
     * @param key The key used for decryption.
     * @return The decrypted text as a String.
     */
    private String decrypt(String text, int key) {
        StringBuilder decrypted = new StringBuilder();
        for (char c : text.toCharArray()) { // Iterates through each character in the encrypted text.
            if (Character.isLetter(c)) {
                char base = Character.isLowerCase(c) ? 'a' : 'A'; // Determines the base character being lowercase or uppercase letters.
                decrypted.append((char) ((c - base - key + 26) % 26 + base)); // Shifts letter by key value.
            } else {
                decrypted.append(c);
            }
        }
        return decrypted.toString();
    }


    /**
     * Displays the decryption frame.
     * This method is called to make the decryption frame visible.
     */
    @Override
    public void display() {
        decryptionFrame.setVisible(true);
    }
}




/**
 * The LayoutUI class represents the main user interface for the LogiThune music player.
 * It provides a layout for displaying albums, songs, and controls for playback.
 * It is the central hub/home screen of the music player.
 * It also handles sorting of albums and provides functionality for playing songs.
 * Allows playback of songs, shuffling, looping and pausing.
 */
class LayoutUI implements DisplayableUI {


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
        playlistsLabel.setForeground(Color.WHITE);
        
        playlistsPanel.add(playlistsLabel);
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

/**
 * Represents a song with a title, duration, and file path.
 * Provides methods that songs can use to play audio, pause, stop, and loop
 */
class Song {

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

/**
 * Represents a rounded border for UI components.
 */
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

/**
 * Represents an album with a title, artist, songs, and album cover.
 */
class Album {

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

/**
 * Represents the UI for displaying an album.
 */
class AlbumFrameUI extends Album implements DisplayableUI {
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

                    // If the user provided a valid name, create an new playlist
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

/**
 * Represents a playlist with a name and a list of songs.
 */
class Playlist {

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

/**
 * Represents a user account with a username and encrypted password.
 */
abstract class Account {

    private String username;
    private String encryptedPassword;

    
    /**
     * Constructor for the Account class.
     *
     * @param username The username of the account.
     * @param encryptedPassword The encrypted password of the account.
     */
    public Account(String username, String encryptedPassword) {
        this.username = username;
        this.encryptedPassword = encryptedPassword;
    }

    /**
     * Gets the encrypted password of the account.
     *
     * @return The encrypted password.
     */
    public String getPassword() {
        return encryptedPassword;
    }

    /**
     * Gets the username of the account.
     *
     * @return The username.
     */
    public String getUsername() {
        return username;
    }
}

/**
 * Represents a user with playlists.
 */
class User extends Account {

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
