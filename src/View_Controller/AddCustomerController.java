package View_Controller;

import DataModel.Country;
import DataModel.FirstLevelDivision;
import Implementations.CountryDaoImpl;
import Implementations.CustomerDaoImpl;
import Implementations.FirstLevelDivisionDaoImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class AddCustomerController {

    public ComboBox<Country> countryComboBox;
    public ComboBox<FirstLevelDivision> firstLevelDivisionComboBox;
    public TextField customerIDTextField;
    public TextField nameTextField;
    public TextField addressTextField;
    public TextField postalCodeTextField;
    public TextField phoneNumberTextField;
    ObservableList<Country> countryObservableList = CountryDaoImpl.getAllCountries();

    public void initialize() {
        countryComboBox.setItems(countryObservableList);
        customerIDTextField.setText(String.valueOf(CustomerDaoImpl.currentCustomerID));

    }

    public void countrySelection() {
        firstLevelDivisionComboBox.setItems(FirstLevelDivisionDaoImpl.getFirstLevelDivisions(countryComboBox.getSelectionModel().getSelectedItem().getCountryID()));
    }

    public void saveNewCustomer(MouseEvent event) throws IOException {

        CustomerDaoImpl.addCustomer(nameTextField.getText(),
                addressTextField.getText(),
                postalCodeTextField.getText(),
                phoneNumberTextField.getText(),
                firstLevelDivisionComboBox.getSelectionModel().getSelectedItem().getDivisionID());
        close(event);
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
