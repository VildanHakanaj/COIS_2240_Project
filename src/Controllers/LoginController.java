package Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LoginController {

    @FXML
    private Button btLogin;

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    public void initialize(){

    }

    DayPaneController dayPaneController = new DayPaneController();

    // create and draw login screen
    public void start() throws Exception{
        GridPane grid = FXMLLoader.load(getClass().getResource("../FXML/login.fxml"));
        grid.getStylesheets().add("StyleSheet.css");

        Stage stage = new Stage();

        stage.setTitle("Login");
        stage.setScene( new Scene(grid));
        stage.initStyle(StageStyle.UNDECORATED);
        stage.getIcons().addAll(new Image("/Photos/6.jpg"));

        stage.show();
    }

    // look at ani's code. this is mostly him
    public void login(){
        System.out.println("Login button was pressed");
        System.out.println(username.getText());
        System.out.println(password.getText());

        try {
            dayPaneController.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // closes stage after pressing the button
        Stage stage = (Stage) btLogin.getScene().getWindow();
        stage.close();
    }

}
