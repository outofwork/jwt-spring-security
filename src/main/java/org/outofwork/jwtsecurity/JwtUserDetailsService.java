package org.outofwork.jwtsecurity;

import org.outofwork.datamodel.JwtTokenUser;
import org.outofwork.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Optional;

/**
 *
 * @author outofwork
 * @version 1.0
 * @since Jan 2025
 */
@Component
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<JwtTokenUser> user = userService.getUserByUsername(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        return new User(user.get().getUsername(), user.get().getPassword(), new ArrayList<>());
    }

    public UserDetails loadUserByEmail(String email) throws UsernameNotFoundException {
        Optional<JwtTokenUser> user = userService.getUserByEmail(email);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        return new User(user.get().getUsername(), user.get().getPassword(), new ArrayList<>());
    }
}
