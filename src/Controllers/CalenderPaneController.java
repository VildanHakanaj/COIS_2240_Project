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
    private static Scene scene;         // Assumes only 1 calendar window at any given time (Which should be true anyways).
    private static AnchorPane layout;

    private java.util.Date date;        // Used to get current year, month and day.
    private int currentYear, currentMonth, currentDay, selectedyear, selectedMonth, selectedDay;
    private int daysInJan, daysInFeb, daysInMar, daysInApr, daysInMay, daysInJun, daysInJul, daysInAug, daysInSep, daysInOct, daysInNov, daysInDec;
    private int daysInYear;

    private int[][] dayBoxNums;

    @FXML
    Label lblYear;

    @FXML
    Label lblMonth;

    private enum WeekDay {
        dummy, Sunday, Monday, Tuesday, Wednesday, Thursday, Friday, Saturday;
    }

    private enum Month {
        dummy, January, February, March, April, May, June, July, August, September, October, November, December;
    }

    public void initialize() {
        // For some reason Date class uses zero indexing for days/months... Add 1 to everything.
        date = new java.util.Date();
        currentDay = date.getDay() + 1;
        currentMonth = date.getMonth() + 1;
        currentYear = date.getYear() + 1900;    // need to add 1900 to get year... for reasons.
        selectedDay = currentDay;               // Initialize selected variables on current date.
        selectedMonth = currentMonth;
        selectedyear = currentYear;
        dayBoxNums = new int[4][7];

        System.out.println(currentMonth + " " + currentDay + " " + currentYear);

        updateDaysInMonths(selectedyear);               // Set the number of days in each month
        lblYear.setText(Integer.toString(currentYear));
        lblMonth.setText(Month.values()[selectedMonth].toString());
    }

    public void start() {
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
        window.setScene(scene);
        window.setResizable(false);
        window.setTitle("Calender View");
        window.show();
        System.out.println("testing");
    }

    private void setDayBoxNumbers() {

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

    // Method Name: getDayBoxRow
    // Parameters:  String source
    // Behaviour:   Given the string from an event.getSource(), return the row (int) within the id within the string.
    //              Prints the row value to the console.
    //              Returns -1 if given string is invalid.
    // Returns:     int
    private int getDayBoxRow(String source) {
        int posZero;
        char rowChar;
        if (source.contains("id=dayBox_")) {
            posZero = source.indexOf("id=dayBox_");
            rowChar = source.charAt( posZero + 10); // Offset by 10 to account for all characters in "id=..."
            System.out.println("Row: " + rowChar);
            return (int)rowChar;
        } else {
            return -1;                              // Invalid given string returns -1.
        }
    }

    // Method Name: getDayBoxCol
    // Parameters:  String source
    // Behaviour:   Given the string from an event.getSource(), return the col (int) within the id within the string.
    //              Prints the collumn value to the console.
    //              Returns -1 if given string is invalid.
    // Returns:     int
    private int getDayBoxCol(String source) {
        int posZero;
        char colChar;
        if (source.contains("id=dayBox_")) {
            posZero = source.indexOf("id=dayBox_");
            colChar = source.charAt( posZero + 12); // Offset by 12 to account for all characters in "id=..."
            System.out.println("Col: " + colChar);
            return (int)colChar;
        } else {
            return -1;                              // Invalid given string returns -1.
        }
    }

    // Method Name: dayBoxHandler
    // Parameters:  none
    // Behaviour:   Sets the functionality of each dayBox.
    //              Assumes initDayBoxIDs has been previously called.
    // Returns:     void
    public void dayBoxHandler(MouseEvent event) {
        int row, col;
        String source = event.getSource().toString();
        System.out.println(source);
        row = getDayBoxRow(source);
        col = getDayBoxCol(source);
    }

    // Method Name: updateDaysInMonths
    // Parameters:  int year
    // Behaviour:   Sets the private methods for the number of days in the month given the year.
    //              Accounts for leap years.
    // Returns:     void
    private void updateDaysInMonths(int year) {
        this.daysInJan = 31;
        if (year % 4 == 0) {                // Is a leap year.
            this.daysInFeb = 29;
            this.daysInYear = 366;
        } else {
            this.daysInFeb = 28;
            this.daysInYear = 365;
        }
        this.daysInMar = 31;
        this.daysInApr = 30;
        this.daysInMay = 31;
        this.daysInJun = 30;
        this.daysInJul = 31;
        this.daysInAug = 31;
        this.daysInSep = 30;
        this.daysInOct = 31;
        this.daysInNov = 30;
        this.daysInDec = 31;
    }
}
