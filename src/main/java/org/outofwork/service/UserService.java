package org.outofwork.service;

import org.outofwork.datamodel.JwtTokenUser;
import org.outofwork.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 *
 * @author outofwork
 * @version 1.0
 * @since Jan 2025
 */
@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Register a new user.
     * This method is transactional to ensure both username and email uniqueness.
     *
     * @param user the JwtTokenUser to be registered
     * @throws IllegalArgumentException if the username or email already exists
     */
    public boolean registerUser(JwtTokenUser user) {
        // Add the user to the repository. The repository will handle uniqueness checks.
        return userRepository.addJwtTokenUser(user);
    }

    /**
     * Find a user by their username.
     *
     * @param username the username to search for
     * @return an Optional containing the user if found
     */
    public Optional<JwtTokenUser> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Find a user by their email.
     *
     * @param email the email to search for
     * @return an Optional containing the user if found
     */
    public Optional<JwtTokenUser> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Fetch the password hash for a given username.
     *
     * @param username the username of the user
     * @return the hashed password, wrapped in an Optional
     */
    public Optional<String> getPassword(String username) {
        return userRepository.findPasswordByUsername(username);
    }
}

