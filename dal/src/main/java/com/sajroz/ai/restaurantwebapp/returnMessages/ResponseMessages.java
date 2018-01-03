package com.sajroz.ai.restaurantwebapp.returnMessages;

public enum ResponseMessages {

    USER_REGISTERED(3, "Registration successful."),
    USER_UPDATED(2, "User updated."),
    IMAGE_SAVED(1, "Image saved successfully."),
    OK(0, "Request completed with no errors."),
    ACCESS_DENIED(403, "Forbidden, bad authorities, access denied"),
    LOGIN_REQUIRED(401, "Unauthorized, login required"),
    NO_USER(-1, "User doesn't exist."),
    DUPLICATE_EMAIL(-2, "Email is taken. Try another."),
    NO_FILE(-3, "No file found."),
    BASE64_ERROR(-4, "Wrong encode of base64."),
    IMAGE_SAVING_ERROR(-5, "Image saving failure."),
    EMPTY_IMAGE_DATA(-6, "No data to save or data is corrupted."),
    ACCESS_TO_USER_ERROR(-7, "You try to access data of different user."),
    LOGIN_FILED(-8, "Login failed, user doesn't exist."),
    DUPLICATE_MEAL(-9, "Meal is already exist."),
    NO_MEAL(-10, "Meal doesn't exist."),
    DUPLICATE_TABLE(-11, "Table already exists."),
    NO_TABLE(-12, "Table doesn't exist."),
    TABLE_OCCUPIED(-13, "Table is occupied."),
    MISSING_DATA(-14, "Missing data."),
    NO_TABLE_RESERVATION(-15, "Table reservation doesn't exist."),
    NO_MEAL_CATEGORY(-16, "Meal category doesn't exists."),
    NO_RESERVATION(-17, "No reservation in database."),
    BAD_DATE_FORMAT(-18, "Incorrect date format."),
    RESTAURANT_OCCUPIED(-19, "Restaurant is occupied"),
    NO_RESTAURANT_RESERVATION(-20, "Restaurant reservation doesn't exist.");

    private final int code;
    private final String description;

    ResponseMessages(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String toString() {
        return code + ": " + description;
    }
}