package Controllers;

import Classes.MyValidation;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Hashtable;

public class LoginController {

    // Uncomment when projects are combined
    DayPaneController dayPaneController = new DayPaneController();

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


    public void initialize() {

    }

    public void start() throws Exception {
        TabPane grid = FXMLLoader.load(getClass().getResource("/FXML/login.fxml"));
        grid.getStylesheets().add("Stylesheets/login.css");


        Stage stage = new Stage();

        stage.setTitle("Login");
        stage.setScene(new Scene(grid));
        stage.initStyle(StageStyle.UNDECORATED);
        stage.getIcons().addAll(new Image("/Photos/6.jpg"));

        stage.show();
    }

    public void login() {
        MyValidation validator = new MyValidation();
        System.out.println("Login button was pressed");

        System.out.println(username1.getText());
        System.out.println(password1.getText());
        Hashtable<String, String> user = new Hashtable<String, String>();
        String uid = username1.getText();
        String pwd = password1.getText();
        //Validate the login
        Hashtable<String, String> errors = validator.validateUserLogin(uid, pwd);

        if (errors.containsKey("error")) {
            System.out.println(errors.get("error"));
            err.setText(errors.get("error"));
        }

        if (errors.size() == 0) {

            //check if username exists and corresponds to an email


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

        Hashtable user = new Hashtable();
        String nam = name.getText();
        String eml = email.getText();
        String uid = username2.getText();
        String pwd = password2.getText();

        user.put(1,nam);
        user.put(2,eml);
        user.put(3,uid);
        user.put(4,pwd);

        Hashtable<String, String> errors = validator.validateNewUser(user);

        if (errors.containsKey("error")) {
            System.out.println(errors.get("error"));
            err2.setText(errors.get("error"));
        }

        if (errors.size() == 0) {

            try {
                String url = "jdbc:sqlite:src/data.db";
                Connection conn = DriverManager.getConnection(url);

                Statement statement = conn.createStatement();
                statement.execute("INSERT INTO Login (Name, Email, Username, Password)" +
                        " VALUES "+"('"+user.get(1)+"','"+user.get(2)+"','"+user.get(3)+"','"+user.get(4)+"')");

                statement.close();
                conn.close();
            } catch (SQLException e) {

                System.out.println("Something went wrong: " + e.getMessage());
            }

            try {
                dayPaneController.start();
            } catch (Exception e) {
                e.printStackTrace();
            }

            //closes stage after pressing the button
            Stage stage = (Stage) btSign.getScene().getWindow();
            stage.close();
        }

    }

}
