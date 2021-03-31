package View_Controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class DeleteAppointmentController {

    /**
     * Closes the delete appointment screen and takes the use back to the main screen.
     * @param event Clicking the "Delete" or "Cancel" button
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
