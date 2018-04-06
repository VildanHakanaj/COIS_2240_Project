
import Controllers.CalenderPaneController;
import Controllers.DayPaneController;
import Controllers.EventController;
import Controllers.LoginController;
import javafx.application.Application;

import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.Scanner;
public class Main extends Application {

    // just start the code. keeps controllers similar to other controllers

    LoginController loginController = new LoginController();

    public Main() throws SQLException {

    }

    @Override
    public void start(Stage stage) {

        try {
            //Start with the login popup
            loginController.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

