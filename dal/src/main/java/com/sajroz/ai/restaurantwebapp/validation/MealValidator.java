package com.sajroz.ai.restaurantwebapp.validation;

import com.sajroz.ai.restaurantwebapp.dto.MealDto;

import java.util.regex.Pattern;

public class MealValidator {
    private MealDto meal;

    public MealValidator(MealDto meal) {
        this.meal = meal;
    }

    public String validateUser() {
        if (!checkName(meal.getName())) {
            return "name";
        } else if (!checkImage(meal.getImage())) {
            return "image";
        } else if(!checkCategory(meal.getMealCategory().getName())) {
            return "mealCategory";
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

    private boolean checkName(String name) {
        String patternUsername = "^[a-zA-ZąćęłńóśźżĄĘŁŃÓŚŹŻ]{1,20}((-|\\s)[a-zA-ZąćęłńóśźżĄĘŁŃÓŚŹŻ]{1,20}){0,4}$";
        return Pattern.matches(patternUsername, name);
    }

    private boolean checkCategory(String category) {
        String patternCategory = "^[a-zA-ZąćęłńóśźżĄĘŁŃÓŚŹŻ]{1,20}((-|\\s)[a-zA-ZąćęłńóśźżĄĘŁŃÓŚŹŻ]{1,20}){0,4}$";
        return Pattern.matches(patternCategory, category);
    }

}
