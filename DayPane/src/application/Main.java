package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage dayStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("dayPanel.fxml"));
        Scene scene = new Scene(root);
        dayStage.setScene(scene);
        dayStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
