package com.sajroz.ai.restaurantwebapp.returnMessages;

public enum Messages {

    USER_REGISTERED(3, "Registration successful"),
    USER_UPDATED(2, "User updated"),
    IMAGE_SAVED(1, "Image saved successfully."),
    OK(0, "Request completed with no errors."),
    NO_USER(-1, "User doesn't exist."),
    DUPLICATE_EMAIL(-2, "Email is taken. Try another."),
    NO_FILE(-3, "No file found."),
    BASE64_ERROR(-4, "Wrong encode of base64."),
    IMAGE_SAVING_ERROR(-5, "Image saving failure."),
    EMPTY_IMAGE_DATA(-6, "No data to save or data is corrupted."),
    ACCESS_ERROR(-7, "You try to change data of different user");

    private final int code;
    private final String description;

    Messages(int code, String description) {
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