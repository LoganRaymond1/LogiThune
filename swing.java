import javax.swing.*;
import java.util.*;

public class swing {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Login login = new Login(); 
            }
        });
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
                Decryption decryption = new Decryption();
                return;
            }
        }
        JOptionPane.showMessageDialog(loginFrame, "Invalid username or password.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}

class Decryption extends Login{
    private JFrame decryptionFrame;
    private String encryptedWords[] = {"fcw"};
    private String randomEncryptWord;
    private int random = (int) (Math.random() * encryptedWords.length);
    private int key = 3; 

    public Decryption() {
        randomEncryptWord = encryptedWords[random];

        decryptionFrame = new JFrame("Decryption Challenge");
        decryptionFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        decryptionFrame.setSize(400, 250);
        decryptionFrame.setLocationRelativeTo(null);
        decryptionFrame.setLayout(null);

        JLabel textLabel = new JLabel("Encrypted Word: " + randomEncryptWord);
        textLabel.setBounds(50, 50, 300, 30);
        decryptionFrame.add(textLabel);

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
            } else {
                JOptionPane.showMessageDialog(decryptionFrame, "Incorrect! Try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
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

