package View_Controller;

import Implementations.UserDaoImpl;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * The login controller handles the actions taken on the login screen.
 * This includes sign in verification, error checking, sign on logging,
 */
public class LoginController {

    /**
     * The text field where the user enters their username.
     */
    public TextField usernameTextField;
    /**
     * The text field where the user enters their password.
     */
    public PasswordField passwordField;
    /**
     * Zone ID label that shows the user's current location.
     */
    public Label zoneID;
    /**
     * Sign in label
     */
    public Label signInLabel;
    /**
     * Button clicked to log into the program.
     */
    public Button loginButton;
    /**
     * Button clicked to exit the program.
     */
    public Button exitButton;
    /**
     * Username label.
     */
    public Label usernameLabel;
    /**
     * Password label.
     */
    public Label passwordLabel;

    /**
     * Alert that shows when an incorrect username and/or password is entered.
     */
    private final Alert loginAlert = new Alert(Alert.AlertType.ERROR);
    /**
     * DateTimeFormatter that sets the format for the time printed in the logApplicationLogin method.
     */
    private DateTimeFormatter dtf = DateTimeFormatter.RFC_1123_DATE_TIME;
    /**
     * ResourceBundle utilized in the translation method for translation to French.
     */
    private ResourceBundle rb = ResourceBundle.getBundle("Utility/Nat", Locale.forLanguageTag("fr"));
    /**
     * FileWriter that creates the file in the root folder. Allows for data to be appended to the file.
     * Used in the logApplicationLogin method.
     */
    private FileWriter fw = new FileWriter("src/login_activity.txt",true);
    /**
     * PrintWriter that utilizes the FileWriter created above. Used in the logApplicationLogin method.
     */
    private PrintWriter pw = new PrintWriter(fw);
    /**
     * Counts the number of login attempts made until success.
     */
    int loginCount = 1;

    /**
     * Constructor created so the FileWriter and PrintWriter classes could be used.
     * @throws IOException Exception thrown by FileWriter and PrintWriter.
     */
    public LoginController() throws IOException {
    }

    /**
     * Sets any initial information.
     * Retrieves the zoneID from the user's computer and displays it.
     */
    public void initialize() {

        if(Locale.getDefault().getLanguage().equals("fr")) {
            translation();
        } else {
            zoneID.setText(ZoneId.systemDefault().toString());
        }
    }

    /**
     * Passes the username and password information provided to the UserDAO to check to see if the information is valid.
     * If the inforamtion is valid, logs into the applicaiton and loads the main screen.
     * If the information is invalid, shows an error message.
     * @param event Clicking "Login" triggers the action.
     */
    public void login(MouseEvent event) throws IOException {

       boolean isValidLogin = UserDaoImpl.isValidLogin(usernameTextField.getText(), passwordField.getText());
        logAppicationLogin(isValidLogin);

       if(isValidLogin) {
               FXMLLoader loader = new FXMLLoader();
               loader.setLocation(getClass().getResource("mainwindow.fxml"));
               try {
                   loader.load();
               } catch (IOException e) {
                   e.printStackTrace();
               }
               loadNewScene(event, loader);
               fw.close();
               pw.close();
           }
       else {
           if(Locale.getDefault().getLanguage().equals("fr")){
               loginAlert.setTitle(rb.getString("error"));
               loginAlert.setHeaderText(rb.getString("unable_to_login"));
               loginAlert.setContentText(rb.getString("incorrect_username_or_password"));
           } else {
               loginAlert.setTitle("Error");
               loginAlert.setHeaderText("Unable to login");
               loginAlert.setContentText("Incorrect username or password");
           }
           loginAlert.show();
       }
   }

    /**
     * Logs login infomation to the "login_activity" file.
     */
    private void logAppicationLogin(boolean isValidLogin) {
        if(!isValidLogin) {
            pw.println("Login Attempt #" + loginCount + " - User: " + usernameTextField.getText() +
                    " - Occured at: " + ZonedDateTime.now(ZoneId.of("UTC")).format(dtf)  + " - Unsuccessful.");
            loginCount++;
        } else {
            pw.println("Login Attempt #" + loginCount + " - User: " + usernameTextField.getText() +
                    " - Occured at: " + ZonedDateTime.now(ZoneId.of("UTC")).format(dtf)  + " - Successful.");;
        }
    }

    /**
     * Closes the application
     */
    public void exitProgram() {
       Platform.exit();
   }

    /**
     * Helper method to load new scenes
     * @param event event that triggered the method
     * @param loader the loader of the scene being loaded
     */
    private void loadNewScene(MouseEvent event, FXMLLoader loader) {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Translates the login screen language to French.
     */
    private void translation() {
        zoneID.setText(rb.getString(ZoneId.systemDefault().toString()));
        signInLabel.setText(rb.getString("Please_sign_in"));
        loginButton.setText(rb.getString("login"));
        exitButton.setText(rb.getString("exit"));
        usernameLabel.setText(rb.getString("username"));
        passwordLabel.setText(rb.getString("password"));

    }
}
