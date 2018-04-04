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

    // -------------------------------------- DATA MEMBERS ------------------------------------------

    // private EventController eventController = new EventController();
    // private NewEventController newEventController = new NewEventController();

    private DayPaneController dayPaneController;

    private static Stage window;            // Static values allow handlers to close THE window.
    private static Scene scene;             // Assumes only 1 calendar window at any given time (Should be true regardless).
    private static AnchorPane layout;

    private java.util.Date date;            // Used to get current year, month and day.
    private int selectedYear, selectedMonth;

    private int[][] dayBoxNums;
    private boolean[][] dayIsPartOfCurrentMonth;

    @FXML
    Label lblMonthYear;

    @FXML
    Label boxNum_1_1, boxNum_1_2, boxNum_1_3, boxNum_1_4, boxNum_1_5, boxNum_1_6, boxNum_1_7,
            boxNum_2_1, boxNum_2_2, boxNum_2_3, boxNum_2_4, boxNum_2_5, boxNum_2_6, boxNum_2_7,
            boxNum_3_1, boxNum_3_2, boxNum_3_3, boxNum_3_4, boxNum_3_5, boxNum_3_6, boxNum_3_7,
            boxNum_4_1, boxNum_4_2, boxNum_4_3, boxNum_4_4, boxNum_4_5, boxNum_4_6, boxNum_4_7,
            boxNum_5_1, boxNum_5_2, boxNum_5_3, boxNum_5_4, boxNum_5_5, boxNum_5_6, boxNum_5_7;

    @FXML
    Pane dayBox_1_1, dayBox_1_2,dayBox_1_3,dayBox_1_4,dayBox_1_5,dayBox_1_6,dayBox_1_7,
            dayBox_2_1, dayBox_2_2,dayBox_2_3,dayBox_2_4,dayBox_2_5,dayBox_2_6,dayBox_2_7,
            dayBox_3_1, dayBox_3_2,dayBox_3_3,dayBox_3_4,dayBox_3_5,dayBox_3_6,dayBox_3_7,
            dayBox_4_1, dayBox_4_2,dayBox_4_3,dayBox_4_4,dayBox_4_5,dayBox_4_6,dayBox_4_7,
            dayBox_5_1, dayBox_5_2,dayBox_5_3,dayBox_5_4,dayBox_5_5,dayBox_5_6,dayBox_5_7;

    // Enum Name:   Month
    // Usage:       Used to neatly and conveniently get the strings of month names given an int index representing the month.
    private enum Month {
        dummy, January, February, March, April, May, June, July, August, September, October, November, December;
    }

    // ----------------------------------- INIT/START ---------------------------------------

    // Method Name: initialize
    // Parameters:  none
    // Behaviour:   default javafx method.
    // Returns:     void
    public void initialize() {
        dayPaneController = new DayPaneController();    // Used when switching to the dayPane view.
        date = new java.util.Date();                    // Initialize selected variables on current date.
        selectedMonth = date.getMonth() + 1;            // Date class uses zero indexing for months... Add 1.
        selectedYear = date.getYear() + 1900;           // need to add 1900 to get current year... for reasons.
        dayIsPartOfCurrentMonth = new boolean[5][7];    // Used for assigning box colours.
        dayBoxNums = new int[5][7];
        updateScene();
    }

    // Method Name: start
    // Parameters:  none
    // Behaviour:   default javafx method.
    // Returns:     void
    public void start() {
        try {                                   // load .fxml to the layout.
            layout = FXMLLoader.load(getClass().getClassLoader().getResource("FXML/calenderPane.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        window = new Stage();                   // Initialize the stage and scene.
        scene = new Scene(layout);
        window.setScene(scene);
        window.setResizable(false);
        window.setTitle("Calender View");
        window.show();
    }

    // ---------------------------------- HANDLER METHODS ------------------------------------

    // Method Name: dayBoxHandler
    // Parameters:  none
    // Behaviour:   Sets the functionality of each dayBox.
    //                                                                  *** Needs to be added to
    // Returns:     void
    public void dayBoxHandler(MouseEvent event) {
        int row, col;
        String source = event.getSource().toString();
        System.out.println(source);
        row = getDayBoxRow(source);
        col = getDayBoxCol(source);
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

    // Method Name: prevMonth
    // Parameters:  ActionEvent event
    // Behaviour:   Changes the view so to display the previous month.
    // Returns:     void
    public void prevMonth(ActionEvent event) {
        System.out.println("Button pressed: Previous Month");
        if (selectedMonth > 1) {
            selectedMonth--;
        } else {
            selectedMonth = 12;
            selectedYear--;
        }
        updateScene();
    }

    // Method Name: nextMonth
    // Parameters:  ActionEvent event
    // Behaviour:   Changes the view so to display the next month.
    // Returns:     void
    public void nextMonth(ActionEvent event) {
        System.out.println("Button pressed: Next Month");
        if (selectedMonth < 12) {
            selectedMonth++;
        } else {
            selectedMonth = 1;
            selectedYear++;
        }
        updateScene();
    }

    // ---------------------------------- OTHER METHODS -------------------------------------

    // Method Name: updateScene
    // Parameters:  none
    // Behaviour:   Updates the values for the numbers on the scene as well as internally.
    //              Updates the labels for the month and year.
    //              Assigns and updates the dayBox colour.
    // Returns:     void
    private void updateScene() {
        lblMonthYear.setText(Month.values()[selectedMonth].toString() + " " + Integer.toString(selectedYear));
        assignNumbers();
        updateNumbers();
        assignOnOffDays();
        setDayBoxColour("beige", "white");
    }

    // Method Name: assignOnOffdays
    // Parameters:  none
    // Behaviour:   Assigns a values in boolean[][] dayIsPartOfCurrentMonth.
    //              Must be called after assignNumbers() and before setDayBoxColour().
    // Returns:     void
    private void assignOnOffDays() {
        int row, col;
        boolean beginCurrentMonth = false;          // Flag for setting current month colour.
        boolean endCurrentMonth = false;            // Flag for ending current month colour.
        int dayIndex = 1;
        int lastDayInCurrentMonth = getDaysInMonth(this.selectedMonth, this.selectedYear);
        for(row = 0; row < 5; row++) {
            for(col = 0; col < 7; col++) {
                if(this.dayBoxNums[row][col] == 1) {
                    beginCurrentMonth = true;
                }
                if (dayIndex > lastDayInCurrentMonth) {
                    endCurrentMonth = true;
                }
                if((beginCurrentMonth) && !(endCurrentMonth)) {
                    this.dayIsPartOfCurrentMonth[row][col] = true;
                    dayIndex++;
                } else {
                    this.dayIsPartOfCurrentMonth[row][col] = false;
                }
            }
        }
    }

    // Method Name: setDayBoxColour
    // Parameters:  String offColour, String onColour
    // Behaviour:   Sets the colour of the dayBoxs so that the "off" boxes have a distinct colour from the "on" boxes.
    // returns:     void
    private void setDayBoxColour(String offColour, String onColour) {
        String colour;
        int row, col;
        for(row = 1; row <= 5; row++) {
            for(col = 1; col <= 7; col++) {
                colour = "-fx-background-color: ";
                if (this.dayIsPartOfCurrentMonth[row - 1][col - 1]) {
                    colour += onColour;
                } else {
                    colour += offColour;
                }
                if (row == 1) {
                    if (col == 1) { dayBox_1_1.setStyle(colour);}
                    if (col == 2) { dayBox_1_2.setStyle(colour);}
                    if (col == 3) { dayBox_1_3.setStyle(colour);}
                    if (col == 4) { dayBox_1_4.setStyle(colour);}
                    if (col == 5) { dayBox_1_5.setStyle(colour);}
                    if (col == 6) { dayBox_1_6.setStyle(colour);}
                    if (col == 7) { dayBox_1_7.setStyle(colour);}
                }
                if (row == 2) {
                    if (col == 1) { dayBox_2_1.setStyle(colour);}
                    if (col == 2) { dayBox_2_2.setStyle(colour);}
                    if (col == 3) { dayBox_2_3.setStyle(colour);}
                    if (col == 4) { dayBox_2_4.setStyle(colour);}
                    if (col == 5) { dayBox_2_5.setStyle(colour);}
                    if (col == 6) { dayBox_2_6.setStyle(colour);}
                    if (col == 7) { dayBox_2_7.setStyle(colour);}
                }
                if (row == 3) {
                    if (col == 1) { dayBox_3_1.setStyle(colour);}
                    if (col == 2) { dayBox_3_2.setStyle(colour);}
                    if (col == 3) { dayBox_3_3.setStyle(colour);}
                    if (col == 4) { dayBox_3_4.setStyle(colour);}
                    if (col == 5) { dayBox_3_5.setStyle(colour);}
                    if (col == 6) { dayBox_3_6.setStyle(colour);}
                    if (col == 7) { dayBox_3_7.setStyle(colour);}
                }
                if (row == 4) {
                    if (col == 1) { dayBox_4_1.setStyle(colour);}
                    if (col == 2) { dayBox_4_2.setStyle(colour);}
                    if (col == 3) { dayBox_4_3.setStyle(colour);}
                    if (col == 4) { dayBox_4_4.setStyle(colour);}
                    if (col == 5) { dayBox_4_5.setStyle(colour);}
                    if (col == 6) { dayBox_4_6.setStyle(colour);}
                    if (col == 7) { dayBox_4_7.setStyle(colour);}
                }
                if (row == 5) {
                    if (col == 1) { dayBox_5_1.setStyle(colour);}
                    if (col == 2) { dayBox_5_2.setStyle(colour);}
                    if (col == 3) { dayBox_5_3.setStyle(colour);}
                    if (col == 4) { dayBox_5_4.setStyle(colour);}
                    if (col == 5) { dayBox_5_5.setStyle(colour);}
                    if (col == 6) { dayBox_5_6.setStyle(colour);}
                    if (col == 7) { dayBox_5_7.setStyle(colour);}
                }
            }
        }
    }

    // Method Name: updateNumbers
    // Parameters:  none
    // Behaviour:   Updates the values for the numbers on the scene after assignNumbers() has been called.
    //              It's ugly but it gets the job done.
    // Returns:     void
    private void updateNumbers() {
        int row, col;
        for(row = 1; row <= 5; row++) {
            for(col = 1; col <= 7; col++) {
                if (row == 1) {
                    if (col == 1) { boxNum_1_1.setText(Integer.toString(dayBoxNums[row - 1][col - 1]));}
                    if (col == 2) { boxNum_1_2.setText(Integer.toString(dayBoxNums[row - 1][col - 1]));}
                    if (col == 3) { boxNum_1_3.setText(Integer.toString(dayBoxNums[row - 1][col - 1]));}
                    if (col == 4) { boxNum_1_4.setText(Integer.toString(dayBoxNums[row - 1][col - 1]));}
                    if (col == 5) { boxNum_1_5.setText(Integer.toString(dayBoxNums[row - 1][col - 1]));}
                    if (col == 6) { boxNum_1_6.setText(Integer.toString(dayBoxNums[row - 1][col - 1]));}
                    if (col == 7) { boxNum_1_7.setText(Integer.toString(dayBoxNums[row - 1][col - 1]));}
                }
                if (row == 2) {
                    if (col == 1) { boxNum_2_1.setText(Integer.toString(dayBoxNums[row - 1][col - 1]));}
                    if (col == 2) { boxNum_2_2.setText(Integer.toString(dayBoxNums[row - 1][col - 1]));}
                    if (col == 3) { boxNum_2_3.setText(Integer.toString(dayBoxNums[row - 1][col - 1]));}
                    if (col == 4) { boxNum_2_4.setText(Integer.toString(dayBoxNums[row - 1][col - 1]));}
                    if (col == 5) { boxNum_2_5.setText(Integer.toString(dayBoxNums[row - 1][col - 1]));}
                    if (col == 6) { boxNum_2_6.setText(Integer.toString(dayBoxNums[row - 1][col - 1]));}
                    if (col == 7) { boxNum_2_7.setText(Integer.toString(dayBoxNums[row - 1][col - 1]));}
                }
                if (row == 3) {
                    if (col == 1) { boxNum_3_1.setText(Integer.toString(dayBoxNums[row - 1][col - 1]));}
                    if (col == 2) { boxNum_3_2.setText(Integer.toString(dayBoxNums[row - 1][col - 1]));}
                    if (col == 3) { boxNum_3_3.setText(Integer.toString(dayBoxNums[row - 1][col - 1]));}
                    if (col == 4) { boxNum_3_4.setText(Integer.toString(dayBoxNums[row - 1][col - 1]));}
                    if (col == 5) { boxNum_3_5.setText(Integer.toString(dayBoxNums[row - 1][col - 1]));}
                    if (col == 6) { boxNum_3_6.setText(Integer.toString(dayBoxNums[row - 1][col - 1]));}
                    if (col == 7) { boxNum_3_7.setText(Integer.toString(dayBoxNums[row - 1][col - 1]));}
                }
                if (row == 4) {
                    if (col == 1) { boxNum_4_1.setText(Integer.toString(dayBoxNums[row - 1][col - 1]));}
                    if (col == 2) { boxNum_4_2.setText(Integer.toString(dayBoxNums[row - 1][col - 1]));}
                    if (col == 3) { boxNum_4_3.setText(Integer.toString(dayBoxNums[row - 1][col - 1]));}
                    if (col == 4) { boxNum_4_4.setText(Integer.toString(dayBoxNums[row - 1][col - 1]));}
                    if (col == 5) { boxNum_4_5.setText(Integer.toString(dayBoxNums[row - 1][col - 1]));}
                    if (col == 6) { boxNum_4_6.setText(Integer.toString(dayBoxNums[row - 1][col - 1]));}
                    if (col == 7) { boxNum_4_7.setText(Integer.toString(dayBoxNums[row - 1][col - 1]));}
                }
                if (row == 5) {
                    if (col == 1) { boxNum_5_1.setText(Integer.toString(dayBoxNums[row - 1][col - 1]));}
                    if (col == 2) { boxNum_5_2.setText(Integer.toString(dayBoxNums[row - 1][col - 1]));}
                    if (col == 3) { boxNum_5_3.setText(Integer.toString(dayBoxNums[row - 1][col - 1]));}
                    if (col == 4) { boxNum_5_4.setText(Integer.toString(dayBoxNums[row - 1][col - 1]));}
                    if (col == 5) { boxNum_5_5.setText(Integer.toString(dayBoxNums[row - 1][col - 1]));}
                    if (col == 6) { boxNum_5_6.setText(Integer.toString(dayBoxNums[row - 1][col - 1]));}
                    if (col == 7) { boxNum_5_7.setText(Integer.toString(dayBoxNums[row - 1][col - 1]));}
                }
            }
        }
    }

    // Method Name: assignNumbers
    // Parameters:  none
    // Behaviour:   Dynamically assigns integers into int[][] dayBoxNums based on class variables selectedMonth and selectedYear.
    // Returns:     void
    private void assignNumbers() {
        int month = selectedMonth;
        int year = selectedYear;
        int prevMonth = month;
        int yearOfPrevMonth = year;
        int startingRow = getDayOfWeekFor1stOfMonth(month, year) - 1;   // 0 -> 6 for array.
        int lastNum = getDaysInMonth(month, year);
        if (month > 1) {
            prevMonth--;
        } else {
            prevMonth = 12;
            yearOfPrevMonth--;
        }
        int lastDayOfPrevMonth = getDaysInMonth(prevMonth, yearOfPrevMonth);
        int row, col, numIndex = 1;
        String temp;
        for(row = 0; row < 5; row++) {
            temp = "";                                      // Temp
            for(col = 0; col < 7; col++) {
                if ((row == 0) && (col < startingRow)) {
                    dayBoxNums[row][col] = lastDayOfPrevMonth - (startingRow - 1 - col);
                } else {
                    dayBoxNums[row][col] = numIndex;
                    numIndex++;
                    if (numIndex > lastNum) {   // Start storing numbers for the next month.
                        numIndex = 1;
                    }
                }
                temp += "[" + dayBoxNums[row][col] + "]";   // Temp
            }
            System.out.println(temp);                       // Temp
        }
    }

    // Method Name: getDayBoxRow
    // Parameters:  String source
    // Behaviour:   Given the string from an event.getSource() passed by a dayBox, return the row (int) within the id within the string.
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
            System.out.println("invalid source string");
            return -1;                              // Invalid given string returns -1.
        }
    }

    // Method Name: getDayBoxCol
    // Parameters:  String source
    // Behaviour:   Given the string from an event.getSource() passed by a dayBox, return the col (int) within the id within the string.
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
            System.out.println("invalid source string");
            return -1;                              // Invalid given string returns -1.
        }
    }

    // Method Name: getDaysInMonth
    // Parameters:  int month, int year
    // Behaviour:   Returns the number of days in the month given the month and year.
    //              Accounts for leap years.
    // Returns:     int
    private int getDaysInMonth(int month, int year) {
        if ((month < 1) || (month > 12)) {
            System.out.println("invalid month");
            return -1;
        }
        if (month == 2) {
            if (year % 4 == 0) {                // Is a leap year.
                return 29;
            } else {
                return 28;
            }
        } else if ((month == 1) || (month == 3) || (month == 5) || (month == 7) || (month == 8) || (month == 10) || (month == 12)) {
            return 31;
        } else {                // 4, 6, 9, 11
            return 30;
        }
    }

    // Method Name: getDayOfWeekFor1stOfMonth
    // Parameters:  int month, int year
    // Behaviour:   Returns an int representing the day of the week of the first day in the given month/year.
    //              Starts with sunday -> 1, ends with saturday -> 7.
    private int getDayOfWeekFor1stOfMonth(int month, int year) {
        if ((month < 1) || (month > 12)) {
            System.out.println("invalid month");
            return -1;
        }
        // based on sunday april 1st 2018.
        // Count offset # of days between given month/year and based date.
        int offSet = 0;
        int dayOffSet;
        boolean addAprilFlag;   // Whether or not to include the days in april 2018 within calculations.
        // If the given month is > april 2018, decrement the month value by 1 so as to not count an extra month of days.
        // If given month was <= april, will not count all days in april.
        if (((year == 2018) && (month > 4)) || (year > 2018)) {
            if (month > 1) {
                month--;
            } else {
                year--;
                month = 12;
            }
            addAprilFlag = true;
        } else {
            addAprilFlag = false;
        }
        while (!((month == 4) && (year == 2018))) {
            if (year > 2018) {
                offSet += getDaysInMonth(month, year);
                if (month > 1) {
                    month--;
                } else {
                    year--;
                    month = 12;
                }
            } else if (year < 2018) {
                offSet += getDaysInMonth(month, year);
                if (month < 12) {
                    month++;
                } else {
                    year++;
                    month = 1;
                }
            } else { // year == 2018.
                if (month > 4) {
                    //addAprilFlag = true;
                    offSet += getDaysInMonth(month, year);
                    month--;
                } else if (month < 4) {
                    //addAprilFlag = false;
                    offSet += getDaysInMonth(month, year);
                    month++;
                }
            }
        }
        if (addAprilFlag) {
            offSet += getDaysInMonth(month, year);  // Add days in April 2018.
            // (offSet % 7) is the number of days to shift right from sunday april 1st.
            // lowest value will be 1 (denoting no shift), which denotes sunday.
            dayOffSet = 1 + (offSet % 7);   // Add 1 to make values 1 -> 7 instead of 0 -> 6.
        } else {
            // (offSet % 7) is the number of days we need to shift left from Sunday april 1st.
            // Because sunday is the first in its week, it is technically the 8th collumn in the previous week.
            // If no shift, we will have 8, and thus another sunday (which needs to be 1).
            dayOffSet = 8 - (offSet % 7);
            if (dayOffSet == 8) {
                dayOffSet = 1;
            }
        }
        return dayOffSet;
    }
}
