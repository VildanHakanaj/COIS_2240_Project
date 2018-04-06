package Controllers;

import Classes.Event;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Duration;
import java.time.LocalDate;

public class NewEventController {

    // FX:ID variables
    @FXML
    private Rectangle eventBar;

    @FXML
    private Button btConfirm;

    @FXML
    private Button btCancel;

    @FXML
    private TextField titleField;

    @FXML
    private DatePicker datePicker;

    @FXML
    private Slider startTimeSlider;

    @FXML
    private Slider endTimeSlider;

    @FXML
    private Label startTimeValue;

    @FXML
    private Label endTimeValue;

    @FXML
    private TextArea descriptionField;

    @FXML
    private ChoiceBox privacyField;

    @FXML
    private ChoiceBox repeatsField;

    @FXML
    private CheckBox thirty;

    @FXML
    private CheckBox hour;

    @FXML
    private CheckBox day;

    @FXML
    private CheckBox week;

    @FXML
    private ColorPicker colorPicker;

    // global variable to hold date passed to NewEventController
    private LocalDate currentDate;

    // constructor to set currentDate to date that was passed
    public NewEventController(LocalDate currentDate){
        this.currentDate = currentDate;
    }

    // cancel creating an event and remove all fields
    @FXML
    private void cancel(){
        Stage stage = (Stage) btCancel.getScene().getWindow();
        stage.close();
        System.out.println("Cancel button pressed");
    }

    // listeners to actively update startTimeValue and endTimeValue labels in pane
    // formats value from 0 - 48 and converts it to the standard notation
    // updates time labels to current slider value
    @FXML
    private void startTimeSliderChange () {
        startTimeSlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
                double out1 = new_val.doubleValue();
                String s1End;
                out5 = Math.round(out1 / 2);
                if (out1 > 23) {
                    s1End = "PM";
                    out1 = out1 - 24;
                } else {
                    s1End = "AM";
                }
                double out2 = Math.round(out1) / 2;
                if (out2 == 0) {
                    out2 = 12;
                }
                if (out1 > 23) {
                    s1End = "AM";
                }
                startTimeValue.setText(String.format("%s", Math.round(out2) + s1End));

                // minimum value of endTime must be at least equal to start time
                endTimeSlider.setMin(startTimeSlider.getValue());
            }
        });
        System.out.println("startTimeSlider edited " + out5);
    }

    @FXML
    private void endTimeSliderChange () {
        endTimeSlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
                double out3 = new_val.doubleValue();
                String s2End;
                out6 = Math.round(out3 / 2);
                if (out3 > 23) {
                    s2End = "PM";
                    out3 = out3 - 24;
                } else {
                    s2End = "AM";
                }
                double out4 = Math.round(out3) / 2;
                if (out4 == 0) {
                    out4 = 12;
                }
                if (out3 > 23) {
                    s2End = "AM";
                }
                endTimeValue.setText(String.format("%s", Math.round(out4) + s2End));
            }
        });
        System.out.println("endTimeSlider edited " + out6);
    }

    // take colorPicker value and set eventBar to that colour
    @FXML
    private void colorPickerChange () {
        eventBar.setFill(colorPicker.getValue());
        System.out.println("Color picker edited");
    }

    // set date to current date
    public void initialize() {
        datePicker.setValue(currentDate);
    }

    // values for duration calculation
    double out5;
    double out6;

    // initialize and open a new window
    public void start() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/newEvent.fxml"));
        loader.setController(this);
        GridPane grid = loader.load();
        grid.getStylesheets().add("Stylesheets/event.css");
        Stage eventStage = new Stage();
        eventStage.setTitle("New Event");
        eventStage.setScene(new Scene(grid));
        eventStage.getIcons().addAll(new Image("/Photos/6.jpg"));
        eventStage.show();
    }

    @FXML
    private void confirm() {

        //check if duration is 0 terminate if it is
        if (out6 == 25) {
            out6 = 24;
        }
        double duration = Math.round(out6 - out5);
        if (duration == 0.0) {
            Stage stage = (Stage) btConfirm.getScene().getWindow();
            stage.close();
            System.out.println("Create event was terminated: Duration is 0");
        } else {

            //set default event title
            if (titleField.getText().trim().isEmpty())
                titleField.setText("Event");

            // print output for debugging reasons
            System.out.println(startTimeValue.getText() + "\n" + endTimeValue.getText());
            System.out.println("Duration is: " + duration);
            System.out.println(titleField.getText());
            System.out.println(datePicker.getValue());
            System.out.println(descriptionField.getText());
            System.out.println(privacyField.getValue());
            System.out.println(repeatsField.getValue());
            if (thirty.isSelected()) {
                System.out.println("Reminder 30");
            } if (hour.isSelected()) {
                System.out.println("Reminder hour");
            } if (day.isSelected()) {
                System.out.println("Reminder day");
            } if (week.isSelected()) {
                System.out.println("Reminder week");}

            // format colorPicker value to work with event bar when pulling out of database
            System.out.println(colorPicker.getValue());
            StringBuilder sb = new StringBuilder(String.valueOf(colorPicker.getValue()));
            sb.deleteCharAt(0);
            sb.deleteCharAt(0);
            sb.deleteCharAt(6);
            sb.deleteCharAt(6);
            String color = sb.toString();

            // store all the proper values in the database
            try {
                String url = "jdbc:sqlite:src/data.db";
                Connection conn = DriverManager.getConnection(url);

                Statement statement = conn.createStatement();
                statement.execute("INSERT INTO Event (Title, Date, " +
                        "Duration, Description, Privacy, Thirty, Hour, " +
                        "Day, Week, Repeat, Colour, Start, End, Color, Strt) VALUES " +
                        "('"+titleField.getText()+"','"+datePicker.getValue()+
                        "','"+duration+"','"+descriptionField.getText()+"','"
                        +privacyField.getValue()+"','"+thirty.getText()
                        +"','"+hour.getText()+"','"+day.getText()+"','"
                        +week.getText()+"','"+repeatsField.getValue()+"','#"
                        +color+"','"+startTimeValue.getText()+
                        "','"+endTimeValue.getText()+"','"+colorPicker.getValue()+"','"+startTimeValue.getText()+"')");
                statement.close();
                conn.close();
            } catch (SQLException e) {

                System.out.println("Something went wrong: " + e.getMessage());
            }
            Stage stage = (Stage) btConfirm.getScene().getWindow();
            stage.close();
            System.out.println("Confirm button pressed");
        }
    }
}
