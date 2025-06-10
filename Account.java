/**
 * Represents a user account with a username and encrypted password.
 */
public abstract class Account {

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