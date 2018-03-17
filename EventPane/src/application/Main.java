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
import javafx.stage.Stage;
import javafx.stage.StageStyle;


// RENAME CLASS FROM "Main" TO "NewEvent" WHEN MERGED WITH OTHER PROJECTS
public class Main extends Application{
    public void start(Stage eventStage) throws Exception{



       // IMPORT FXML CODE
        GridPane grid = FXMLLoader.load(getClass().getResource("Event.fxml"));
        grid.getStylesheets().add("/application/StyleSheet.css");




        // WINDOW
        Scene scene = new Scene(grid);
        scene.getStylesheets().add("/application/StyleSheet.css");

        //eventStage.initStyle(StageStyle.UNDECORATED);

        Image image = new Image("/application/Photos/6.jpg");
        eventStage.getIcons().addAll(image);

        eventStage.setScene(scene);
        eventStage.setTitle("New Classes.Event");
        eventStage.show();
    }

    // RUN JAVAFX
    public static void main(String[] args) {
        launch(args);
    }
}
