package com.sajroz.ai.restaurantwebapp.validation;

import com.sajroz.ai.restaurantwebapp.dto.UserDto;

import java.util.regex.Pattern;

public class UserValidation {
    /*private UserDto user;

    public UserValidation(UserDto user) {
        this.user = user;
    }

    public String validateUser() {
        if (checkUsername(user.getName()) == false) {
            return false;
        } else if (checkPassword(user.getPassword()) == false) {
            return false;
        } else if (checkFirstname(user.getSurname()) == false) {
            return false;
        } else if (checkLastname(user.getSurname()) == false) {
            return false;
        } else if (checkEmail(user.getEmail()) == false) {
            return false;
        } else {
            return null;
        }
    }

    private boolean checkUsername(String username) {
        String patternUsername = "^(?=.*[a-z])[\\wąćęłńóśźżĄĘŁŃÓŚŹŻ\\d]{3,20}$";
        return Pattern.matches(patternUsername, username);
    }

    private boolean checkPassword(String password) {
        String patternPassword = "^(?=.*[A-Z])(?=.*[0-9]).{6,20}$";
        return Pattern.matches(patternPassword, password);
    }

    private boolean checkFirstname(String firstname) {
        String patternFirstname = "^[A-ZŁŻ][a-ząćęłńóśźżĄĘŁŃÓŚŹŻ]{1,20}$";
        return Pattern.matches(patternFirstname, firstname);
    }

    private boolean checkLastname(String lastnamename) {
        String patternLastname = "^[A-ZĆŁŚŻŹ][a-ząćęłńóśźżĄĘŁŃÓŚŹŻ]{1,20}((-|\\\\s)?[A-ZĆŁŚŻŹ][a-ząćęłńóśźżĄĘŁŃÓŚŹŻ]{1,20})?$";
        return Pattern.matches(patternLastname, lastnamename);
    }

    private boolean checkEmail(String email) {
        String patternEmail = "^[[\\w]+(?:\\.[\\w]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}]{6,50}$";
        return Pattern.matches(patternEmail, email);
    }*/
}

