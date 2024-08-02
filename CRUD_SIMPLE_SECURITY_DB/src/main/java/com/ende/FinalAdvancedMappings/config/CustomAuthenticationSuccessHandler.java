package com.ende.FinalAdvancedMappings.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        String redirectUrl = determineTargetUrl(authentication);
        response.sendRedirect(redirectUrl);
    }

    protected String determineTargetUrl(Authentication authentication) {
        boolean isProfessor = false;
        boolean isStudent = false;

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (GrantedAuthority grantedAuthority : authorities) {
            String authority = grantedAuthority.getAuthority();
            if (authority.equals("ROLE_PROFESSOR") || authority.equals("ROLE_DEAN") ||
                    authority.equals("ROLE_MATH") || authority.equals("ROLE_PHYSICS") ||
                    authority.equals("ROLE_CS") || authority.equals("ROLE_TEST")) {
                isProfessor = true;
                break;
            } else if (authority.equals("ROLE_STUDENT") ||authority.equals("ROLE_VERTRETER")) {
                isStudent = true;
                break;
            }
        }

        if (isProfessor) {
            return "/professor-home";
        } else if (isStudent) {
            return "/student-home";
        } else {
            throw new IllegalStateException("User role not recognized");
        }
    }
}
