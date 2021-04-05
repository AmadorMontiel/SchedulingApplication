package View_Controller;

import DataModel.Appointment;
import DataModel.Contact;
import DataModel.Customer;
import DataModel.User;
import Implementations.AppointmentDaoImpl;
import Implementations.ContactDaoImpl;
import Implementations.CustomerDaoImpl;
import Implementations.UserDaoImpl;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class AddAppointmentController {

    public TextField appointmentIDTextField;
    public TextField titleTextField;
    public TextField descriptionTextField;
    public TextField locationTextField;

    public ComboBox<Contact> contactComboBox;
    public ComboBox<Appointment> typeComboBox;

    public DatePicker startDateTimePicker;
    public DatePicker endDateTimePicker;

    public ComboBox<Customer> customerComboBox;
    public ComboBox<User> userComboBox;

    private ObservableList<Contact> contactObservableList = ContactDaoImpl.getAllContacts();
    private ObservableList<Appointment> appointmentTypeObservableList = AppointmentDaoImpl.getAllAppointments();
    private ObservableList<Customer> customerObservableList = CustomerDaoImpl.getAllCustomers();
    private ObservableList<User> userObservableList = UserDaoImpl.getAllUsers();

    public void initialize() {

        contactComboBox.setItems(contactObservableList);
        typeComboBox.setItems(appointmentTypeObservableList);
        customerComboBox.setItems(customerObservableList);
        userComboBox.setItems(userObservableList);

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
