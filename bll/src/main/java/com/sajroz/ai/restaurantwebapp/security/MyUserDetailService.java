package com.sajroz.ai.restaurantwebapp.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import com.sajroz.ai.restaurantwebapp.dto.UserDto;
import com.sajroz.ai.restaurantwebapp.services.UserService;

import java.util.Collections;

@Component
public class MyUserDetailService implements UserDetailsService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserService userService;

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        logger.debug("loadUserByEmail, email={}", email);

        UserDto user = userService.getUserByEmail(email);

        if(user == null) {
            logger.debug("loadUserByEmail failed - user not found, email={}", email);
            //throw new UsernameNotFoundException("User not found");
            return null;
        }

        logger.debug("loadUserByEmail successful, email={}", email);
        return new User(user.getEmail(), "", Collections.singletonList(new SimpleGrantedAuthority("ROLE")));
    }


}
