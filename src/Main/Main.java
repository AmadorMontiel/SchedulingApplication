package Main;

import Utility.DBConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.Locale;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../View_Controller/login.fxml"));
        if(Locale.getDefault().getLanguage().equals("fr")) { primaryStage.setTitle("Application de Planification "); }
        else { primaryStage.setTitle("Scheduling Application"); }
        primaryStage.setScene(new Scene(root, 450, 300));
        primaryStage.show();
    }


    public static void main(String[] args) {
        //Locale.setDefault(new Locale("fr"));

        /*Value returing lambda expression
        GeneralInterface square = (n) -> n*n;
        System.out.println(square.calcSquare(6));
         */

        DBConnection.startConnection();
        launch(args);
        DBConnection.closeConnection();
    }
}
