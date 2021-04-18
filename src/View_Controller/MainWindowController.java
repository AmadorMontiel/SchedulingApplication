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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

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
    public Button exitButton;

    public void initialize() {

        customersComboBox.setItems(CustomerDaoImpl.getAllCustomers());
        appointmentsComboBox.setItems(AppointmentDaoImpl.getAllAppointments());

        //Added lambda function to close that application on clicking Exit button
        exitButton.setOnAction(event -> Platform.exit());
    }

    /**
     * Closes the application
     */
    /*
    public void exitProgram() {
        Platform.exit();
    }
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

    private FXMLLoader getFxmlLoader(String s) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(s));
        loader.load();
        return loader;
    }

    private void loadNewScene(MouseEvent event, FXMLLoader loader) {
        Node node = (Node) event.getSource();
        stage = (Stage) node.getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
    }

    public void addCustomerClicked(MouseEvent event) {
        sceneLoader(event, "addcustomer.fxml");
    }

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

    public void addAppointmentClicked(MouseEvent event) {
        sceneLoader(event, "addappointment.fxml");
    }

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
        }
    }

    public void deleteAppointmentClicked(MouseEvent event) {
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

    public void viewAppointmentsClicked(MouseEvent event) {
        sceneLoader(event, "viewappointment.fxml");
    }

    public void reportsClicked(MouseEvent event) {
        sceneLoader(event, "reports.fxml");
    }

    public void receiveUser(User userLoggedIn) {
        loggedInUser = userLoggedIn;
        associatedAppointmentsWithUser = AppointmentDaoImpl.getAppointmentsAssociatedWithUser(loggedInUser.getUserID());
        for (Appointment appointment : associatedAppointmentsWithUser) {
            if(AppointmentDaoImpl.isAppointmentWithin15Minutes(appointment.getAppointmentID())) {
                appointmentAlert.setTitle("Alert");
                appointmentAlert.setHeaderText("Appointment Coming Up!");
                appointmentAlert.setContentText("Appointment ID: " + appointment.getAppointmentID()
                        + " Date/Time: " + TimeConversion.localTimeConversion(appointment.getStart()));
                appointmentAlert.show();
            } else {
                appointmentAlert.setTitle("Alert");
                appointmentAlert.setHeaderText("No Upcoming Appointments");
                appointmentAlert.setContentText("There are no upcoming appointments for the user!");
                appointmentAlert.show();
            }
        }
    }
}
