package View_Controller;

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
import java.time.*;

public class AddAppointmentController {

    public TextField appointmentIDTextField;
    public TextField titleTextField;
    public TextField descriptionTextField;
    public TextField locationTextField;
    public TextField typeTextField;

    public LocalDate startDate;
    public LocalDate endDate;
    public LocalTime startTime = LocalTime.MIDNIGHT;
    public LocalTime endTime = LocalTime.of(23,44);
    public LocalDateTime startDateAndTime;
    public LocalDateTime endDateAndTime;

    public DatePicker startDateTimePicker;
    public DatePicker endDateTimePicker;

    public ComboBox<Contact> contactComboBox;
    public ComboBox<Customer> customerComboBox;
    public ComboBox<User> userComboBox;
    public ComboBox<LocalTime> startTimeComboBox;
    public ComboBox<LocalTime> endTimeComboBox;


    private ObservableList<Contact> contactObservableList = ContactDaoImpl.getAllContacts();
    private ObservableList<Customer> customerObservableList = CustomerDaoImpl.getAllCustomers();
    private ObservableList<User> userObservableList = UserDaoImpl.getAllUsers();

    public void initialize() {

        contactComboBox.setItems(contactObservableList);
        customerComboBox.setItems(customerObservableList);
        userComboBox.setItems(userObservableList);
        appointmentIDTextField.setText(String.valueOf(AppointmentDaoImpl.currentAppointmentID));

        while (startTime.isBefore(endTime.plusSeconds(1))) {
            startTimeComboBox.getItems().add(startTime);
            endTimeComboBox.getItems().add(startTime);
            startTime = startTime.plusMinutes(15);
        }
        startTimeComboBox.getItems().add(LocalTime.of(23,45));
        endTimeComboBox.getItems().add(LocalTime.of(23,45));
    }

    public void saveNewAppointment() {

        startDateAndTime = LocalDateTime.of(startDateTimePicker.getValue(), startTimeComboBox.getValue());
        endDateAndTime = LocalDateTime.of(endDateTimePicker.getValue(), endTimeComboBox.getValue());

        if(isAllowableTime(startDateAndTime, endDateAndTime)) {
            AppointmentDaoImpl.addAppointment(titleTextField.getText(), descriptionTextField.getText(),
                    locationTextField.getText(), typeTextField.getText(), startDateAndTime, endDateAndTime,
                    customerComboBox.getSelectionModel().getSelectedItem().getId(), userComboBox.getSelectionModel().getSelectedItem().getUserID(),
                    contactComboBox.getSelectionModel().getSelectedItem().getContactID());
        }

    }

    //TODO Add in check to see if appointment times overlap
    public boolean isAllowableTime(LocalDateTime start, LocalDateTime end) {

        LocalTime businessOpenTime = LocalTime.of(8,0);
        LocalTime businessCloseTime = LocalTime.of(22,0);

        ZoneId localTimeZone = ZoneId.of(String.valueOf(ZoneId.systemDefault()));
        ZoneId easternTimeZone = ZoneId.of("America/New_York");

        ZonedDateTime currentStartTime = start.atZone(localTimeZone);
        ZonedDateTime currentEndTime = end.atZone(localTimeZone);

        ZonedDateTime ESTStartTime = currentStartTime.withZoneSameInstant(easternTimeZone);
        LocalTime estStarting = ESTStartTime.toLocalTime();
        ZonedDateTime ESTEndTime = currentEndTime.withZoneSameInstant(easternTimeZone);
        LocalTime estEnding = ESTEndTime.toLocalTime();

        if (estStarting.isAfter(businessOpenTime.minusSeconds(1)) && estEnding.isBefore(businessCloseTime.plusSeconds(1))) {
            System.out.println("This should return true.");
            return !AppointmentDaoImpl.isOverlappingAppointment(start, end, customerComboBox.getSelectionModel().getSelectedItem().getId());
        } else {
            System.out.println("this should return false;");
            return false;
        }
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
