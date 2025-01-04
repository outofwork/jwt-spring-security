package org.outofwork.jwtsecurity;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 *
 * @author outofwork
 * @version 1.0
 * @since Jan 2025
 */
@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        // Set the response status code
        response.setStatus(HttpStatus.FORBIDDEN.value());

        // Set the response type and message for the access denied error
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        // Return a custom JSON error message
        response.getWriter().write("{\"error\": \"Access Denied: You do not have permission to access this resource.\"}");
    }

}
