package Classes;

import sun.plugin2.message.Message;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyValidation {
    //Hashtable for the error key valu pair
    private Hashtable<String, String> errors;
    private final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    //MyValidation constructor will initialize the
    public MyValidation(){
        errors = new Hashtable<String, String>();
        errors.clear();
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

    //Validate the userlogin
    public Hashtable validateUserLogin(String username, String password) throws NoSuchAlgorithmException {
        errors.clear();
        String user = "Vildan";
        String pwd = "password";
        return errors;
    }

    public Hashtable validateNewUser(Hashtable user){
        errors.clear();
        String emptyError = "Can't be empty!";

        String name = String.valueOf(user.get("name"));
        String email = String.valueOf(user.get("email"));
        String username = String.valueOf(user.get("uid"));
        String pwd = String.valueOf(user.get("pwd"));

        if(name.trim().isEmpty()){
            errors.put("error", "Name field cannot be empty");
        } else if(email.trim().isEmpty()){
            errors.put("error", "Email field cannot be empty");
        } else if(!validateEmail(email)){
            errors.put("error", "Enter valid email");
        } else if(username.trim().isEmpty()){
            errors.put("error", "Username field cannot be empty");
        } else if(pwd.trim().isEmpty()){
            errors.put("error", "Password cannot be empty");
        }else if(pwd.length() < 8){
            errors.put("error", "Password needs to be at least 8 characters long");
        }
        return errors;
    }

    //Validates if the email is the correct format.
    public boolean validateEmail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
        return matcher.find();
    }

}
