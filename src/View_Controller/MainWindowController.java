package View_Controller;

import DataModel.Appointment;
import DataModel.Customer;
import DataModel.User;
import Implementations.AppointmentDaoImpl;
import Implementations.CustomerDaoImpl;
import Utility.TimeConversion;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The controller for the main window screen.
 */
public class MainWindowController {

    public Alert errorAlert = new Alert(Alert.AlertType.ERROR);
    public Alert deletionConfirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
    public Alert deletionConfirmedAlert = new Alert(Alert.AlertType.INFORMATION);
    public Alert appointmentAlert = new Alert(Alert.AlertType.INFORMATION);

    public User loggedInUser;
    public ObservableList<Appointment> associatedAppointmentsWithUser;

    public ComboBox<Appointment> appointmentsComboBox;
    public ComboBox<Customer> customersComboBox;
    public Stage stage;

    /**
     * Sets the values for the Customer and Appointment combo boxes.
     */
    public void initialize() {
        customersComboBox.setItems(CustomerDaoImpl.getAllCustomers());
        appointmentsComboBox.setItems(AppointmentDaoImpl.getAllAppointments());
    }

    /**
     * Closes the application
     */
    public void exitProgram() {
        Platform.exit();
    }

    /**
     * Helper method to load new scenes.
     * @param event Clicking the appropriate button to load a new scene.
     * @param s The string that captures the fxml file to be loaded.
     */
    private void sceneLoader(MouseEvent event, String s) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(s));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Helper method for getting the FXMLLoader.
     * @param s The String representing the FXML file.
     * @return Returns the loader.
     * @throws IOException Throws IOException on .load
     */
    private FXMLLoader getFxmlLoader(String s) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(s));
        loader.load();
        return loader;
    }

    /**
     * Used by the update customer and update appointment methods.
     * Used because of the need to send inforomation to those screens.
     * @param event Clicking the appropriate button for the scene
     * @param loader Loader of the scene needed to load.
     */
    private void loadNewScene(MouseEvent event, FXMLLoader loader) {
        Node node = (Node) event.getSource();
        stage = (Stage) node.getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Opens the add customer screen.
     * @param event Clicking the "Add" button under customers.
     */
    public void addCustomerClicked(MouseEvent event) {
        sceneLoader(event, "addcustomer.fxml");
    }

    /**
     * Opens the update customer screen. Also sends the customer information that
     * is selected to the update customer screen.
     * Shows an error if button is clicked and no customer has been selected.
     * @param event Clicking "Update" under customers.
     * @throws IOException IOException thrown by loadNewScene method.
     */
    public void updateCustomerClicked(MouseEvent event) throws IOException {
        FXMLLoader loader = getFxmlLoader("updatecustomer.fxml");

        UpdateCustomerController uCController = loader.getController();
        if(customersComboBox.getSelectionModel().getSelectedItem() != null) {
            uCController.receiveCustomer(customersComboBox.getSelectionModel().getSelectedItem());
            loadNewScene(event, loader);
        } else {
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText("No Customer Selected");
            errorAlert.setContentText("Please select a customer to update.");
            errorAlert.show();
        }
    }

    /**
     * Deletes the selected customer. Prior is deletion, checks to see if there are any
     * associated appointments with that customer. If an associated appointment exists,
     * shows an error saying the appointment must be deleted first.
     * Also confirms with the user that they want the customer deleted.
     * Will also show an error if button is clicked and no customer has been selected.
     */
    public void deleteCustomerClicked() {
        if (customersComboBox.getSelectionModel().getSelectedItem() != null) {
            if(AppointmentDaoImpl.getAssociatedAppointments(customersComboBox.getSelectionModel().getSelectedItem().getId()) == 0) {
                deletionConfirmationAlert.setTitle("Confirmation");
                deletionConfirmationAlert.setHeaderText("Confirm Customer Deletion");
                deletionConfirmationAlert.setContentText("Are you sure you want to delete Customer: "
                        + customersComboBox.getSelectionModel().getSelectedItem().getName());
                deletionConfirmationAlert.showAndWait();

                if (deletionConfirmationAlert.getResult() == ButtonType.OK) {
                    try {
                        CustomerDaoImpl.deleteCustomer(customersComboBox.getSelectionModel().getSelectedItem().getId());
                        deletionConfirmedAlert.setTitle("Deletion Confirmed");
                        deletionConfirmedAlert.setHeaderText("Customer Deleted");
                        deletionConfirmedAlert.setContentText(customersComboBox.getSelectionModel().getSelectedItem().toString() + " has been deleted!");
                        deletionConfirmedAlert.show();
                        customersComboBox.getItems().remove(customersComboBox.getSelectionModel().getSelectedItem());

                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                errorAlert.setTitle("Error");
                errorAlert.setHeaderText("Unable To Delete Customer");
                errorAlert.setContentText("Delete associated appointments first.");
                errorAlert.show();
            }
        } else {
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText("No Customer Selected");
            errorAlert.setContentText("Please select a customer to delete");
            errorAlert.show();
        }
    }

    /**
     * Opens the add appointment screen.
     * @param event Clicking "add" under appointments.
     */
    public void addAppointmentClicked(MouseEvent event) {
        sceneLoader(event, "addappointment.fxml");
    }

    /**
     * Opens the update appointment screen. Also send the appointment information
     * that is selected to the update appointment screen.
     * Shows an error if the button is clicked and no appointment has been selected.
     * @param event Clicking "Update" under appointments.
     * @throws IOException IOException thrown by loadNewScene method.
     */
    public void updateAppointmentClicked(MouseEvent event) throws IOException {
        FXMLLoader loader = getFxmlLoader("updateappointment.fxml");

        UpdateAppointmentController uAController = loader.getController();
        if(appointmentsComboBox.getSelectionModel().getSelectedItem() != null) {
            uAController.receiveAppointment(appointmentsComboBox.getSelectionModel().getSelectedItem());
            loadNewScene(event, loader);
        } else {
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText("No Appointment Selected");
            errorAlert.setContentText("Please select an appointment to update.");
            errorAlert.show();
        }
    }

    /**
     * Deletes the selected appointment. Prior to deletion, confirms with the user
     * they want to delete the appointment.
     * Shows an error if the button is clicked and no appointment has been selected.
     */
    public void deleteAppointmentClicked() {
        if(appointmentsComboBox.getSelectionModel().getSelectedItem() != null) {
            deletionConfirmationAlert.setTitle("Confirmation");
            deletionConfirmationAlert.setHeaderText("Confirm Appointment Deletion");
            deletionConfirmationAlert.setContentText("Are you sure you want to delete Appointment: "
                    + appointmentsComboBox.getSelectionModel().getSelectedItem().getTitle());
            deletionConfirmationAlert.showAndWait();

            if (deletionConfirmationAlert.getResult() == ButtonType.OK) {
                try {
                    AppointmentDaoImpl.deleteAppointment(appointmentsComboBox.getSelectionModel().getSelectedItem().getAppointmentID());
                    deletionConfirmedAlert.setTitle("Deletion Confirmed");
                    deletionConfirmedAlert.setHeaderText("Appointment Deleted");
                    deletionConfirmedAlert.setContentText(appointmentsComboBox.getSelectionModel().getSelectedItem().toString() + " has been deleted!");
                    deletionConfirmedAlert.show();
                    appointmentsComboBox.getItems().remove(appointmentsComboBox.getSelectionModel().getSelectedItem());

                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        } else {
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText("No Appointment Selected");
            errorAlert.setContentText("Please select an appointment to delete");
            errorAlert.show();
        }
    }

    /**
     * Loads the view appointment screen.
     * @param event Clicking the "View" button.
     */
    public void viewAppointmentsClicked(MouseEvent event) {
        sceneLoader(event, "viewappointment.fxml");
    }

    /**
     * Loads the reports screen.
     * @param event Clicking the "Reports" button.
     */
    public void reportsClicked(MouseEvent event) {
        sceneLoader(event, "reports.fxml");
    }

    /**
     * Method that receieves the user information from the login screen.
     * User info is then passed to the AppointmentDAO file to see if there
     * is any appointments coming up in the next 15 minutes for the user.
     * If an appointment is starting within the next 15 minutes, an alert shows
     * as soon as the user logs in saying the ID and time of the appointment.
     * If there is on appointment starting within 15 minutes of logging in, an alert
     * shows indicating such.
     * @param userLoggedIn User logged into the system.
     */
    public void receiveUser(User userLoggedIn) {
        loggedInUser = userLoggedIn;
        associatedAppointmentsWithUser = AppointmentDaoImpl.getAppointmentsByUser(loggedInUser.getUserID());
        for (Appointment appointment : associatedAppointmentsWithUser) {
            if(AppointmentDaoImpl.isAppointmentWithin15Minutes(appointment.getAppointmentID())) {
                appointmentAlert.setTitle("Alert");
                appointmentAlert.setHeaderText("Appointment Coming Up!");
                appointmentAlert.setContentText("Appointment ID: " + appointment.getAppointmentID()
                        + " Date/Time: " + TimeConversion.localTimeConversion(appointment.getStart()));
                appointmentAlert.show();
                break;
            } else {
                appointmentAlert.setTitle("Alert");
                appointmentAlert.setHeaderText("No Upcoming Appointments");
                appointmentAlert.setContentText("There are no upcoming appointments for the user!");
                appointmentAlert.show();
            }
        }
    }
}
