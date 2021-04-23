package View_Controller;

import DataModel.Appointment;
import DataModel.Contact;
import DataModel.Customer;
import Implementations.AppointmentDaoImpl;
import Implementations.ContactDaoImpl;
import Implementations.CustomerDaoImpl;
import Interfaces.LambdaReportDisplay;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;

public class ReportsController {

    public TableView<Appointment> appointmentByMonthAndTypeTable;
    public TableColumn<Appointment, Integer> countColumn;
    public TableColumn<Appointment, String> typeColumn;
    public TableColumn<Appointment, String> monthColumn;

    public TableView<Appointment> appointmentByContactTable;
    public TableColumn<Appointment, Integer> abcAppointmentIDColumn;
    public TableColumn<Appointment, String> abcTitleColumn;
    public TableColumn<Appointment, String> abcTypeColumn;
    public TableColumn<Appointment, String> abcDescriptionColumn;
    public TableColumn<Appointment, LocalDateTime> abcStartColumn;
    public TableColumn<Appointment, LocalDateTime>abcEndColumn;
    public TableColumn<Appointment, Integer> abcCustomerColumn;


    public TableView<Appointment> customerTable;
    public TableColumn<Appointment, Integer> customerAppointmentIDColumn;
    public TableColumn<Appointment, String> customerTitleColumn;
    public TableColumn<Appointment, String> customerTypeColumn;
    public TableColumn<Appointment, String> customerDescriptionColumn;
    public TableColumn<Appointment, LocalDateTime> customerStartColumn;
    public TableColumn<Appointment, LocalDateTime> customerEndColumn;
    public TableColumn<Appointment, Integer> customerContactIDColumn;
    public TableColumn<Appointment, Integer> customerUserIDColumn;

    public ComboBox<Contact> contactComboBox;
    public ComboBox<Customer> customerComboBox;
    private ObservableList<Appointment> appointmentByMonthList;


    /**
     * Contains the second Lambda Expression
     */
    public void initialize() {
        countColumn.setCellValueFactory(new PropertyValueFactory<>("count"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        monthColumn.setCellValueFactory(new PropertyValueFactory<>("month"));

        LambdaReportDisplay lrd = (ObservableList<Appointment> appointments) -> {
            appointments = AppointmentDaoImpl.getAppointmentsByTypeAndMonth();
            return appointments;
        };

        appointmentByMonthAndTypeTable.setItems(lrd.observableListAssignment(appointmentByMonthList));

        abcAppointmentIDColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        abcTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        abcTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        abcDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        abcStartColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
        abcEndColumn.setCellValueFactory(new PropertyValueFactory<>("end"));
        abcCustomerColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));

        customerAppointmentIDColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        customerTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        customerTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        customerDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        customerStartColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
        customerEndColumn.setCellValueFactory(new PropertyValueFactory<>("end"));
        customerContactIDColumn.setCellValueFactory(new PropertyValueFactory<>("contactID"));
        customerUserIDColumn.setCellValueFactory(new PropertyValueFactory<>("userID"));

        contactComboBox.setItems(ContactDaoImpl.getAllContacts());
        customerComboBox.setItems(CustomerDaoImpl.getAllCustomers());

    }

    public void contactSelection() {
        appointmentByContactTable.setItems(AppointmentDaoImpl.getAppointmentsByContact(contactComboBox.getSelectionModel().getSelectedItem().getContactID()));
    }

    public void customerSelection() {
        customerTable.setItems(AppointmentDaoImpl.getAppointmentsByCustomer(customerComboBox.getSelectionModel().getSelectedItem().getId()));
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
