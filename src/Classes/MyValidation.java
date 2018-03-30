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
        }else if(!username.equals("Vildan") || !password.equals("password")){
            errors.put("error", "Invalid Username or Password");
        }

        return errors;
    }

    public Hashtable<String,String> validateNewUser(Hashtable<String, String> user){
        errors.clear();
        String emptyError = "Can't be empty!";
        String name = user.get("name");
        String email = user.get("email");
        String username = user.get("uid");
        String pwd = user.get("pwd");

        if(name.trim().isEmpty()){
            errors.put("name", emptyError);
        }

        if(email.trim().isEmpty()){
            errors.put("email", emptyError);
        }else if(!validateEmail(email)){
            errors.put("email", "Enter valid email");
        }

            return errors;
    }

    public boolean validateEmail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
        return matcher.find();
    }

}
