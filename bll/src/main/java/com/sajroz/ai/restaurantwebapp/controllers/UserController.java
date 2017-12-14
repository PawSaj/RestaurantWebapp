package com.sajroz.ai.restaurantwebapp.controllers;

import com.sajroz.ai.restaurantwebapp.dto.UserDto;
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

    @RequestMapping(value = "/user/update", method = RequestMethod.GET, produces = "application/json")
    public UserDto updateUser() {
        return userService.getUserByEmail((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }

    @RequestMapping(value = "/user/update/{userId}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = "application/json")
    public String updateUser(@PathVariable(value = "userId") Long userId, @RequestBody UserDto userDto) {
        JSONObject returnMessage = new JSONObject();
        logger.info("updating to, user={}", userDto);
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals(userService.getUserById(userId).getEmail())) {
            try {
                String message = userService.updateUser(userId, userDto, false);
                //correct logged user info
                Authentication request = new UsernamePasswordAuthenticationToken(userDto.getEmail(), userDto.getPassword(), SecurityContextHolder.getContext().getAuthentication().getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(request);
                return message;
            } catch (DataIntegrityViolationException e) {
                returnMessage.put("status", -1);
                returnMessage.put("message", "Email is taken. Try another.");
                return returnMessage.toString();
            }
        } else {
            returnMessage.put("status", -2);
            returnMessage.put("message", "You try to change data of different user");
            return returnMessage.toString();
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
            returnMessage.put("status", -1);
            returnMessage.put("message", "Email is taken. Try another.");
            return returnMessage.toString();
        }

    }

}
