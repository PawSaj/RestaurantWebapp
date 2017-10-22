package com.sajroz.ai.restaurantwebapp.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.sajroz.ai.restaurantwebapp.model.entity.UserDao;
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

    @RequestMapping(method = RequestMethod.POST, value = "/registration")
    public String registration(@RequestParam String username, @RequestParam String password) {
        logger.debug("registration, username={}", username);
        if (userService.getUserByUsername(username) != null) {
            return "user already exist";
        } else {
            UserDao user = new UserDao();
            user.setUsername(username);
            user.setPassword(password);
            userService.insertUserToDatabase(user);
            return "register successful";
        }
    }
}
