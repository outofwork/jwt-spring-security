package org.outofwork.rest;

import org.outofwork.datamodel.ErrorCode;
import org.outofwork.datamodel.JwtAuthenticationResponse;
import org.outofwork.datamodel.JwtErrorResponse;
import org.outofwork.jwtsecurity.JwtTokenProvider;
import org.outofwork.jwtsecurity.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import static org.outofwork.config.AppConstant.BEARER_AUTH;

/**
 * @author outofwork
 * @version 1.0
 * @since Jan 2025
 */
@RestController
@RequestMapping("/api/auth")
public class GenerateTokenController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @PostMapping("/generate-token")
    public ResponseEntity<Object> generateJwtToken(@RequestBody Map<String, String> generateTokenRequest) {
        String username = generateTokenRequest.get("username");
        String email = generateTokenRequest.get("email");
        String password = generateTokenRequest.get("password");

        UserDetails userDetails = null;
        if (username != null && !username.isEmpty()) {
            try {
                userDetails = jwtUserDetailsService.loadUserByUsername(username);
            } catch (UsernameNotFoundException exception) {
                //Skip the Exception and Try with Email.
            }
        }
        if (userDetails == null && email != null && !email.isEmpty()) {
            try {
                userDetails = jwtUserDetailsService.loadUserByEmail(email);
            } catch (UsernameNotFoundException exception) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new JwtErrorResponse(ErrorCode.USER_EMAIL_NOT_FOUND.getErrorMessage(), ErrorCode.USER_EMAIL_NOT_FOUND.getErrorCode())
                );
            }
        }

        if (username == null && email == null) {
            // If neither username nor email is provided, return an error
            return ResponseEntity.badRequest().body(
                    new JwtErrorResponse(ErrorCode.USER_INPUT_MISSING.getErrorMessage(), ErrorCode.USER_INPUT_MISSING.getErrorCode())
            );
        }


        // Authenticate user
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userDetails.getUsername(), password)
            );
        } catch (BadCredentialsException e) {
            // Invalid credentials
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    new JwtErrorResponse(ErrorCode.INVALID_CREDENTIALS.getErrorMessage(), ErrorCode.INVALID_CREDENTIALS.getErrorCode())
            );
        }

        // Generate JWT token and its expiration time
        String jwtToken = jwtTokenProvider.generateToken(authentication, true);
        long expiryTime = jwtTokenProvider.getTokenExpirationTime(jwtToken);

        JwtAuthenticationResponse response = new JwtAuthenticationResponse(
                userDetails.getUsername(),
                BEARER_AUTH + jwtToken,
                convertMillisToGMT(expiryTime),
                convertMillisToGMT(System.currentTimeMillis())
        );

        return ResponseEntity.ok(response);
    }


    private String convertMillisToGMT(long timestampInMillis) {
        ZonedDateTime zonedDateTime = Instant.ofEpochMilli(timestampInMillis).atZone(ZoneOffset.UTC);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss 'GMT'");
        return formatter.format(zonedDateTime);
    }

}
