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

    public void initialize() {

        contactComboBox.setItems(ContactDaoImpl.getAllContacts());
        customerComboBox.setItems(CustomerDaoImpl.getAllCustomers());
        userComboBox.setItems(UserDaoImpl.getAllUsers());
        appointmentIDTextField.setText(String.valueOf(AppointmentDaoImpl.currentAppointmentID));

        while (startTime.isBefore(endTime.plusSeconds(1))) {
            startTimeComboBox.getItems().add(startTime);
            endTimeComboBox.getItems().add(startTime);
            startTime = startTime.plusMinutes(15);
        }
        startTimeComboBox.getItems().add(LocalTime.of(23,45));
        endTimeComboBox.getItems().add(LocalTime.of(23,45));
    }

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
            System.out.println("The Time works");
            if(AppointmentDaoImpl.isOverlappingAppointment(start,end, customerComboBox.getSelectionModel().getSelectedItem().getId(), Integer.parseInt(appointmentIDTextField.getText())))
            {
                System.out.println("There is an overlapping appointment.");
                return false;
            }
            else {
                System.out.println("No overlapping appointments.");
                return true;
            }
        } else {
            System.out.println(estStartDate);
            System.out.println(estEndDate);
            System.out.println("Time does not work.");
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
