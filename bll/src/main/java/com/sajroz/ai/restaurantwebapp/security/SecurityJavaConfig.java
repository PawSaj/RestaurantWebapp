package com.sajroz.ai.restaurantwebapp.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@ComponentScan
public class SecurityJavaConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Autowired
    private DaoAuthenticationProvider authenticationProvider;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @PostConstruct
    protected void init() {
        logger.info("INITIALIZE SecurityJavaConfig");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {

        authenticationProvider.setPasswordEncoder(passwordEncoder());
        auth.eraseCredentials(false);
        auth.authenticationProvider(authenticationProvider);

//        auth.inMemoryAuthentication()
//                .withUser("temporary").password("temporary").roles("ADMIN")
//                .and()
//                .withUser("user").password("userPass").roles("USER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
        http.sessionManagement()
                .sessionFixation()

                .none();
        // zabezpieczenie przed atakiem clickjacking
        http.headers().frameOptions().sameOrigin();

        // token CSRF
        http.csrf().disable();

        // uwierzytelnianie
        http.exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint)
                .and().formLogin().successForwardUrl("/successfulLogin").failureForwardUrl("/failedLogin")
                .and().logout().logoutSuccessUrl("/successfulLogout")
                .and().authorizeRequests()
                .antMatchers("/login*", "/logout*", "/registration", "/successfulLogout", "/test", "/menu").permitAll()
                .antMatchers("/admin/**").hasAnyRole("ADMIN")
                .antMatchers().hasAnyRole()
                .anyRequest().authenticated();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}