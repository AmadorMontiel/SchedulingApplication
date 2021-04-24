package View_Controller;

import DataModel.Country;
import DataModel.FirstLevelDivision;
import Implementations.CountryDaoImpl;
import Implementations.CustomerDaoImpl;
import Implementations.FirstLevelDivisionDaoImpl;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Controller for the Add Customer screen.
 */
public class AddCustomerController {

    public ComboBox<Country> countryComboBox;
    public ComboBox<FirstLevelDivision> firstLevelDivisionComboBox;
    public TextField customerIDTextField;
    public TextField nameTextField;
    public TextField addressTextField;
    public TextField postalCodeTextField;
    public TextField phoneNumberTextField;
    ObservableList<Country> countryObservableList = CountryDaoImpl.getAllCountries();
    private final Alert errorAlert = new Alert(Alert.AlertType.ERROR);

    /**
     * Sets the values for the County combo box.
     * Also sets the value for the Customer ID Field.
     */
    public void initialize() {
        countryComboBox.setItems(countryObservableList);
        customerIDTextField.setText("Auto-Generated");
    }

    /**
     * Method that changes the firstLevelDivisonComboBox based on the selection of the countryComboBox
     */
    public void countrySelection() {
        firstLevelDivisionComboBox.setItems(FirstLevelDivisionDaoImpl.getFirstLevelDivisions(countryComboBox.getSelectionModel().getSelectedItem().getCountryID()));
    }

    /**
     * Saves a new customer using the information provided by the user. Also checks to make sure
     * all fields have information, else presents an error.
     * @param event Clicking the "save" button.
     * @throws IOException Exception thrown by the close method.
     */
    public void saveNewCustomer(MouseEvent event) throws IOException {
    if(nameTextField.getText().isEmpty() || addressTextField.getText().isEmpty() || postalCodeTextField.getText().isEmpty() || phoneNumberTextField.getText().isEmpty() ||
    firstLevelDivisionComboBox.getSelectionModel().isEmpty() || countryComboBox.getSelectionModel().isEmpty()) {
        errorAlert.setTitle("Error");
        errorAlert.setHeaderText("Invalid Input");
        errorAlert.setContentText("All information must be filled out.");
        errorAlert.show();
    } else {
        CustomerDaoImpl.addCustomer(nameTextField.getText(),
                addressTextField.getText(),
                postalCodeTextField.getText(),
                phoneNumberTextField.getText(),
                firstLevelDivisionComboBox.getSelectionModel().getSelectedItem().getDivisionID());
        close(event);
        }
    }

    /**
     * Closes the add customer screen and takes the use back to the main screen.
     * @param event Clicking the "Save" or "cancel" button
     * @throws IOException FXMLLoader can throw the IOException
     */
    public void close(MouseEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage thisStage = (Stage) node.getScene().getWindow();
        thisStage.close();

        Parent root = FXMLLoader.load(getClass().getResource("mainwindow.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(root));

        stage.show();
    }


}
