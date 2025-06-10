import javax.swing.*;

/**
 * The LoginUI class represents the login interface for LogiThune music player.
 * It provides users with a frame to enter their username and password.
 * With a successful login (with provided logins), it transitions to the DecryptionUI for further security.
 */
public class LoginUI implements DisplayableUI {
    
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