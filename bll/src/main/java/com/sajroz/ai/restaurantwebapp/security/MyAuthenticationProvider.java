package com.sajroz.ai.restaurantwebapp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.sajroz.ai.restaurantwebapp.services.UserService;

import java.util.Collections;

@Component
@Transactional
public class MyAuthenticationProvider extends DaoAuthenticationProvider {

    private final UserService userService;

    @Autowired
    public MyAuthenticationProvider(UserDetailsService userDetailsService, UserService userService) {
        this.setUserDetailsService(userDetailsService);
        this.userService = userService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        if (authentication.getCredentials() != null && authentication.getPrincipal() != null) {
            String email = (String) authentication.getPrincipal();
            String password = (String) authentication.getCredentials();
            String role = userService.userByEmailAndPasswordExists(email, password);
            if (role != null) {
                return new UsernamePasswordAuthenticationToken(email, password, Collections.singletonList(new SimpleGrantedAuthority(role)));
            }
        }

        return null;

    }


}
