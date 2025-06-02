import javax.swing.*;
import java.awt.*;
import javax.sound.sampled.*;
import java.io.*;

public class swing {
    public static void main(String[] args) {
                new Login(); 
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

class Layout {
    private JFrame layoutFrame;

    public Layout() {
        layoutFrame = new JFrame("LogiThune");
        layoutFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        layoutFrame.setSize(1000, 700);
        layoutFrame.setLocationRelativeTo(null);
        layoutFrame.setLayout(new BorderLayout());


        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.setBackground(Color.LIGHT_GRAY);
        topPanel.setPreferredSize(new Dimension(1000, 50));


        JPanel searchHomePanel = new JPanel();
        searchHomePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        searchHomePanel.setBackground(Color.LIGHT_GRAY);

        ImageIcon homeIcon = new ImageIcon("homeIcon.jpg");
        Image scaledImage = homeIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        JButton homeButton = new JButton(new ImageIcon(scaledImage));
        homeButton.setPreferredSize(new Dimension(30, 30));

        JTextField searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(300, 30));
        searchHomePanel.add(homeButton);
        searchHomePanel.add(searchField);

        JPanel accountMenuPanel = new JPanel();
        accountMenuPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        accountMenuPanel.setBackground(Color.LIGHT_GRAY);
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(Color.LIGHT_GRAY);
        JMenu menu = new JMenu();
        menu.setIcon(new ImageIcon("userIcon.jpg")); 
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

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.setBackground(Color.LIGHT_GRAY);
        bottomPanel.setPreferredSize(new Dimension(1000, 70));

        JProgressBar musicBar = new JProgressBar(0, 100);
        bottomPanel.add(musicBar);
        musicBar.setPreferredSize(new Dimension(300, 20)); 

        // Load the audio file
        try {
            File audioFile = new File("PARTYNEXTDOOR-CN-TOWER-Ft-DRAKE-(HipHopKit.com).wav.wav"); // Replace with your audio file
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);

            // Start the playback and update the progress bar
            clip.start();
            while (clip.isRunning()) {
                int progress = (int) (100 * clip.getMicrosecondPosition() / clip.getMicrosecondLength());
                musicBar.setValue(progress);
                try {
                    Thread.sleep(100); // Update the progress bar every 100ms
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
            e.printStackTrace();
        }


        topPanel.add(searchHomePanel, BorderLayout.CENTER);
        topPanel.add(accountMenuPanel, BorderLayout.EAST);

        layoutFrame.add(topPanel, BorderLayout.NORTH);
        layoutFrame.add(bottomPanel, BorderLayout.SOUTH);

        layoutFrame.setVisible(true);
    }
}

