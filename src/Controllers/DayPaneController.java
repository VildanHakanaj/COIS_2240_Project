package Controllers;

import Classes.Event;
import com.sun.xml.internal.bind.v2.model.core.ID;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
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
    EventController eventController = new EventController();

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
            root = FXMLLoader.load(getClass().getClassLoader().getResource("FXML/dayPane.fxml"));
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


    // update the title and redraw event "buttons"
    public void updateDate() {
        title.setText(String.valueOf(date.getValue().getMonth() + " - " + date.getValue().getDayOfMonth()));

        Node node = gride.getChildren().get(0);
        gride.getChildren().clear();
        gride.getChildren().add(0,node);


        ArrayList<Button> btArr = new ArrayList<Button>();
        btArr.clear();

        String oD = String.valueOf(date.getValue());
        System.out.println(oD);

        int count = 0;
        int box = 0;

        try {
            String url = "jdbc:sqlite:src/data.db";
            Connection conn = DriverManager.getConnection(url);

            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT ID FROM Event WHERE Date='" + oD + "'");

            for (int i = 0; i < countRows(oD); i++) {
                count++;
                System.out.println(count);


                rs.next();
                box = rs.getInt("ID");

                // create an event, take info from event and store it into the button, add that to the arraylist

                Event bt = new Event(box);
                Button button = new Button(bt.getTitleField());
                button.setMinHeight(bt.getDuration() * 30);
                button.setStyle("-fx-background-color:"+bt.getColour());
                button.setAlignment(Pos.TOP_LEFT);
                int finalBox = box;
                button.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        eventController.setID(finalBox);
                        try {
                            eventController.start();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });


                GridPane.setValignment(button, VPos.TOP);

                btArr.add(button);
                gride.add(btArr.get(i), i, bt.getStart());
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

    // control if createEvent button is pressed, creates a new event
    public void create(){
        try {
            newEventController.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // count the number of rows present in the database
    public static int countRows(String date) {
        int count = 0;
        try {
            Connection conn = DriverManager.getConnection("jdbc:sqlite:src/data.db");
            Statement s = conn.createStatement();
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