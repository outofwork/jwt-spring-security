package org.outofwork.rest;

import org.outofwork.datamodel.JwtTokenUser;
import org.outofwork.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 *
 * @author outofwork
 * @version 1.0
 * @since Jan 2025
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Boolean> registerUser(@RequestBody Map<String, String> registerUserRequest) {
        JwtTokenUser jwtTokenUser = new JwtTokenUser.Builder()
                .setUsername(registerUserRequest.get("username"))
                .setEmailId(registerUserRequest.get("email"))
                .setFirstName(registerUserRequest.get("firstName"))
                .setLastName(registerUserRequest.get("lastName"))
                .setPassword(registerUserRequest.get("password"))
                .setActive(true)
                .createJwtTokenUser();
        Boolean isCreated = userService.registerUser(jwtTokenUser);
        return ResponseEntity.ok(isCreated);
    }
}