
import Controllers.CalenderPaneController;
import Controllers.DayPaneController;
import Controllers.EventController;
import javafx.application.Application;

import javafx.stage.Stage;

public class Main extends Application {

    // just start the code. keeps controllers similar to other controllers

    //LoginController loginController = new LoginController();
    CalenderPaneController calenderPaneController = new CalenderPaneController();

    @Override
    public void start(Stage stage) {

        try {
            calenderPaneController.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

