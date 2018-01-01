package com.sajroz.ai.restaurantwebapp.security;

import com.sajroz.ai.restaurantwebapp.returnMessages.JSONMessageGenerator;
import com.sajroz.ai.restaurantwebapp.returnMessages.ResponseMessages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final JSONMessageGenerator jsonMessageGenerator;

    @Autowired
    public RestAuthenticationEntryPoint(JSONMessageGenerator jsonMessageGenerator) {
        this.jsonMessageGenerator = jsonMessageGenerator;
    }

    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e)
            throws IOException, ServletException {
        logger.debug("ENTER commence(): httpServletRequest={}, httpServletResponse={}, e={}", httpServletRequest, httpServletResponse, e);
        logger.debug("requestUri: {}", httpServletRequest);

        try{
            httpServletResponse.addHeader("content-type", "application/json;charset=UTF-8");
            httpServletResponse.getWriter().print(jsonMessageGenerator.createSimpleResponse(ResponseMessages.LOGIN_REQUIRED));
        }
        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
        }
    }
}