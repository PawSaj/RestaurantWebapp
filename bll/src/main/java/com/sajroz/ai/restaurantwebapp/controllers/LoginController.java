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

    private final UserService userService;

    private final JSONMessageGenerator jsonMessageGenerator;

    @Autowired
    public LoginController(UserService userService, JSONMessageGenerator jsonMessageGenerator) {
        this.userService = userService;
        this.jsonMessageGenerator = jsonMessageGenerator;
    }

    @RequestMapping(value = "/successfulLogin", produces = "application/json")
    public String successfulLogin() {
        return userService.userInfoResponse((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }

    @RequestMapping(value = "/failedLogin", produces = "application/json")
    public String failedLogin() {
        return jsonMessageGenerator.createSimpleResponse(ResponseMessages.LOGIN_FILED).toString();
    }

    @RequestMapping(value = "/successfulLogout", produces = "application/json")
    public String successfulLogout() {
        return jsonMessageGenerator.createSimpleResponse(ResponseMessages.OK).toString();
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST, produces = "application/json")
    public String registration(@RequestBody UserDto userDto) {
        logger.info("registration, user={}", userDto);
        return userService.registerUser(userDto);
    }

    @RequestMapping(value = "/accessDenied", produces = "application/json")
    public String accessDenied() {
        return jsonMessageGenerator.createSimpleResponse(ResponseMessages.ACCESS_DENIED).toString();
    }
}
