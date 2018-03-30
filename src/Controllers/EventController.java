package Controllers;

import Classes.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class EventController {

    Event event;

    {
        try {
            event = new Event();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private Button btDelete;

    @FXML
    private Button btClose;

    @FXML
    private Rectangle eventBar;

    @FXML
    private Label eventTitle;

    @FXML
    private TextArea descriptionField;

    @FXML
    private Label startTimeValue;

    @FXML
    private Label endTimeValue;

    @FXML
    private Label privacySetting;

    @FXML
    private Label repeatValue;

    @FXML
    private Label date;

    @FXML
    private Label thirty;

    @FXML
    private Label hour;

    @FXML
    private Label day;

    @FXML
    private Label week;

    private int ID;

    public void initialize(){

        ID = event.getID();

        eventBar.setFill(Color.valueOf(event.getColour()));
        eventTitle.setText(event.getTitleField());
        descriptionField.setText(event.getDescriptionField());
        startTimeValue.setText(event.getStart());
        endTimeValue.setText(event.getEnd());
        privacySetting.setText(event.getPrivacy());
        repeatValue.setText(event.getRepeat());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-mm-dd");
        date.setText(event.getDate());

        if (event.getThirty() == "true"){
            thirty.setText("30 minutes before");
        } else { thirty.setText(""); }

        if (event.getHour() == "true"){
            hour.setText("1 hour before");
        } else { hour.setText(""); }

        if (event.getDay() == "true"){
            day.setText("1 day before");
        } else { day.setText(""); }

        if (event.getWeek() == "true"){
            week.setText("1 week before");
        } else { week.setText(""); }
    }

    public void delete(){
        // get event id and remove the information from the database
        try {
            String url = "jdbc:sqlite:src/data.db";
            Connection conn = null;

                conn = DriverManager.getConnection(url);

            Statement statement = conn.createStatement();
            statement.execute("DELETE FROM Event WHERE ID='"+ID+"'");

            statement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Stage stage = (Stage) btDelete.getScene().getWindow();
        stage.close();
    }

    public void exit(){
        Stage stage = (Stage) btClose.getScene().getWindow();
        stage.close();
    }

    // create and open a new window
    public void start() throws Exception{

        GridPane grid = FXMLLoader.load(getClass().getResource("/FXML/event.fxml"));
        grid.getStylesheets().add("StyleSheets/event.css");

        Stage eventStage = new Stage();

        eventStage.setTitle(String.valueOf(ID));
        eventStage.setScene(new Scene(grid));
        eventStage.getIcons().addAll(new Image("/Photos/6.jpg"));

        eventStage.show();
    }
}
