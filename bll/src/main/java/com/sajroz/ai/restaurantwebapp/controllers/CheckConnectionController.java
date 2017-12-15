package com.sajroz.ai.restaurantwebapp.controllers;

import com.sajroz.ai.restaurantwebapp.dto.UserDto;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CheckConnectionController {

    @RequestMapping(value = "/test")
    public String test() {
        return "good";
    }

    @RequestMapping(value = "/test2")
    public String test2() {

        return (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal() + " "
                + SecurityContextHolder.getContext().getAuthentication().getCredentials() + " "
                + SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
    }
}
