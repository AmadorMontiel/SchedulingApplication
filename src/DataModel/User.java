package DataModel;

/**
 * Represents a user of the program.
 */
public class User {

    /**
     * User ID that the user uses to log into the system.
     */
    private int userID;

    /**
     * The name associated with the user account.
     */
    private String name;

    /**
     * The user's password.
     */
    private String password;

    /**
     * Constructor for the User class.
     * @param userID the userID of the new user.
     * @param name the name of the new user.
     * @param password the password of the new user.
     */
    public User(int userID, String name, String password) {
        this.userID = userID;
        this.name = name;
        this.password = password;
    }

    /**
     * Gets the user ID.
     * @return the user ID.
     */
    public int getUserID() {
        return userID;
    }

    /**
     * Sets the user ID.
     * @param userID the user ID to set.
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     * Gets the user's name.
     * @return the user's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the user's name.
     * @param name the name to be set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the password of the user.
     * @return the password of the user.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the user.
     * @param password the password to set.
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
