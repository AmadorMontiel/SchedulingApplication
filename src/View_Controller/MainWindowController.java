package View_Controller;

import DataModel.Appointment;
import DataModel.Customer;
import Implementations.AppointmentDaoImpl;
import Implementations.CustomerDaoImpl;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class MainWindowController {

    public Alert errorAlert = new Alert(Alert.AlertType.ERROR);
    public Alert deletionConfirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);

    public ComboBox<Appointment> appointmentsComboBox;
    public ComboBox<Customer> customersComboBox;
    public Stage stage;
    private Scene scene;
    ObservableList<Customer> customerObservableList = CustomerDaoImpl.getAllCustomers();
    ObservableList<Appointment> appointmentsObservableList = AppointmentDaoImpl.getAllAppointments();

    public void initialize() {

        customersComboBox.setItems(customerObservableList);
        appointmentsComboBox.setItems(appointmentsObservableList);
    }
    /**
     * Closes the application
     */
    public void exitProgram() {
        Platform.exit();
    }

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
            errorAlert.setContentText("Please select a customer to update");
            errorAlert.show();
        }

    }

    public void deleteCustomerClicked(MouseEvent event) {

        //TODO Add in message checking to make sure user wants to delete the customer
        //TODO Add in check to see if there are any associated appointments for the customer

        boolean isCustomerDeleted = false;
        if (customersComboBox.getSelectionModel().getSelectedItem() != null) {

            deletionConfirmationAlert.setTitle("Confirmation");
            deletionConfirmationAlert.setHeaderText("Confirm Customer Deletion");
            deletionConfirmationAlert.setContentText("Are you sure you want to delete " + customersComboBox.getSelectionModel().getSelectedItem().getName());
            deletionConfirmationAlert.showAndWait();

            try {
                customersComboBox.getItems().remove(customersComboBox.getSelectionModel().getSelectedItem());
                CustomerDaoImpl.deleteCustomer(customersComboBox.getSelectionModel().getSelectedItem().getId());

            } catch (NullPointerException e) {
                e.printStackTrace();
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

    public void updateAppointmentClicked(MouseEvent event) {
        sceneLoader(event, "updateappointment.fxml");
    }

    public void deleteAppointmentClicked(MouseEvent event) {
        sceneLoader(event, "deleteappointment.fxml");
    }

    public void viewAppointmentsClicked(MouseEvent event) {
        sceneLoader(event, "viewappointment.fxml");
    }

    public void reportsClicked(MouseEvent event) {
        sceneLoader(event, "reports.fxml");
    }
}
