package Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Hashtable;

public class LoginController {

    // Uncomment when projects are combined
    DayPaneController dayPaneController = new DayPaneController();

    @FXML
    private Button btLogin;

    @FXML Button btSign;

    @FXML
    private TextField name;

    @FXML
    private TextField email;

    @FXML
    private TextField username1;

    @FXML
    private TextField username2;

    @FXML
    private PasswordField password1;

    @FXML
    private PasswordField password2;


    public void initialize(){

    }

    public void start() throws Exception{
        TabPane grid = FXMLLoader.load(getClass().getResource("/FXML/login.fxml"));
        grid.getStylesheets().add("Stylesheets/login.css");


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

        if(errors.size() == 0){
            try {
                dayPaneController.start();
            } catch (Exception e) {
                e.printStackTrace();
            }

            //closes stage after pressing the button
            Stage stage = (Stage) btLogin.getScene().getWindow();
            stage.close();
        }
    }

    public void signUp() {
        MyValidation validator = new MyValidation();
        System.out.println("Signup button was pressed");
        System.out.println(name.getText());
        System.out.println(email.getText());
        System.out.println(username2.getText());
        System.out.println(password2.getText());

        Hashtable<String, String> user = new Hashtable<String, String>();

        String uid = username2.getText();
        String pwd = password2.getText();


        try {
            dayPaneController.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Stage stage = (Stage) btSign.getScene().getWindow();
        stage.close();
        }
    }

