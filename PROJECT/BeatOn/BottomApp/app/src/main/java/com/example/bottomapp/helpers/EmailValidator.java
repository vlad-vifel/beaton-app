package com.example.bottomapp.helpers;

import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Java email validation program
 *
 * @author pankaj
 *
 */
public class EmailValidator {
    // Email Regex java
    private static final String EMAIL_REGEX = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";

    // static Pattern object, since pattern is fixed
    private static Pattern pattern;

    private Databaser databaser = new Databaser();
    public boolean check = false;
    // non-static Matcher object because it's created from the input String
    private Matcher matcher;

    public EmailValidator() {
        // initialize the Pattern object
        pattern = Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE);
    }

    public boolean validateEmail(String email) {
        matcher = pattern.matcher(email);
        return matcher.matches();
    }



    public boolean repeatEmail (String email) {
        return true;
    }

    public boolean validatePass (String pass) {
        if (pass.length() >= 8) { return true;}
        else { return false;}
    }

    public boolean validateLogin (String login) {
        return (login.toLowerCase().matches("[a-z]+"));
    }
}
