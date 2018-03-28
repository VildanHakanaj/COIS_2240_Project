package LoginPane;

import CalendarPane.CalendarPaneController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.xml.validation.Validator;
import java.util.Hashtable;

public class LoginController {

    // Uncomment when projects are combined
    CalendarPaneController calendarPaneController = new CalendarPaneController();

    @FXML
    private Button btLogin;

    @FXML
    private TextField username1;

    @FXML
    private PasswordField password1;

    public void initialize(){

    }

    public void start() throws Exception{
        TabPane grid = FXMLLoader.load(getClass().getResource("login.fxml"));
//        grid.getStylesheets().add("/LoginPane/StyleSheet.css");

        Stage stage = new Stage();

        stage.setTitle("Login");
        stage.setScene( new Scene(grid));
        stage.initStyle(StageStyle.UNDECORATED);
        stage.getIcons().addAll(new Image("/Photos/6.jpg"));

        stage.show();
    }

    public void login(){
        MyValidation validator = new MyValidation();
        System.out.println("Login button was pressed");

        System.out.println(username1.getText());
        System.out.println(password1.getText());
        Hashtable<String, String> user = new Hashtable<String, String>();
        String uid = username1.getText();
        String pwd = password1.getText();
        //Validate the login
        Hashtable<String, String> errors = validator.validateUserLogin(uid, pwd);

        if(errors.containsKey("error")){
            System.out.println(errors.get("error"));
        }
//         Uncomment when combined with rest of project
        if(errors.size() == 0){
            try {
                calendarPaneController.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
//Only if there is no errors
//         closes stage after pressing the button

            Stage stage = (Stage) btLogin.getScene().getWindow();
            stage.close();
        }
    }

}
