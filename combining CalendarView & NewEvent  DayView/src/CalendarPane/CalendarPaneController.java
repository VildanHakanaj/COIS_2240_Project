package CalendarPane;

import DayPane.DayPaneController;
import NewEventPane.NewEventController;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class CalendarPaneController {
    final ObjectProperty<YearMonth> month = new SimpleObjectProperty<>();
    final ObjectProperty<Locale> locale = new SimpleObjectProperty<>(Locale.getDefault());
    String date = new SimpleDateFormat("MM-yyyy").format(new Date());


    CalendarView calendarView = new CalendarView();


    @FXML
    private Label title;

    @FXML
    private Label one;

    @FXML
    private BorderPane calendar;

    @FXML
    private GridPane calendarGrid;

    public CalendarPaneController() {
    }


    public void initialize(){
        title.setText(date);
    }



    public void start() throws Exception{

        BorderPane root = FXMLLoader.load(getClass().getResource("calendarPane.fxml"));
        root.getStylesheets().add("/CalendarPane/StyleSheet.css");

        Stage stage = new Stage();

        stage.setTitle("Calendar");
        stage.setScene(new Scene(root));
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.getIcons().addAll(new Image("/Photos/6.jpg"));

        stage.show();
    }

    public void next(){
        System.out.println("Next was pressed");
    }

    public void previous(){
        System.out.println("Previous was pressed");
    }


    NewEventController newEventController = new NewEventController();

    public void create(){
        System.out.println("Create was pressed");
        try {
            newEventController.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Node getView() {
        return calendar ;
    }

    public final ObjectProperty<YearMonth> monthProperty() {
        return this.month;
    }

    public final YearMonth getMonth() {
        return this.monthProperty().get();
    }

    public final void setMonth(final YearMonth month) {
        this.monthProperty().set(month);
    }

    public final ObjectProperty<Locale> localeProperty() {
        return this.locale;
    }

    public final java.util.Locale getLocale() {
        return this.localeProperty().get();
    }

    public final void setLocale(final java.util.Locale locale) {
        this.localeProperty().set(locale);
    }

}
