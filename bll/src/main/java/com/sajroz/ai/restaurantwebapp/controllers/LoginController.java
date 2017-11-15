package com.sajroz.ai.restaurantwebapp.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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
    public @ResponseBody String registration(@RequestParam String email,
                        @RequestParam String password,
                        @RequestParam String username,
                        @RequestParam String surname,
                        @RequestParam(required = false) int phone,
                        @RequestParam(required = false) byte[] user_image ) {
        logger.debug("registration, email={}", email);
        if (userService.getUserByEmail(email) != null) {
            logger.debug("registration faild - user already exist, email={}", email);
            return "user already exist";
        } else {
            logger.debug("saving user to database, email={}", email);
            UserDao user = new UserDao();
            user.setEmail(email);
            user.setUsername(username);
            user.setSurname(surname);
            user.setPassword(password);
            user.setPhone(phone);
            user.setImage(user_image);
            user.setRole("USER");
            userService.insertUserToDatabase(user);
            return "register successful";
        }
    }
}
