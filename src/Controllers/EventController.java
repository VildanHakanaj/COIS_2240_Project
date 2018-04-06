

package Controllers;

import Classes.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
import java.time.format.DateTimeFormatter;

public class EventController {

    // FX:ID variables
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

    // ID to be global variable
    private int ID;

    // set this ID equal to ID that was passed
    public EventController(int ID) throws SQLException {
        this.ID = ID;
    }

    // if delete button is pressed remove event from database and close window
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

    // if close button is pressed close window
    public void exit(){
        Stage stage = (Stage) btClose.getScene().getWindow();
        stage.close();
    }

    // set values inside the event window
    public void initialize() throws Exception{

        // instantiate a new event equal to ID of event that was pressed
        Event event = new Event(ID);

        // populate fields with information from that event
        eventBar.setFill(Color.valueOf(event .getColour()));
        eventTitle.setText(event.getTitleField());
        descriptionField.setText(event.getDescriptionField());
        startTimeValue.setText(event.getStrt());
        endTimeValue.setText(event.getEnd());
        privacySetting.setText(event.getPrivacy());
        repeatValue.setText(event.getRepeat());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-mm-dd");
        date.setText(event.getDate());
        thirty.setText(event.getThirty());
        hour.setText(event.getHour());
        day.setText(event.getDay());
        week.setText(event.getWeek());
    }

    // create and open a new window
    public void start() throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("FXML/event.fxml"));
        loader.setController(this);
        GridPane grid = loader.load();
        Stage eventStage = new Stage();
        eventStage.setTitle(String.valueOf(ID));
        eventStage.setScene(new Scene(grid));
        eventStage.getIcons().addAll(new Image("/Photos/6.jpg"));
        eventStage.show();
    }
}
