package Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class EventController {

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
    private DatePicker date;

    @FXML
    private Label thirty;

    @FXML
    private Label hour;

    @FXML
    private Label day;

    @FXML
    private Label week;

    public void initialize(){
    // set all values of the event viewer
    }

    public void delete(){
        // get event id and remove the information from the database

        Stage stage = (Stage) btDelete.getScene().getWindow();
        stage.close();
    }

    public void exit(){
        Stage stage = (Stage) btClose.getScene().getWindow();
        stage.close();
    }

    // create and open a new window
    public void start() throws Exception{

        GridPane root = FXMLLoader.load(getClass().getResource("/FXML/event.fxml"));
        root.getStylesheets().add("StyleSheet.css");

        Stage eventStage = new Stage();

        eventStage.setTitle("Event");
        eventStage.setScene(new Scene(root));
        eventStage.getIcons().addAll(new Image("/Photos/6.jpg"));

        eventStage.show();
    }
}
