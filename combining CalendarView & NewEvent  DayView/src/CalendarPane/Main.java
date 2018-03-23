package CalendarPane;

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

    CalendarPaneController calendarPaneController = new CalendarPaneController();

    @Override
    public void start(Stage stage) {


        CalendarView calendarView = new CalendarView();

        /*// BUTTON TO MOVE TO NEXT MONTH
        Button next = new Button(">");
        next.setMinSize(40,850);
        next.setOnAction(e -> calendarView.nextMonth());

        // BUTTON TO MOVE TO PREVIOUS MONTH
        Button previous = new Button("<");
        previous.setMinSize(40,850);
        previous.setOnAction(e -> calendarView.previousMonth());
*/

        try {
            calendarPaneController.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

        /*// STAGE WINDOW
        BorderPane root = new BorderPane(calendarView.getView(), null, null, null, null);
        root.setEffect(new InnerShadow());
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.getIcons().add(new Image("/Photos/6.jpg"));
        stage.show();
        stage.show();*/
    }


    public static void main(String[] args) {
        launch(args);
    }
}

