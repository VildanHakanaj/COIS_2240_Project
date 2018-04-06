package Controllers;

import Classes.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;

public class DayPaneController {

    // Create global variable to receive date from CalendarPane
    LocalDate clickedDate;

    // Constructor to set this objects date to be the clicked date
    public DayPaneController(LocalDate clickedDate){
        this.clickedDate = clickedDate;
    }

    // FX:ID variables
    @FXML
    public GridPane gride;

    @FXML
    private Label title;

    @FXML
    private DatePicker date;

    // initialize datePicker date and title
    public void initialize(){
        date.setValue(clickedDate);
        title.setText(String.valueOf(clickedDate.getMonth() + " - " + clickedDate.getDayOfMonth()));
        updateDate();
    }

    // create and open a new window
    public void start() throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("FXML/dayPane.fxml"));
        loader.setController(this);
        BorderPane root = loader.load();
        root.getStylesheets().add("Stylesheets/dayPane.css");
        Stage eventStage = new Stage();
        eventStage.setResizable(false);
        eventStage.setTitle("Day Pane");
        eventStage.setScene(new Scene(root));
        eventStage.getIcons().addAll(new Image("/Photos/6.jpg"));
        eventStage.show();
    }

    // update the title and redraw event "buttons"
    public void updateDate() {
        // title according to the current date
        title.setText(String.valueOf(date.getValue().getMonth() + " - " + date.getValue().getDayOfMonth()));

        // select all existing boxes on grid
        // clear the gride
        // retain index 0 node for the gridoutline
        Node node = gride.getChildren().get(0);
        gride.getChildren().clear();
        gride.getChildren().add(0,node);

        // string oD is the openedDate value that is currently on screen
        String oD = String.valueOf(date.getValue());

        // call countRows to query how many entries are on the current date
        int size = countRows(oD);

        // box is the id of an event
        int box;

        // create array to store buttons as well as event
        Button[] btArr;
        Event[] index;

        // create array to store event ID from button
        int[] id;

        // clear arrays from previous loops
        index = null;
        btArr = null;
        id = null;

        // set array sizes to the number of rows in the database
        btArr = new Button[size];
        id = new int[size];

        try {
            String url = "jdbc:sqlite:src/data.db";
            Connection conn = DriverManager.getConnection(url);

            Statement statement = conn.createStatement();

            //***********************************//
            ResultSet rs = statement.executeQuery("SELECT * FROM Event WHERE Date='" + oD + "'");

            // for every entry in the database
            for (int i = 0; i < countRows(oD); i++) {

                // move to the next entry in resultSet
                rs.next();

                // set box value to ID value at current pointer in resultSet
                box = rs.getInt("ID");

                // set index i in ID at ID value of box
                id[i] = box;

                // draw the button on the grid
                Event bt = new Event(box);
                Button button = new Button(bt.getTitleField());             // set titlefield
                button.setMinHeight(bt.getDuration() * 30);                 // set height by increments of 30
                button.setStyle("-fx-background-color:"+bt.getColour());    // set colour of box
                button.setAlignment(Pos.TOP_LEFT);                          // set aligment of text

                // create static array copying the ID array, for use in action event
                int[] finalId = id;

                // on click open correct event
                button.addEventHandler(MouseEvent.MOUSE_CLICKED,
                        new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event) {

                                // set ID try equal to the index that is the column of the button
                                // buttons are placed in 1 by one meaning their column index is the same as their ID index
                                int IDt = finalId[GridPane.getColumnIndex((Node) event.getSource())];

                                // open event pane
                                try {
                                    EventController ec = new EventController(IDt);  // pass ID of event
                                    ec.start();                                     // initialize event
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                        });

                // set alignment of events to start at the top
                GridPane.setValignment(button, VPos.TOP);

                // add button to screen at column equal to their index, and row equal to their start time
                btArr[i] = (button);

                // check for fringe case if user does not set initial time
                if (bt.getStrt().equals("12AM")){
                    gride.add(btArr[i], i, 0);
                } else { gride.add(btArr[i], i, bt.getStart()); }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

    // open NewEventPane and pass it the current date
    public void create(){
        NewEventController newEventController = new NewEventController(date.getValue(), this);
        try {
            newEventController.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // count the number of rows present in the database for use in updateDate method
    public static int countRows(String date) {
        int count = 0;
        try {
            Connection conn = DriverManager.getConnection("jdbc:sqlite:src/data.db");
            Statement s = conn.createStatement();

            //****************************
            ResultSet r = s.executeQuery("SELECT COUNT(*) AS ID FROM Event WHERE Date = '"+ date +"'");
            r.next();
            count = r.getInt("ID");
            r.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(count);
        return count;
    }
}