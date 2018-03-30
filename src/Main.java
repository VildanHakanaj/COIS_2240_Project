
import Controllers.DayPaneController;
import Controllers.LoginController;
import javafx.application.Application;

import javafx.stage.Stage;

public class Main extends Application {

    // just start the code. keeps controllers similar to other controllers

    LoginController loginController = new LoginController();

    @Override
    public void start(Stage stage) {

        try {
            loginController.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

