package Controllers;

import Classes.Database;
import Classes.Event;
import Classes.MyValidation;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.Hashtable;
import java.util.WeakHashMap;

public class LoginController {

    // Uncomment when projects are combined
    CalenderPaneController calendarPaneController;
    private Database db = new Database();

    @FXML
    private Button btLogin;

    @FXML
    private Button btSign;

    @FXML
    private Label err;

    @FXML
    private Label err2;

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

    public LoginController() throws SQLException{

    }


    public void initialize(){
        calendarPaneController = new CalenderPaneController();  // Init Calendar Pane.
    }

    public void start() throws Exception {
        TabPane grid = FXMLLoader.load(getClass().getResource("/FXML/login.fxml"));
        grid.getStylesheets().add("Stylesheets/login.css");

        Stage stage = new Stage();

        stage.setTitle("Login");
        stage.setScene(new Scene(grid));
        stage.setResizable(false);
        stage.getIcons().addAll(new Image("/Photos/6.jpg"));

        stage.show();
    }

    public void login() throws SQLException {
        //Validator
        MyValidation validator = new MyValidation();
        //I used a hashtable because of the easy storage and the ability of key value pair
        Hashtable<String, String> user = new Hashtable<String, String>();

        //Get the values from the form
        String username = username1.getText();
        String password = password1.getText();

        //Validate the login
        try {
            //Validate the login
            Hashtable<String, String> errors = validator.validateUserLogin(username, password);
            //If there is noerrors then go ahead and let the user in.
            if(errors.size() == 0){

                //Need to open another pane after the user is logged in.
                System.out.println("Welcome back " + username);
                // Open Calendar Pane.
                calendarPaneController.start();
                //closes stage after pressing the button
                Stage stage = (Stage) username1.getScene().getWindow();
                stage.close();
            }else{
                //Print the errors if there was one
                System.out.println(errors.get("error"));
                err.setText(errors.get("error"));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    //The sign up process
    public void signUp() throws SQLException {
        //Validation
        MyValidation validator = new MyValidation();
        //Grab the values from the form and store them in the hashtable
        Hashtable user = new Hashtable();
        user.put("name", name.getText());
        user.put("email", email.getText());
        user.put("uid", username2.getText());
        user.put("pwd", password2.getText());

        //Validate and get back the errors array;
        Hashtable<String, String> errors = validator.validateNewUser(user);
        if (errors.size() == 0) {
            try {
                db.insertUser(user);
                System.out.println("Welcome to our agenda app " + user.get("uid"));
            } catch (SQLException e) {
                System.out.println("Something went wrong: " + e.getMessage());
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }

            // Open Calendar Pane.
            calendarPaneController.start();

            //closes stage after pressing the button
            Stage stage = (Stage) btSign.getScene().getWindow();
            stage.close();
        }else{
            //Print the errors
            System.out.println(errors.get("error"));
            err2.setText(errors.get("error"));
        }
    }
}
