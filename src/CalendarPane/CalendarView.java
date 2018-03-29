package CalendarPane;


import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.css.PseudoClass;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.time.temporal.WeekFields;
import java.util.Locale;

public class CalendarView {
    private final ObjectProperty<YearMonth> month = new SimpleObjectProperty<>();

    private final ObjectProperty<Locale> locale = new SimpleObjectProperty<>(Locale.getDefault());

    private final BorderPane view ;
    private final GridPane calendar ;

    public CalendarView(YearMonth month) {
        view = new BorderPane();
        view.getStyleClass().add("calendar");
        calendar = new GridPane();
        calendar.getStyleClass().add("calendar-grid");

        Label header = new Label();
        header.setMaxWidth(Double.MAX_VALUE);
        header.getStyleClass().add("calendar-header");

        this.month.addListener((obs, oldMonth, newMonth) ->
                rebuildCalendar());

        this.locale.addListener((obs, oldLocale, newLocale) ->
                rebuildCalendar());

        view.setTop(header);
        view.setCenter(calendar);

        view.getStylesheets().add(getClass().getResource("/CalendarPane/StyleSheet.css").toExternalForm());

        setMonth(month);

        header.textProperty().bind(Bindings.createStringBinding(() ->
                        this.month.get().format(DateTimeFormatter.ofPattern("MMMM yyyy", locale.get())),
                this.month, this.locale));
    }

    public CalendarView() {
        this(YearMonth.now()) ;
    }

    public void nextMonth() {
        month.set(month.get().plusMonths(1));
    }

    public void previousMonth() {
        month.set(month.get().minusMonths(1));
    }

    private void rebuildCalendar() {

        calendar.getChildren().clear();

        WeekFields weekFields = WeekFields.of(locale.get());

        LocalDate first = month.get().atDay(1);

        int dayOfWeekOfFirst = first.get(weekFields.dayOfWeek()) ;

        // column headers:
        for (int dayOfWeek = 1 ; dayOfWeek <= 7 ; dayOfWeek++) {
            LocalDate date = first.minusDays(dayOfWeekOfFirst - dayOfWeek);
            DayOfWeek day = date.getDayOfWeek() ;
            Label label = new Label(day.getDisplayName(TextStyle.SHORT_STANDALONE, locale.get()));
            label.getStyleClass().add("calendar-day-header");
            GridPane.setHalignment(label, HPos.CENTER);
            calendar.add(label, dayOfWeek - 1, 0);
        }

        LocalDate firstDisplayedDate = first.minusDays(dayOfWeekOfFirst - 1);
        LocalDate last = month.get().atEndOfMonth() ;
        int dayOfWeekOfLast = last.get(weekFields.dayOfWeek());
        LocalDate lastDisplayedDate = last.plusDays(7 - dayOfWeekOfLast);

        PseudoClass beforeMonth = PseudoClass.getPseudoClass("before-display-month");
        PseudoClass afterMonth = PseudoClass.getPseudoClass("after-display-month");

        for (LocalDate date = firstDisplayedDate ; ! date.isAfter(lastDisplayedDate) ; date = date.plusDays(1)) {
            Label label = new Label(String.valueOf(date.getDayOfMonth()));


            // create a button for an event and set text
            Button bt = new Button();
            bt.setText(String.valueOf(date));
            //


            //
            GridPane grid = new GridPane();
            grid.setMinWidth(150);
            grid.setMinHeight(150);
            grid.setGridLinesVisible(true);
            //

            // CREATE HANDLER TO OPEN NEW DayPane WINDOW
            LocalDate finalDate = date;
            grid.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                System.out.println("This pane's date is: " + finalDate);

                // IMPORT FXML CODE
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/DayPane/DayPane.fxml"));
                Parent root = null;
                try {
                    root = (Parent) fxmlLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                root.getStylesheets().add("/DayPane/StyleSheet.css");




                // WINDOW
                Stage eventStage = new Stage();
                Scene eventScene = new Scene(root);
                //eventScene.getStylesheets().add("/NewEvent/StyleSheet.css");

                eventStage.initStyle(StageStyle.UNDECORATED);
                Image image = new Image("/Photos/6.jpg");
                eventStage.getIcons().addAll(image);


                eventStage.setScene(eventScene);
                eventStage.setTitle("New Event");
                eventStage.show();


                /*FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/DayPane/DayPane"));
                Parent root = null;

                //root.getStylesheets().add("/DayPane/StyleSheet.css");

                Stage dayStage = new Stage();
                Scene dayScene = new Scene(root);
                dayScene.getStylesheets().add("/DayPane/StyleSheet.css");

                dayStage.initStyle(StageStyle.UNDECORATED);
                Image image = new Image("/Photos/6.jpg");
                dayStage.getIcons().addAll(image);

                dayStage.setScene(dayScene);
                dayStage.setTitle("Day View");
                dayStage.show();*/
            });

            label.getStyleClass().add("calendar-cell");
            label.pseudoClassStateChanged(beforeMonth, date.isBefore(first));
            label.pseudoClassStateChanged(afterMonth, date.isAfter(last));

            GridPane.setHalignment(label, HPos.LEFT);


            int dayOfWeek = date.get(weekFields.dayOfWeek()) ;
            int daysSinceFirstDisplayed = (int) firstDisplayedDate.until(date, ChronoUnit.DAYS);
            int weeksSinceFirstDisplayed = daysSinceFirstDisplayed / 7 ;


            //
            // set date to compare to
            LocalDate a = LocalDate.of(2018,03,22);

            // compare to the date and add the event button
            if (date.equals(a)){
                grid.add(bt,0,1);
            }
            //


            grid.add(label, 0,0);
            calendar.add(grid, dayOfWeek - 1, weeksSinceFirstDisplayed + 1);
        }
    }

    public Node getView() {
        return view ;
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