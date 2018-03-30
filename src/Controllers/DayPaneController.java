package Controllers;

import Classes.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

import static javafx.scene.paint.Color.RED;


public class DayPaneController {

    NewEventController newEventController = new NewEventController();
    Event event;


    {
        try {
            event = new Event();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public GridPane gride;

    @FXML
    private Label title;

    @FXML
    private DatePicker date;

    // initialize datePicker date and title
    public void initialize(){

        LocalDate now = LocalDate.now();
        date.setValue(now);

        title.setText(String.valueOf(now.getMonth() + " - " + now.getDayOfMonth()));

        updateDate();
    }


    // create and open a new window
    public void start() throws Exception{
        BorderPane root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/FXML/dayPane.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        root.getStylesheets().add("Stylesheets/dayPane.css");

        Stage eventStage = new Stage();


        eventStage.initStyle(StageStyle.UNDECORATED);
        eventStage.setTitle("Day Pane");
        eventStage.setScene(new Scene(root));
        eventStage.getIcons().addAll(new Image("/Photos/6.jpg"));

        eventStage.show();

    }

    private int comp;

    // update the title and redraw event "buttons"
    public void updateDate() {
        title.setText(String.valueOf(date.getValue().getMonth() + " - " + date.getValue().getDayOfMonth()));
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

    // count the number of rows present in the database
    public static int countRows() {
        int count = 0;
        try {
            Connection conn = DriverManager.getConnection("jdbc:sqlite:src/data.db");
            Statement s = conn.createStatement();
            ResultSet r = s.executeQuery("SELECT COUNT(*) AS ID FROM Event");
            r.next();
            count = r.getInt("ID");
            r.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }
}