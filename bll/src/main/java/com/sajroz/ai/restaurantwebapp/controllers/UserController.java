package com.sajroz.ai.restaurantwebapp.controllers;

import com.sajroz.ai.restaurantwebapp.dto.UserDto;
import com.sajroz.ai.restaurantwebapp.returnMessages.JSONMessageGenerator;
import com.sajroz.ai.restaurantwebapp.returnMessages.Messages;
import com.sajroz.ai.restaurantwebapp.services.UserService;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserService userService;

    @Autowired
    private JSONMessageGenerator jsonMessageGenerator;

    @RequestMapping(value = "/user/update", method = RequestMethod.GET, produces = "application/json")
    public UserDto updateUser() {
        return userService.getUserByEmail((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }

    @RequestMapping(value = "/user/update/{userId}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = "application/json")
    public String updateUser(@PathVariable(value = "userId") Long userId, @RequestBody UserDto userDto) {
        logger.info("updating to, user={}", userDto);
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals(userService.getUserById(userId).getEmail())) {
            try {
                String message = userService.updateUser(userId, userDto, false);
                //correct logged user info
                Authentication request = new UsernamePasswordAuthenticationToken(userDto.getEmail(), userDto.getPassword(), SecurityContextHolder.getContext().getAuthentication().getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(request);
                return message;
            } catch (DataIntegrityViolationException e) {
                return jsonMessageGenerator.createSimpleRespons(Messages.DUPLICATE_EMAIL).toString();
            }
        } else {
            return jsonMessageGenerator.createSimpleRespons(Messages.ACCESS_ERROR).toString();
        }

    }

    @RequestMapping(value = "/admin/users/update", method = RequestMethod.GET, produces = "application/json")
    public String adminUpdateUser() {
        return userService.getAllUsers((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }

    @RequestMapping(value = "/admin/user/update/{userId}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = "application/json")
    public String adminUpdateUser(@PathVariable(value = "userId") Long userId, @RequestBody UserDto userDto) {
        JSONObject returnMessage = new JSONObject();
        logger.info("updating to, user={}", userDto);
        try {
            String message = userService.updateUser(userId, userDto, true);
            if (SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals(userService.getUserById(userId).getEmail())) {
                //correct logged user info
                Authentication request = new UsernamePasswordAuthenticationToken(userDto.getEmail(), userDto.getPassword(), SecurityContextHolder.getContext().getAuthentication().getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(request);
            }
            return message;
        } catch (DataIntegrityViolationException e) {
            return jsonMessageGenerator.createSimpleRespons(Messages.DUPLICATE_EMAIL).toString();
        }

    }

}
