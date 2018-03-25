package NewEventPane;

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
import javafx.stage.StageStyle;

import java.time.LocalDate;


public class NewEventController {

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

    public void initialize(){
        LocalDate now = LocalDate.now();
        datePicker.setValue(now);
    }

    // values for duration calculation
    double out5;
    double out6;

    public void start() throws Exception{

        Long id = java.time.Instant.now().getEpochSecond();

        System.out.println(id);

        GridPane grid = FXMLLoader.load(getClass().getResource("newEvent.fxml"));
        grid.getStylesheets().add("/NewEventPane/StyleSheet.css");

        Stage eventStage = new Stage();

        eventStage.setTitle("New Event");
        eventStage.setScene(new Scene(grid));
        eventStage.initStyle(StageStyle.TRANSPARENT);
        eventStage.getIcons().addAll(new Image("/Photos/6.jpg"));

        eventStage.show();
    }

    /*
    * [ ] Validate the input
    * [ ] Insert
    * */
    @FXML
    private void confirm(){

        //check if duration is 0 terminate if it is
        if (out6 == 25){ out6 = 24; }
        double duration = Math.round(out6 -  out5);
        if (duration == 0.0){
            Stage stage = (Stage) btConfirm.getScene().getWindow();
            stage.close();
            System.out.println("Create event was terminated: Duration is 0");
        } else {

        //set default event title
        if (titleField.getText().trim().isEmpty())
            titleField.setText("Event");


        //Code to retrieve data and
            //Validate the data
        System.out.println(startTimeValue.getText() + "\n" +endTimeValue.getText());
        System.out.println("Duration is: " + duration);
        System.out.println(titleField.getText());
        System.out.println(datePicker.getValue());
        System.out.println(descriptionField.getText());
        System.out.println(privacyField.getValue());
        System.out.println(repeatsField.getValue());


        //Check if the any of the reminders are selected
            //not neccessary
        if (thirty.isSelected()){
            System.out.println("Reminder 30");
        }
        if (hour.isSelected()){
            System.out.println("Reminder hour");
        }
        if (day.isSelected()){
            System.out.println("Reminder day");
        }
        if (week.isSelected()){
            System.out.println("Reminder week");
        }

        //print the color
        System.out.println(colorPicker.getValue());

        //Close the event page
        Stage stage = (Stage) btConfirm.getScene().getWindow();
        stage.close();
        System.out.println("Confirm button pressed");
    }}
    //The cancel button
    @FXML
    private void cancel(){
        Stage stage = (Stage) btCancel.getScene().getWindow();
        stage.close();
        System.out.println("Cancel button pressed");
    }

    //Start time of the event
    @FXML
    private void startTimeSliderChange(){
        startTimeSlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
                double out1 = new_val.doubleValue();
                String s1End;
                out5 = Math.round(out1 / 2);
                if (out1 > 23){
                    s1End = "PM";
                    out1 = out1 - 24;
                } else { s1End = "AM"; }
                double out2 = Math.round(out1) / 2;
                if (out2 == 0){ out2 = 12; }
                if (out1 > 23){ s1End = "AM"; }
                startTimeValue.setText(String.format("%s", Math.round(out2) + s1End));
                endTimeSlider.setMin(startTimeSlider.getValue());
            }
        });
        System.out.println("startTimeSlider edited " + out5);
    }

    //End time of the event
    @FXML
    private void endTimeSliderChange(){
        endTimeSlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
                double out3 = new_val.doubleValue();
                String s2End;
                out6 = Math.round(out3 / 2);
                if (out3 > 23){
                    s2End = "PM";
                    out3 = out3 - 24;
                } else { s2End = "AM"; }
                double out4 = Math.round(out3) / 2;
                if (out4 == 0){ out4 = 12; }
                if (out3 > 23){ s2End = "AM"; }
                endTimeValue.setText(String.format("%s", Math.round(out4) + s2End));
            }
        });
        System.out.println("endTimeSlider edited " + out6);
    }

    @FXML
    //Color picking for the event.
    private void colorPickerChange(){
        eventBar.setFill(colorPicker.getValue());
        System.out.println("Color picker edited");
    }
}
