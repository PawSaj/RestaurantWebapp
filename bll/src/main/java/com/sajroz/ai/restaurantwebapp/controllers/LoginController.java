package com.sajroz.ai.restaurantwebapp.controllers;

import com.sajroz.ai.restaurantwebapp.dto.UserDto;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.sajroz.ai.restaurantwebapp.services.UserService;

@RestController
public class LoginController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/successfulLogin")
    public String successfulLogin() {
        return "login successful";
    }

    @RequestMapping(value = "/failedLogin")
    public String failedLogin() {
        return "error";
    }

    @RequestMapping(value = "/successfulLogout")
    public String successfulLogout() {
        return "logout successful";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@RequestBody UserDto userDto) {
        logger.info("registration, user={}", userDto);
        JSONObject returnMessage = new JSONObject();
        if (userService.getUserByEmail(userDto.getEmail()) != null) {
            logger.warn("registration failed - user already exist, email={}", userDto.getEmail());

            returnMessage.put("status", -1);
            returnMessage.put("message", "Email is taken. Try another.");
            return returnMessage.toString();
        } else {
            logger.info("saving user to database, user={}", userDto);

            userDto.setRole("USER");
            userService.insertUserToDatabase(userService.mapUserFromDto(userDto));
            returnMessage.put("status", 0);
            returnMessage.put("message", "Registration successful");
            return returnMessage.toString();
        }
    }
}
