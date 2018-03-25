package CalendarPane;

import LoginPane.LoginController;
import NewEventPane.NewEventController;
import javafx.application.Application;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    LoginController loginController = new LoginController();
    CalendarPaneController calendarPaneController = new CalendarPaneController();

    @Override
    public void start(Stage stage) {


        try {
            calendarPaneController.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}

