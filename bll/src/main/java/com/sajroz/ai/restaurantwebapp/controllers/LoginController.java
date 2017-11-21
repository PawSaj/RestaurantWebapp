package com.sajroz.ai.restaurantwebapp.controllers;

import com.sajroz.ai.restaurantwebapp.dto.UserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.sajroz.ai.restaurantwebapp.services.UserService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

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
    public @ResponseBody
    String registration(@RequestParam String email,
                        @RequestParam String password,
                        @RequestParam String username,
                        @RequestParam String surname,
                        @RequestParam(required = false) int phone,
                        @RequestParam(required = false) byte[] user_image) {
        logger.info("registration, email={}", email);
        if (userService.getUserByEmail(email) != null) {
            logger.warn("registration faild - user already exist, email={}", email);
            return "user already exist";
        } else {
            logger.info("saving user to database, email={}", email);
            UserDto user = new UserDto();
            user.setEmail(email);
            user.setUsername(username);
            user.setSurname(surname);
            user.setPassword(password);
            user.setPhone(phone);

            if (user_image != null) {
                String filepath = storeImage(user_image);
                if(filepath != null) {
                    logger.info("saving user image successful, email={}", email);
                    user.setImage(filepath);
                } else {
                    logger.warn("saving user image failed, email={}", email);
                    user.setImage(null);
                }
            } else {
                user.setImage(null);
            }

            user.setRole("USER");
            userService.insertUserToDatabase(userService.mapUserFromDto(user));
            return "register successful";
        }
    }

    private String storeImage(byte[] user_image) {
        String filename = UUID.randomUUID().toString();
        File file = new File(filename + ".jpg");
        while (file.isFile()) {
            filename = UUID.randomUUID().toString();
            file = new File(filename + ".jpg");
        }

        try {
            FileOutputStream out = new FileOutputStream(file);
            out.write(user_image);
            out.close();
            return (file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }


    }
}
