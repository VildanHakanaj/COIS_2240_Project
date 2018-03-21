import java.util.Locale;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {

        CalendarView calendarView = new CalendarView() ;

        Button next = new Button(">");
        next.setOnAction(e -> calendarView.nextMonth());

        Button previous = new Button("<");
        previous.setOnAction(e -> calendarView.previousMonth());



        BorderPane root = new BorderPane(calendarView.getView(), null, next, null, previous);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}