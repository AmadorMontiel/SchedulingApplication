package Interfaces;

import javafx.scene.control.Alert;

/**
 * Functional Interface for LambdaAlert
 */
public interface LambdaAlert {
    /**
     * Abstract method for use in the saveUpdatedAppointment method in the
     * UpdateAppointmentController.
     * @param errorAlert The Alert to be modified and displayed.
     */
    void invalidInputError(Alert errorAlert);
}
