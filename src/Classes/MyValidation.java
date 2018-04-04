package Classes;

import sun.plugin2.message.Message;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyValidation {
    //Hashtable for the error key valu pair
    private Hashtable<String, String> errors;
    //The patter for the email validation
    private final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    //MyValidation constructor will initialize the
    public MyValidation(){
        errors = new Hashtable<String, String>();
        errors.clear();
    }

    //Validate the user login
    public Hashtable validateUserLogin(String username, String password) throws NoSuchAlgorithmException, SQLException {
        errors.clear();
       try{
           Database db = new Database(); //Open the database

            String empty = " can't be empty";
            String error = "Invalid username or password";

            ResultSet rs = db.selectUserByUsername(username);
    //        String user = "Vildan";
    //        String pwd = "password";

            String hash = hashPassword(password);

            if(username.trim().equals("")){
                errors.put("error", "Name" + empty);
            }else if(!username.equals(rs.getString("username"))){
                errors.put("error", error);
            }

            if(password.trim().equals("")){
                errors.put("error", "Password" + empty);
            }else if(!hash.equals(rs.getString("password"))){
                errors.put("error", error);
            }
       }catch(SQLException e){
            e.printStackTrace();
       }
        return errors;
    }

//    public Hashtable validateNewUser(Hashtable user){
//        errors.clear();
//        String emptyError = "Field Cannot be empty";
//        String name = String.valueOf(user.get("name"));
//        String email = String.valueOf(user.get("email"));
//        String username = String.valueOf(user.get("uid"));
//        String pwd = String.valueOf(user.get("pwd"));
//
//        if(name.trim().isEmpty()){
//            errors.put("error", "Name " + emptyError);
//        } else if(email.trim().isEmpty()){
//            errors.put("error", "Email" + emptyError);
//        } else if(!validateEmail(email)){
//            errors.put("error", "Enter valid email");
//        } else if(username.trim().isEmpty()){
//            errors.put("error", "Username field " + emptyError);
//        } else if(pwd.trim().isEmpty()){
//            errors.put("error", "Password " + emptyError);
//        }else if(pwd.length() < 8){
//            errors.put("error", "Password needs to be at least 8 characters long");
//        }
//        return errors;
//    }

    //Validates if the email is the correct format.
    private boolean validateEmail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
        return matcher.find();
    }


    //This function will hash the password that you get from the user in the signup form
    public static String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        byte[] b = md.digest();
        StringBuffer sb = new StringBuffer();
        for(byte b1 : b ){
            sb.append(Integer.toHexString(b1 & 0xff).toString());
        }
        return sb.toString(); //Return the hashed password
    }

}
