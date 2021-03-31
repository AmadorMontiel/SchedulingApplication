package View_Controller;

import DataModel.Country;
import DataModel.Customer;
import DataModel.FirstLevelDivision;
import Implementations.FirstLevelDivisionDaoImpl;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class UpdateCustomerController {

    public TextField customerIDTextField;
    public TextField nameTextFIeld;
    public TextField addressTextField;
    public TextField postalCodeTextField;
    public TextField phoneNumberTextField;
    public ComboBox<Country> countryComboBox;
    public ComboBox<FirstLevelDivision> firstLevelDivisionComboBox;
    public TableView<Customer> customerTableView;
    public TableColumn<Customer, Integer> customerIDColumn;
    public TableColumn<Customer, String> customerNameColumn;
    public TableColumn<Customer, String> customerAddressColumn;
    public TableColumn<Customer, String> customerPostalCodeColumn;
    public TableColumn<Customer, String> customerPhoneNumberColumn;
    public TableColumn<Customer, String> customerFLDColumn;

    private Customer tempCustomer;


    public void receiveCustomer(Customer selectedCustomer) {

        tempCustomer = selectedCustomer;

        customerIDTextField.setText(String.valueOf(selectedCustomer.getId()));
        nameTextFIeld.setText(selectedCustomer.getName());
        addressTextField.setText(selectedCustomer.getAddress());
        postalCodeTextField.setText(selectedCustomer.getPostalCode());
        phoneNumberTextField.setText(selectedCustomer.getPhoneNumber());
        //firstLevelDivisionComboBox.setItems();




    }

    /**
     * Closes the add appointment screen and takes the use back to the main screen.
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
