package Classes;

import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyValidation {
    private Hashtable<String, String> errors;
    private final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);


    public MyValidation(){
        errors = new Hashtable<String, String>();
        errors.clear();
    }

    //Validate the userlogin
    public Hashtable validateUserLogin(String username, String password){
        errors.clear();
        if(username.equals("") || password.equals("")){
            errors.put("error", "Invalid Username or Password");
        }

        return errors;
    }

    public Hashtable validateNewUser(Hashtable user){
        errors.clear();
        String emptyError = "Can't be empty!";
        String name = String.valueOf(user.get(1));
        String email = String.valueOf(user.get(2));
        String username = String.valueOf(user.get(3));
        String pwd = String.valueOf(user.get(4));

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
        }

        return errors;
    }

    public boolean validateEmail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
        return matcher.find();
    }

}
