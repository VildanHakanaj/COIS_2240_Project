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
import javafx.scene.layout.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.stage.StageStyle;
import javafx.scene.input.MouseEvent;
import jdk.internal.org.objectweb.asm.Handle;

import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

public class CalenderPaneController {

    // ----- DATA MEMBERS -----

    // private EventController eventController = new EventController();
    // private NewEventController newEventController = new NewEventController();

    private DayPaneController dayPaneController = new DayPaneController();

    private static Stage window;        // Static values allow handlers to close THE window.
    private static Scene scene;         // Assumes only 1 calendar window at any given time (Which should be true)
    private static AnchorPane layout;

    public void initialize(){

    }

    public void start() {
        // layout = null;
        // load .fxml to the layout.
        try {
            layout = FXMLLoader.load(getClass().getClassLoader().getResource("FXML/calenderPane.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // load stylesheet to the layout.
        // Initialize stage and scene.
        window = new Stage();
        scene = new Scene(layout);
        window.initStyle(StageStyle.UNDECORATED);
        window.setTitle("Calender View");
        window.setScene(scene);
        window.show();
        System.out.println("testing");
        dayBoxHandler();
    }

    public void clickDayBox(MouseEvent event) {
        System.out.println(event.getSource().toString());
    }

    // Method Name: switchToDayPane
    // Parameters:  ActionEvent event
    // Behaviour:   Opens an instance of DayPaneController.
    //              Closes current window.
    // Returns:     void
    public void switchToDayPane(ActionEvent event) {
        System.out.println("Button Press: Switch to Day View");
        try {
            dayPaneController.start();      // Start a dayPaneController instance.
        } catch (Exception e) {
            e.printStackTrace();
        }
        window.close();                     // Close the current window.
    }

    // Method Name: dayBoxHandler
    // Parameters:  None
    // Behaviour:   Sets the functionality of each dayBox.
    // Returns:     void
    public void dayBoxHandler() {
        Pane[] dayBoxs = new Pane[28];  // 4*7 = 28
        final String[] IDs = new String[28];
        String indexedID;
        int row, col, i = 0;
        for(row = 1; row <= 4; row ++) {
            for (col = 1; col <= 7; col++) {
                indexedID = "dayBox_" + row + "_" + col;
                dayBoxs[i] = new Pane();
                dayBoxs[i].setId(indexedID);
                IDs[i] = indexedID;
                dayBoxs[i].setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @FXML
                public void handle(MouseEvent e) {

                        /*eventController.setID();
                        try {
                            eventController.start();
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }*/
                }
            });
                i++;    // Increment for next loop.
            }
        }
    }
}
