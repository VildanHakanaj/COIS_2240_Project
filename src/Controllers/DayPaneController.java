package Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.stage.StageStyle;

import javax.swing.text.Style;
import java.time.LocalDate;


public class DayPaneController {

    NewEventController newEventController = new NewEventController();

    @FXML
    private Label title;

    @FXML
    private DatePicker date;

    // initialize datePicker date and title
    public void initialize(){
        LocalDate now = LocalDate.now();
        date.setValue(now);

        title.setText(String.valueOf(now.getMonth() + " - " + now.getDayOfMonth()));
    }

    // create and open a new window
    public void start() throws Exception{

        BorderPane root = FXMLLoader.load(getClass().getResource("/FXML/dayPane.fxml"));
        root.getStylesheets().add("Stylesheets/dayPane.css");

        Stage eventStage = new Stage();


        eventStage.initStyle(StageStyle.UNDECORATED);
        eventStage.setTitle("Day Pane");
        eventStage.setScene(new Scene(root));
        eventStage.getIcons().addAll(new Image("/Photos/6.jpg"));

        eventStage.show();
    }

    // update the title and redraw event "buttons"
    public void updateDate(){
        title.setText(String.valueOf(date.getValue().getMonth() + " - " + date.getValue().getDayOfMonth()));
        //logically update events that are drawn
        //make sure to check the privacy of the event
    }

    // move to the next day
    public void nextDate(){
        date.setValue(date.getValue().plusDays(1));
        updateDate();
    }

    // move to the previous day
    public void lastDate(){
        date.setValue(date.getValue().minusDays(1));
        updateDate();
    }

    // control if createEvent button is pressed, creates a new event
    public void create(){
        try {
            newEventController.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
