package com.sajroz.ai.restaurantwebapp.validation;

import com.sajroz.ai.restaurantwebapp.dto.RestaurantReservationDto;

import java.util.regex.Pattern;

public class RestaurantReservationValidator {
    private RestaurantReservationDto restaurantReservationDto;

    public RestaurantReservationValidator(RestaurantReservationDto restaurantReservationDto) {
        this.restaurantReservationDto = restaurantReservationDto;
    }

    public String validateUser() {
        if (!checkDescribe(restaurantReservationDto.getDescribe())) {
            return "describe";
        } else {
            return null;
        }
    }

    private boolean checkDescribe(String describe) {
        if (describe == null|| "".equals(describe)) {
            return true;
        }
        String patternDescribe = "^[a-zA-ZąćęłńóśźżĄĘŁŃÓŚŹŻ0-9!:\";'.?\\s]{1,1000}$";
        return Pattern.matches(patternDescribe, describe);
    }
}
