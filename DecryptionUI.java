import javax.swing.*;
import java.awt.*;

/**
 * The DecryptionUI class represents the decryption interface for extra security.
 * Users must enter the decrypted letter sequence based on a randomly selected encrypted sequence and key.
 */
public class DecryptionUI implements DisplayableUI {

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

        // User submits, and it checks if the user input matches the decrypted word
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