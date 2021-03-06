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
 * This includes sign in verification, error checking, sign on logging, and translation as needed.
 */
public class LoginController {


    public TextField usernameTextField;
    public PasswordField passwordField;
    public Label zoneID;
    public Label signInLabel;
    public Button loginButton;
    public Button exitButton;
    public Label usernameLabel;
    public Label passwordLabel;

    private final Alert loginAlert = new Alert(Alert.AlertType.ERROR);
    private DateTimeFormatter dtf = DateTimeFormatter.RFC_1123_DATE_TIME;
    private ResourceBundle rb;
    private FileWriter fw = new FileWriter("src/login_activity.txt",true);
    private PrintWriter pw = new PrintWriter(fw);
    int loginCount = 1;

    /**
     * Constructor created so the FileWriter and PrintWriter classes could be used.
     * @throws IOException Exception thrown by FileWriter and PrintWriter.
     */
    public LoginController() throws IOException {
    }

    /**
     * Checks the locale and translates if needed. Otherwise, just retrieves the
     * zoneID from the user's computer and displays it.
     */
    public void initialize() {
        if(Locale.getDefault().getLanguage().equals("fr")) {
            rb = ResourceBundle.getBundle("Utility/Nat", Locale.getDefault());
            zoneID.setText(ZoneId.systemDefault().toString());
            translation();
        } else {
            zoneID.setText(ZoneId.systemDefault().toString());
        }
    }

    /**
     * Passes the username and password information provided to the UserDAO to check to see if the information is valid.
     * If the inforamtion is valid, logs into the application and loads the main screen.
     * If the information is invalid, shows an error message.
     * @param event Clicking "Login" triggers the action.
     */
    public void login(MouseEvent event) throws IOException {

       boolean isValidLogin = UserDaoImpl.isValidLogin(usernameTextField.getText(), passwordField.getText());
        logAppicationLogin(isValidLogin);

       if(isValidLogin) {
               FXMLLoader loader = getFxmlLoader();
               MainWindowController MWController = loader.getController();
               MWController.receiveUser(UserDaoImpl.getUserByName(usernameTextField.getText()));
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
                    " - Occured at: " + ZonedDateTime.now(ZoneId.of("UTC")).format(dtf)  + " - Successful.");
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
        signInLabel.setText(rb.getString("Please_sign_in"));
        loginButton.setText(rb.getString("login"));
        exitButton.setText(rb.getString("exit"));
        usernameLabel.setText(rb.getString("username"));
        passwordLabel.setText(rb.getString("password"));

    }

    /**
     * Helper method for sending information from login screen to mainwindow.
     * @return returns the loader to be used for the next screen
     * @throws IOException Exception thrown when dealing with FXMLLoader
     */
    private FXMLLoader getFxmlLoader() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("mainwindow.fxml"));
        loader.load();
        return loader;
    }
}
