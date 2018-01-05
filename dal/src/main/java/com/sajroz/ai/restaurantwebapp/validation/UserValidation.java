package com.sajroz.ai.restaurantwebapp.validation;

import com.sajroz.ai.restaurantwebapp.dto.UserDto;

import java.util.regex.Pattern;

public class UserValidation {
    private UserDto user;

    public UserValidation(UserDto user) {
        this.user = user;
    }

    public String validateUser() {
        if (!checkEmail(user.getEmail())) {
            return "email";
        } else if (!checkPassword(user.getPassword())) {
            return "password";
        } else if (!checkName(user.getName())) {
            return "name";
        } else if (!checkSurname(user.getSurname())) {
            return "surname";
        } else if (!checkPhone(user.getPhone())) {
            return "phone";
        } else if (!checkImage(user.getImage())) {
            return "image";
        } else {
            return null;
        }
    }

    private boolean checkImage(String image) {
        if (image == null) {
            return true;
        }
        String patternImage = "^[a-zA-Z0-9-]{1,40}\\.[a-zA-Z]{1,5}$";
        return Pattern.matches(patternImage, image);
    }

    private boolean checkPhone(Integer phone) {
        if (phone == null) {
            return true;
        }
        String patternPhone = "^\\d{9}$";
        return Pattern.matches(patternPhone, phone.toString());
    }

    private boolean checkName(String name) {
        String patternUsername = "^[A-ZĆŁŚŻŹ][a-ząćęłńóśźż]{1,20}$";
        return Pattern.matches(patternUsername, name);
    }

    private boolean checkPassword(String password) {
        String patternPassword = "^(?=.*[A-Z])(?=.*[0-9]).{6,20}$";
        return Pattern.matches(patternPassword, password);
    }

    private boolean checkSurname(String surname) {
        String patternSurname = "^[A-ZĆŁŚŻŹ][a-ząćęłńóśźż]{1,20}((-|\\s)[A-ZĆŁŚŻŹ][a-ząćęłńóśźż]{1,20})?$";
        return Pattern.matches(patternSurname, surname);
    }

    private boolean checkEmail(String email) {
        String patternEmail = "^[\\w]{1,20}@[\\w]{1,10}(\\.[a-zA-Z]{1,5}){1,6}";
        return Pattern.matches(patternEmail, email);
    }
}

