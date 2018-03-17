// THE VIEW CLASS FOR EVENT

/*  TODO
    USE CONTROLLER CLASS TO TAKE ALL USER INPUT AND SAVE IT AS AN EVENT OBJECT -
        EVENT TITLE WILL EDIT SAVE EVENT NAME
        START & END TIME SLIDERS SHOULD CALCULATE EVENT TIME AND CREATE A VECTOR ON CALENDAR
        DESCRIPTION WILL SAVE AS A STRING
        PRIVACY WILL SAVE AND ALLOW OTHER USERS TO VIEW OR NOT VIEW THE EVENT
        REMINDERS WILL STORE A STRING TELLING THE USER WHEN THEY WILL BE REMINDED
        REPEATS WILL PLACE VECTOR ON CALENDAR (CREATED ABOVE) AT SET INTERVALS
        COLOUR SELECTION WILL EDIT THE COLOUR OF THE VECTOR ON THE CALENDAR
        CONFIRM BUTTON WILL CHECK USER INPUT FOR ERRORS, PROMPT FOR INCORRECT OR EMPTY FIELDS, AND SAVE EVENT WHEN EVERYTHING IS GOOD
        CANCEL BUTTON WILL TERMINATE PROCESS

    EDIT CSS -
        SELECT A LARGER MORE LEGIBLE FONT
        NORMALIZE BUTTON SIZE AND CENTER TEXT ON BUTTONS
        ALLOW DESCRIPTION TEXT FIELD TO EXPAND FOR LARGE DESCRIPTIONS
        CHANGING COLOUR SHOULD CHANGE WINDOW BANNER COLOUR
        UPDATING THE EVENT TITLE FIELD SHOULD CAUSE THE WINDOW NAME TO BE "New Classes.Event - USER INPUT"
        GIVE IT THAT MODERN KONRAD FEEL
 */


package application;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


// RENAME CLASS FROM "Main" TO "NewEvent" WHEN MERGED WITH OTHER PROJECTS
public class Main extends Application{
    public void start(Stage eventStage) throws Exception{

        // SLIDER VALUES FOR TIME

        Slider startTimeSlider = new Slider();   // CONFIGURE START TIME SLIDER
       startTimeSlider.setMin(0);
       startTimeSlider.setMax(48);
       startTimeSlider.setValue(0);
       startTimeSlider.setShowTickLabels(false);
       startTimeSlider.setShowTickMarks(true);
       startTimeSlider.setMajorTickUnit(12);
       startTimeSlider.setMinorTickCount(4);
       startTimeSlider.setBlockIncrement(2);

           // LISTENER TO UPDATE TIME LABEL FOR START TIME SLIDER
           final Label startTimeValue = new Label(Double.toString(startTimeSlider.getValue()));
            startTimeSlider.valueProperty().addListener(new ChangeListener<Number>() {
                public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
                    double out1 = new_val.doubleValue();
                    String s1End;
                    if (out1 > 23){
                        s1End = "PM";
                        out1 = out1 - 24;
                    } else { s1End = "AM"; }
                    double out2 = Math.round(out1) / 2;
                    if (out2 == 0){ out2 = 12; }
                    if (out1 > 23){ s1End = "AM"; }
                    startTimeValue.setText(String.format("%s", Math.round(out2) + s1End));
                }
            });

        Slider endTimeSlider = new Slider();    // CONFIGURE END TIME SLIDER
        endTimeSlider.setMin(0);
        endTimeSlider.setMax(48);
        endTimeSlider.setValue(0);
        endTimeSlider.setShowTickLabels(false);
        endTimeSlider.setShowTickMarks(true);
        endTimeSlider.setMajorTickUnit(12);
        endTimeSlider.setMinorTickCount(4);
        endTimeSlider.setBlockIncrement(2);

            // LISTENER TO UPDATE TIME LABEL FOR END TIME SLIDER
            final Label endTimeValue = new Label(Double.toString(endTimeSlider.getValue()));
            endTimeSlider.valueProperty().addListener(new ChangeListener<Number>() {
                public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
                    double out3 = new_val.doubleValue();
                    String s2End;
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




       // IMPORT FXML CODE
        GridPane grid = FXMLLoader.load(getClass().getResource("NewEvent.fxml"));
        grid.getStylesheets().add("/application/StyleSheet.css");

        // PRINT OFF VALUES THAT I COULDN'T FIGURE OUT HOW TO PRINT OTHERWISE
        grid.add(startTimeSlider,1,2);
        grid.add(startTimeValue,2,2);
        grid.add(endTimeSlider, 1,3);
        grid.add(endTimeValue,  2,3);











        // WINDOW
        Scene scene = new Scene(grid);
        scene.getStylesheets().add("/application/StyleSheet.css");

        eventStage.initStyle(StageStyle.UNDECORATED);



        eventStage.setScene(scene);
        eventStage.setTitle("New Classes.Event");
        eventStage.show();
    }

    // RUN JAVAFX
    public static void main(String[] args) {
        launch(args);
    }
}
