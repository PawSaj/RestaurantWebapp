package com.sajroz.ai.restaurantwebapp.controllers;

import com.sajroz.ai.restaurantwebapp.dto.UserDto;
import com.sajroz.ai.restaurantwebapp.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET, produces = "application/json")
    public UserDto getUser() {
        return userService.getUserDtoByEmail((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }

    @RequestMapping(value = "/user/{userId}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = "application/json")
    public String getUser(@PathVariable(value = "userId") Long userId, @RequestBody UserDto userDto) {
        logger.debug("Updating to user={}", userDto);
        return userService.updateUser(userId, userDto, false);
    }

    @RequestMapping(value = "/user/{userId}", method = RequestMethod.DELETE, produces = "application/json")
    public String deleteUser(@PathVariable Long userId) {
        return userService.deleteUser(userId, false);
    }

    @RequestMapping(value = "/admin/users", method = RequestMethod.GET, produces = "application/json")
    public String adminUpdateUser() {
        return userService.getAllUsers((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }

    @RequestMapping(value = "/admin/users/{userId}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = "application/json")
    public String adminUpdateUser(@PathVariable(value = "userId") Long userId, @RequestBody UserDto userDto) {
        logger.info("adminUpdateUser Updating by admin to, user={}", userDto);
        return userService.updateUser(userId, userDto, true);
    }

    @RequestMapping(value = "/admin/users/{userId}", method = RequestMethod.DELETE, produces = "application/json")
    public String adminDeleteUser(@PathVariable Long userId) {
        return userService.deleteUser(userId, true);
    }

}
