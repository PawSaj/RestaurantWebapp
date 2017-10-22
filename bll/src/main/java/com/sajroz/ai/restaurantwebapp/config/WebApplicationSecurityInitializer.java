package com.sajroz.ai.restaurantwebapp.config;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

import javax.servlet.SessionTrackingMode;
import java.util.EnumSet;
import java.util.Set;

public class WebApplicationSecurityInitializer extends AbstractSecurityWebApplicationInitializer {

    @Override
    protected boolean enableHttpSessionEventPublisher() {
        // workaround for Spring Security issue SEC-2855
        return true;
    }

    /**
     * Ustawienie trybu śledzenia sesji na plik cookie.
     *
     * @return tryb śledzenia: plik cookie
     */
    @Override
    protected Set<SessionTrackingMode> getSessionTrackingModes() {
        return EnumSet.of(SessionTrackingMode.COOKIE);
    }
}
