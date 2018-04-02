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

import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
public class CalenderPaneController {

    // ----- DATA MEMBERS -----
    EventController eventController = new EventController();
    NewEventController newEventController = new NewEventController();

    Stage window;
    Scene scene;
    AnchorPane layout;

    public void initialize(){}

    public void start() throws Exception {
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
    }

    // Method Name: dayBoxHandler
    // Parameters:  None
    // Behaviour:   Sets the functionality of each dayBox.
    // Returns:     void
    public void dayBoxHandler() {
        Pane[] dayBoxs = new Pane[28];  // 4*7 = 28
        String tempID;
        int row, col, i = 0;
        for(row = 1; row <= 4; row ++) {
            for (col = 1; col <= 7; col++) {
                tempID = "dayBox_" + row + "_" + col;
                i++;    // Increment for next loop.
            }
        }
    }
}
