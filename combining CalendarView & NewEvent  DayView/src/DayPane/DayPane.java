package DayPane;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class DayPane {

    public void start() throws Exception{

        GridPane grid = FXMLLoader.load(getClass().getResource("dayPane.fxml"));
        //grid.getStylesheets().add("/DayPane/StyleSheet.css");

        Stage eventStage = new Stage();

        eventStage.setTitle("Day Pane");
        eventStage.setScene(new Scene(grid));
        eventStage.initStyle(StageStyle.UNDECORATED);
        eventStage.getIcons().addAll(new Image("/Photos/6.jpg"));

        eventStage.show();
    }
}
