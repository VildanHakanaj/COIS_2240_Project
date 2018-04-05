package Controllers;

import Classes.Database;
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


    public void initialize() throws SQLException {
        Database db = new Database();
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
        MyValidation validator = new MyValidation();
        Hashtable<String, String> user = new Hashtable<String, String>();


        String uid = username1.getText();
        String pwd = password1.getText();

        //Validate the login
        try {
            //Validate the login
            Hashtable<String, String> errors = validator.validateUserLogin(uid, pwd);
            if(errors.size() == 0){
                try {
                    dayPaneController.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //closes stage after pressing the button
                Stage stage = (Stage) username1.getScene().getWindow();
                stage.close();
            }else{
                System.out.println(errors.get("error"));
                err.setText(errors.get("error"));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
    public void signUp() throws SQLException {
        MyValidation validator = new MyValidation();
        //Grab the values from the form
        Hashtable user = new Hashtable();
        user.put("name",name.getText());
        user.put("email",email.getText());
        user.put("uid",username2.getText());
        user.put("pwd",password2.getText());

        Hashtable<String, String> errors = validator.validateNewUser(user);

        if (errors.containsKey("error")) {
            System.out.println(errors.get("error"));
            err2.setText(errors.get("error"));
        }

        if (errors.size() == 0) {

            try {
                db.insertUser(user);
            } catch (SQLException e) {
                System.out.println("Something went wrong: " + e.getMessage());
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
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

    public String getPass(String uid){
//check if username exists and corresponds to an email
        String pass = null;
        try {
            String url = "jdbc:sqlite:src/data.db";
            Connection conn = DriverManager.getConnection(url);

            Statement statement = conn.createStatement();
            ResultSet r = statement.executeQuery("SELECT * FROM Login WHERE Username='"+uid+"'");
            pass = r.getString("Password");

            statement.close();
            conn.close();
            return pass;
        } catch (SQLException e) {

            System.out.println("Something went wrong: " + e.getMessage());
        }finally{
            return pass;
        }
    }
}
