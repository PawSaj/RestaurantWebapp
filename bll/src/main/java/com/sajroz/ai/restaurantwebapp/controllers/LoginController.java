package com.sajroz.ai.restaurantwebapp.controllers;

import com.sajroz.ai.restaurantwebapp.dto.UserDto;
import com.sajroz.ai.restaurantwebapp.returnMessages.JSONMessageGenerator;
import com.sajroz.ai.restaurantwebapp.returnMessages.ResponseMessages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import com.sajroz.ai.restaurantwebapp.services.UserService;

@RestController
public class LoginController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserService userService;

    @Autowired
    private JSONMessageGenerator jsonMessageGenerator;

    @RequestMapping(value = "/successfulLogin", produces = "application/json")
    public UserDto successfulLogin() {
        return userService.getUserByEmail((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }

    @RequestMapping(value = "/failedLogin", produces = "application/json")
    public String failedLogin() {
        return jsonMessageGenerator.createSimpleRespons(ResponseMessages.LOGIN_FILED).toString();
    }

    @RequestMapping(value = "/successfulLogout", produces = "application/json")
    public String successfulLogout() {
        return jsonMessageGenerator.createSimpleRespons(ResponseMessages.OK).toString();
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST, produces = "application/json")
    public String registration(@RequestBody UserDto userDto) {
        logger.info("registration, user={}", userDto);
        if (userService.getUserByEmail(userDto.getEmail()) != null) {
            logger.warn("registration failed - user already exist, email={}", userDto.getEmail());

            return jsonMessageGenerator.createSimpleRespons(ResponseMessages.DUPLICATE_EMAIL).toString();
        } else {
            logger.info("saving user to database, user={}", userDto);

            userDto.setRole("USER");
            userService.saveUserToDatabase(userService.mapUserFromDto(userDto));
            return jsonMessageGenerator.createSimpleRespons(ResponseMessages.USER_REGISTERED).toString();
        }
    }
}
