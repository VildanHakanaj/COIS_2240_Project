import java.io.IOException;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {


        CalendarView calendarView = new CalendarView() ;

        // BUTTON TO MOVE TO NEXT MONTH
        Button next = new Button(">");
        next.setOnAction(e -> calendarView.nextMonth());

        // BUTTON TO MOVE TO PREVIOUS MONTH
        Button previous = new Button("<");
        previous.setOnAction(e -> calendarView.previousMonth());

        // BUTTON TO OPEN A CreateEvent WINDOW
        Button createEvent = new Button("Create Event");
        createEvent.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {


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
                startTimeSlider.setMaxWidth(120);

                Slider endTimeSlider = new Slider();    // CONFIGURE END TIME SLIDER

                endTimeSlider.setMax(49);   // 49 TO PREVENT GLITCH WHERE SLIDER DISAPPEARS
                endTimeSlider.setValue(0);
                endTimeSlider.setShowTickLabels(false);
                endTimeSlider.setShowTickMarks(true);
                endTimeSlider.setMajorTickUnit(12);
                endTimeSlider.setMinorTickCount(4);
                endTimeSlider.setBlockIncrement(2);
                endTimeSlider.setMaxWidth(120);

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
                        endTimeSlider.setMin(startTimeSlider.getValue());
                    }
                });



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
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/NewEvent/NewEvent.fxml"));
                GridPane grid = null;
                try {
                    grid = (GridPane) fxmlLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                grid.getStylesheets().add("/NewEvent/StyleSheet.css");

                // PRINT OFF VALUES THAT I COULDN'T FIGURE OUT HOW TO PRINT OTHERWISE
                grid.add(startTimeSlider,1,3);
                grid.add(startTimeValue,1,3);
                GridPane.setHalignment(startTimeValue, HPos.RIGHT);
                grid.add(endTimeSlider, 1,4);
                grid.add(endTimeValue,  1,4);
                GridPane.setHalignment(endTimeValue, HPos.RIGHT);


                // WINDOW
                Stage eventStage = new Stage();
                Scene eventScene = new Scene(grid);
                eventScene.getStylesheets().add("/NewEvent/StyleSheet.css");

                //eventStage.initStyle(StageStyle.UNDECORATED);
                Image image = new Image("/Photos/6.jpg");
                eventStage.getIcons().addAll(image);


                eventStage.setScene(eventScene);
                eventStage.setTitle("New Class");
                eventStage.show();
            }
        });

        // STAGE WINDOW
        BorderPane root = new BorderPane(calendarView.getView(), createEvent, next, null, previous);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image("/Photos/6.jpg"));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}