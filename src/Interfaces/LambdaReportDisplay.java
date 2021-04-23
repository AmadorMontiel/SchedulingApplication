package Interfaces;

import DataModel.Appointment;
import javafx.collections.ObservableList;

public interface LambdaReportDisplay {
    ObservableList<Appointment> observableListAssignment(ObservableList<Appointment> appointments);
}
