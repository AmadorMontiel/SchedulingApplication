package View_Controller;

import DataModel.Appointment;
import Implementations.AppointmentDaoImpl;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;

public class ViewAppointmentsController  {

    public TableView<Appointment> appointmentsTable;
    public TableColumn<Appointment, Integer> appointmentIDColumn;
    public TableColumn<Appointment, String> titleColumn;
    public TableColumn<Appointment, String> descriptionColumn;
    public TableColumn<Appointment, String> locationColumn;
    public TableColumn<Appointment, String> contactColumn;
    public TableColumn<Appointment, String> typeColumn;
    public TableColumn<Appointment, LocalDateTime> startDateTimeColumn;
    public TableColumn<Appointment, LocalDateTime> endDateTimeColumn;
    public TableColumn<Appointment, Integer> customerIDColumn;
    public RadioButton byMonth;
    public RadioButton byWeek;
    public RadioButton viewAll;


    private ObservableList<Appointment> appointmentsList;

    public void initialize() {

        appointmentIDColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactColumn.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>( "type"));
        startDateTimeColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
        endDateTimeColumn.setCellValueFactory(new PropertyValueFactory<>("end"));
        customerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));

        appointmentsList = AppointmentDaoImpl.getAllAppointmentsWithNames();
        allAppointments();

    }

    public void appointmentsByWeek() {
        appointmentsTable.setItems(AppointmentDaoImpl.getAppointmentsByWeek());


    }

    public void appointmentsByMonth() {
        appointmentsTable.setItems(AppointmentDaoImpl.getAppointmentsByMonth());
    }

    public void allAppointments() {
        appointmentsTable.setItems(appointmentsList);

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
