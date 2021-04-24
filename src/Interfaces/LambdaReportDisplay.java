package Interfaces;

import DataModel.Appointment;
import javafx.collections.ObservableList;

/**
 * Functional Interface for LambdaReportDisplay
 */
public interface LambdaReportDisplay {
    /**
     * Abstract method for use in initialize method of ReportsController.
     * @param appointments Observable List to be modified.
     * @return The observable list of appointments that has been set.
     */
    ObservableList<Appointment> observableListAssignment(ObservableList<Appointment> appointments);
}
