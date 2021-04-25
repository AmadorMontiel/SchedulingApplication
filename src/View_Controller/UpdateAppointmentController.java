package View_Controller;

import DataModel.Appointment;
import DataModel.Contact;
import DataModel.Customer;
import DataModel.User;
import Implementations.AppointmentDaoImpl;
import Implementations.ContactDaoImpl;
import Implementations.CustomerDaoImpl;
import Implementations.UserDaoImpl;
import Interfaces.LambdaAlert;
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
 * Controller for the update appointment screen.
 */
public class UpdateAppointmentController {
    public TextField appointmentIDTextField;
    public TextField titleTextField;
    public TextField descriptionTextField;
    public TextField locationTextField;
    public TextField typeTextField;

    public DatePicker startDatePicker;
    public DatePicker endDatePicker;

    public LocalTime startTime = LocalTime.MIDNIGHT;
    public LocalTime endTime = LocalTime.of(23,44);
    public LocalDateTime startDateAndTime;
    public LocalDateTime endDateAndTime;

    public ComboBox<Contact> contactComboBox;
    public ComboBox<Customer> customerComboBox;
    public ComboBox<User> userComboBox;
    public ComboBox<LocalTime> startTimeComboBox;
    public ComboBox<LocalTime> endTimeComboBox;

    public Alert errorAlert = new Alert(Alert.AlertType.ERROR);

    private Contact contact;
    private Customer customer;
    private User user;

    /**
     * Loads and displays the combo box info for Contacts, Customers, and Users.
     * Also fills in values of the startTime and endTime combo boxes for selection.
     */
    public void initialize() {
        contactComboBox.setItems(ContactDaoImpl.getAllContacts());
        customerComboBox.setItems(CustomerDaoImpl.getAllCustomers());
        userComboBox.setItems(UserDaoImpl.getAllUsers());

        while (startTime.isBefore(endTime.plusSeconds(1))) {
            startTimeComboBox.getItems().add(startTime);
            endTimeComboBox.getItems().add(startTime);
            startTime = startTime.plusMinutes(15);
        }
        startTimeComboBox.getItems().add(LocalTime.of(23,45));
        endTimeComboBox.getItems().add(LocalTime.of(23,45));
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

    /**
     * Receives the appointment that was selected on the main screen and sets the form information
     * based on the selected appointment.
     * @param selectedAppointment The appointment selected on the main screen.
     */
    public void receiveAppointment(Appointment selectedAppointment) {

        appointmentIDTextField.setText(String.valueOf((selectedAppointment.getAppointmentID())));
        titleTextField.setText(selectedAppointment.getTitle());
        descriptionTextField.setText(selectedAppointment.getDescription());
        locationTextField.setText(selectedAppointment.getLocation());
        typeTextField.setText(selectedAppointment.getType());
        startDatePicker.setValue(selectedAppointment.getStart().toLocalDate());
        endDatePicker.setValue(selectedAppointment.getEnd().toLocalDate());
        startTimeComboBox.setValue(selectedAppointment.getStart().toLocalTime());
        endTimeComboBox.setValue(selectedAppointment.getEnd().toLocalTime());

        getComboBoxItems(selectedAppointment);
        contactComboBox.setValue(contact);
        customerComboBox.setValue(customer);
        userComboBox.setValue(user);


    }

    /**
     * Attempts to save a new appointment using the information entered/selected by the user.
     * Checks to make sure that information has been entered for all fields and displays an error if not.
     * The error that is displayed utilizes a lambda expression.
     * This lambda simplifies the code and reduces the amount of code needed if the alert needed to be used again.
     * Calls isAllowableTime to make checks prior to saving the appointment.
     * @param event Clicking the "Save" button.
     * @throws IOException IOException thrown by close method.
     */
    public void saveUpdatedAppointment(MouseEvent event) throws IOException {
        if (startDatePicker.getValue() == null || endDatePicker.getValue() == null || titleTextField.getText().isEmpty() ||
                descriptionTextField.getText().isEmpty() || locationTextField.getText().isEmpty() || typeTextField.getText().isEmpty() ||
                customerComboBox.getSelectionModel().getSelectedItem() == null || userComboBox.getSelectionModel().getSelectedItem() == null ||
                contactComboBox.getSelectionModel().getSelectedItem() == null) {

            LambdaAlert alert = (Alert alertError) -> {
                errorAlert.setTitle("Error");
                errorAlert.setHeaderText("Invalid Input");
                errorAlert.setContentText("All information must be filled out.");
                errorAlert.show();
            };
            alert.invalidInputError(errorAlert);

        } else {
            startDateAndTime = LocalDateTime.of(startDatePicker.getValue(), startTimeComboBox.getValue());
            endDateAndTime = LocalDateTime.of(endDatePicker.getValue(), endTimeComboBox.getValue());

            if(isAllowableTime(startDateAndTime, endDateAndTime)) {

                startDateAndTime = TimeConversion.UTCConversion(startDateAndTime);
                endDateAndTime = TimeConversion.UTCConversion(endDateAndTime);

                AppointmentDaoImpl.modifyAppointment(Integer.parseInt(appointmentIDTextField.getText()), titleTextField.getText(), descriptionTextField.getText(),
                        locationTextField.getText(), typeTextField.getText(), startDateAndTime, endDateAndTime,
                        customerComboBox.getSelectionModel().getSelectedItem().getId(), userComboBox.getSelectionModel().getSelectedItem().getUserID(),
                        contactComboBox.getSelectionModel().getSelectedItem().getContactID());
                close(event);
            }
        }
    }

    /**
     * Uses the selected appointment from the main screen to extract the Contact, Customer and User info.
     * @param selectedAppointment The appointment selected on the main screen.
     */
    private void getComboBoxItems (Appointment selectedAppointment) {
        contact = ContactDaoImpl.getContactByID(selectedAppointment.getContactID());
        customer = CustomerDaoImpl.getCustomerByID(selectedAppointment.getCustomerID());
        user = UserDaoImpl.getUserByID(selectedAppointment.getUserID());
    }

    /**
     * Checks to see if the appointment overlaps with any other appointments.
     * Determines if the date/time entered of the updated appointment is valid.
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

        if (estStarting.isAfter(businessOpenTime.minusSeconds(1)) && estEnding.isBefore(businessCloseTime.plusSeconds(1)) && (estStartDate.isEqual(estEndDate) || (estStartDate.isBefore(estEndDate)))) {
            if(AppointmentDaoImpl.isOverlappingAppointment(start,end, customerComboBox.getSelectionModel().getSelectedItem().getId(), Integer.parseInt(appointmentIDTextField.getText())))
            {
                errorAlert.setTitle("Error");
                errorAlert.setHeaderText("Invalid Input");
                errorAlert.setContentText("There is an overlapping appointment.");
                errorAlert.show();
                return false;
            }
            else {
                return true;
            }
        } else {
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText("Invalid Time");
            errorAlert.setContentText("Time falls outside of business hours.");
            errorAlert.show();
            return false;
        }
    }

}
