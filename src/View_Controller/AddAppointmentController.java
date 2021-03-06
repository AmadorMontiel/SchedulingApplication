package View_Controller;

import DataModel.Contact;
import DataModel.Customer;
import DataModel.User;
import Implementations.AppointmentDaoImpl;
import Implementations.ContactDaoImpl;
import Implementations.CustomerDaoImpl;
import Implementations.UserDaoImpl;
import Utility.TimeConversion;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.*;

/**
 * Controller for the Add Appointment screen.
 */
public class AddAppointmentController {

    public TextField appointmentIDTextField;
    public TextField titleTextField;
    public TextField descriptionTextField;
    public TextField locationTextField;
    public TextField typeTextField;

    public LocalTime startTime = LocalTime.MIDNIGHT;
    public LocalTime endTime = LocalTime.of(23,44);
    public LocalDateTime startDateAndTime;
    public LocalDateTime endDateAndTime;

    public DatePicker startDatePicker;
    public DatePicker endDatePicker;

    public ComboBox<Contact> contactComboBox;
    public ComboBox<Customer> customerComboBox;
    public ComboBox<User> userComboBox;
    public ComboBox<LocalTime> startTimeComboBox;
    public ComboBox<LocalTime> endTimeComboBox;

    public Alert errorAlert = new Alert(Alert.AlertType.ERROR);

    /**
     * Loads and displays the combo box info for Contacts, Customers, and Users.
     * Also fills in values of the startTime and endTime combo boxes for selection.
     */
    public void initialize() {

        contactComboBox.setItems(ContactDaoImpl.getAllContacts());
        customerComboBox.setItems(CustomerDaoImpl.getAllCustomers());
        userComboBox.setItems(UserDaoImpl.getAllUsers());
        appointmentIDTextField.setText("Auto-Generated");

        while (startTime.isBefore(endTime.plusSeconds(1))) {
            startTimeComboBox.getItems().add(startTime);
            endTimeComboBox.getItems().add(startTime);
            startTime = startTime.plusMinutes(15);
        }
        startTimeComboBox.getItems().add(LocalTime.of(23,45));
        endTimeComboBox.getItems().add(LocalTime.of(23,45));
    }

    /**
     * Attmepts to save a new appointment using the information entered/selected by the user.
     * Checks to make sure that information has been entered for all fields and displays an error if not.
     * Also calls isAllowableTime to check to make sure the date/time selected is valid.
     * @param event Clicking the "save" button activates the method.
     * @throws IOException Exception thrown by the close method.
     */
    public void saveNewAppointment(MouseEvent event) throws IOException {
        if (startDatePicker.getValue() == null || endDatePicker.getValue() == null || titleTextField.getText().isEmpty() ||
            descriptionTextField.getText().isEmpty() || locationTextField.getText().isEmpty() || typeTextField.getText().isEmpty() ||
            customerComboBox.getSelectionModel().getSelectedItem() == null || userComboBox.getSelectionModel().getSelectedItem() == null ||
            contactComboBox.getSelectionModel().getSelectedItem() == null) {

            errorAlert.setTitle("Error");
            errorAlert.setHeaderText("Invalid Input");
            errorAlert.setContentText("All information must be filled out.");
            errorAlert.show();

        } else {
            startDateAndTime = LocalDateTime.of(startDatePicker.getValue(), startTimeComboBox.getValue());
            endDateAndTime = LocalDateTime.of(endDatePicker.getValue(), endTimeComboBox.getValue());

            if(isAllowableTime(startDateAndTime, endDateAndTime)) {

                startDateAndTime = TimeConversion.UTCConversion(startDateAndTime);
                endDateAndTime = TimeConversion.UTCConversion(endDateAndTime);

                AppointmentDaoImpl.addAppointment(titleTextField.getText(), descriptionTextField.getText(),
                        locationTextField.getText(), typeTextField.getText(), startDateAndTime, endDateAndTime,
                        customerComboBox.getSelectionModel().getSelectedItem().getId(), userComboBox.getSelectionModel().getSelectedItem().getUserID(),
                        contactComboBox.getSelectionModel().getSelectedItem().getContactID());
                close(event);
            }
        }
    }

    /**
     * Checks to see if the appointment overlaps with any other appointments.
     * Determines if the date/time entered of the new appointment is valid.
     * This is done by converting the times to EST and comparing the times to the open
     * and close times of the business.
     * @param start The start date/time of the new appointment.
     * @param end The end date/time of the new appointment.
     * @return Returns true is the date/time is valid, otherwise, returns false.
     */
    private boolean isAllowableTime(LocalDateTime start, LocalDateTime end) {

        LocalTime businessOpenTime = LocalTime.of(8,0);
        LocalTime businessCloseTime = LocalTime.of(22,0);

        ZoneId localTimeZone = ZoneId.of(String.valueOf(ZoneId.systemDefault()));
        ZoneId easternTimeZone = ZoneId.of("America/New_York");

        ZonedDateTime currentStartTime = start.atZone(localTimeZone);
        ZonedDateTime currentEndTime = end.atZone(localTimeZone);

        ZonedDateTime ESTStartTime = currentStartTime.withZoneSameInstant(easternTimeZone);
        LocalTime estStarting = ESTStartTime.toLocalTime();
        LocalDate estStartDate = ESTStartTime.toLocalDate();
        ZonedDateTime ESTEndTime = currentEndTime.withZoneSameInstant(easternTimeZone);
        LocalTime estEnding = ESTEndTime.toLocalTime();
        LocalDate estEndDate = ESTEndTime.toLocalDate();

        if ((estStarting.isAfter(businessOpenTime.minusSeconds(1)) && estEnding.isBefore(businessCloseTime.plusSeconds(1))) && estStartDate.isEqual(estEndDate)) {
            if(AppointmentDaoImpl.isOverlappingAppointment(start,end, customerComboBox.getSelectionModel().getSelectedItem().getId()))
            {
                errorAlert.setTitle("Error");
                errorAlert.setHeaderText("Invalid Appointment Time");
                errorAlert.setContentText("There is an overlapping appointment.");
                errorAlert.show();
                return false;
            }
            else {
                return true;
            }
        } else {
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText("Invalid Appointment Time");
            errorAlert.setContentText("Appointment falls outside of business hours.");
            errorAlert.show();
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
