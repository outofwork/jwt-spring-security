package org.outofwork.repository;

import org.outofwork.datamodel.JwtTokenUser;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author outofwork
 * @version 1.0
 * @since Jan 2025
 */
@Component
public class UserRepository {

    // Concurrent maps to store users by username and email for uniqueness
    private final Map<String, JwtTokenUser> usersByUsername = new ConcurrentHashMap<>();
    private final Map<String, JwtTokenUser> usersByEmail = new ConcurrentHashMap<>();

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * Find a user by their username.
     *
     * @param username the username to search for
     * @return an Optional containing the user if found
     */
    public Optional<JwtTokenUser> findByUsername(String username) {
        JwtTokenUser user = usersByUsername.get(username);
        return Optional.ofNullable(user).filter(JwtTokenUser::isActive);
    }

    /**
     * Find a user by their email address.
     *
     * @param email the email to search for
     * @return an Optional containing the user if found
     */
    public Optional<JwtTokenUser> findByEmail(String email) {
        JwtTokenUser user = usersByEmail.get(email);
        return Optional.ofNullable(user).filter(JwtTokenUser::isActive);
    }

    /**
     * Add a new user to the repository.
     * Ensures both username and email are unique.
     *
     * @param user the JwtTokenUser to be added
     * @throws IllegalArgumentException if the username or email already exists
     */
    public boolean addJwtTokenUser(JwtTokenUser user) {
        if (usersByUsername.containsKey(user.getUsername())) {
            throw new IllegalArgumentException("Username already exists.");
        }
        if (usersByEmail.containsKey(user.getEmail())) {
            throw new IllegalArgumentException("Email already exists.");
        }

        // Hash the password and add the user
        String hashedPassword = passwordEncoder.encode(user.getPassword());

        JwtTokenUser userWithHashedPassword = new JwtTokenUser.Builder()
                .setUsername(user.getUsername())
                .setPassword(hashedPassword)
                .setFirstName(user.getFirstName())
                .setLastName(user.getLastName())
                .setEmailId(user.getEmail())
                .setActive(user.isActive())
                .createJwtTokenUser();

        // Store the user by username and email
        usersByUsername.put(userWithHashedPassword.getUsername(), userWithHashedPassword);
        usersByEmail.put(userWithHashedPassword.getEmail(), userWithHashedPassword);

        return true;
    }

    /**
     * Fetch the password hash for a given username.
     *
     * @param username the username of the user
     * @return the hashed password, wrapped in an Optional
     */
    public Optional<String> findPasswordByUsername(String username) {
        return Optional.ofNullable(usersByUsername.get(username))
                .map(JwtTokenUser::getPassword);
    }

    /**
     * Fetch the password hash for a given email.
     *
     * @param email the email of the user
     * @return the hashed password, wrapped in an Optional
     */
    public Optional<String> findPasswordByEmail(String email) {
        return Optional.ofNullable(usersByEmail.get(email))
                .map(JwtTokenUser::getPassword);
    }
}
