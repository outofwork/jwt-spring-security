package org.outofwork.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.outofwork.datamodel.JwtTokenUser;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryTest {

    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository = new UserRepository();
    }

    @Test
    void testAddJwtTokenUser_success() {
        // Arrange: Create a new user
        JwtTokenUser newUser = new JwtTokenUser.Builder()
                .setUsername("newUser")
                .setPassword("newPassword")
                .setFirstName("New")
                .setLastName("User")
                .setEmailId("new.user@example.com")
                .setActive(true)
                .createJwtTokenUser();

        // Act: Add the new user
        userRepository.addJwtTokenUser(newUser);

        // Assert: Check if the user is added by username and email
        Optional<JwtTokenUser> userByUsername = userRepository.findByUsername("newUser");
        assertTrue(userByUsername.isPresent(), "User should be found by username.");

        Optional<JwtTokenUser> userByEmail = userRepository.findByEmail("new.user@example.com");
        assertTrue(userByEmail.isPresent(), "User should be found by email.");
    }

    @Test
    void testFindByUsername_nonExistingUser() {
        // Act: Fetch a non-existing user by username
        Optional<JwtTokenUser> user = userRepository.findByUsername("nonExistingUser");

        // Assert: Ensure the user is not found
        assertFalse(user.isPresent(), "User should not be found by username.");
    }

    @Test
    void testFindByEmail_nonExistingUser() {
        // Act: Fetch a non-existing user by email
        Optional<JwtTokenUser> user = userRepository.findByEmail("non.existing@example.com");

        // Assert: Ensure the user is not found
        assertFalse(user.isPresent(), "User should not be found by email.");
    }

    @Test
    void testFindPasswordByUsername_nonExistingUser() {
        // Act: Fetch password for non-existing user
        Optional<String> password = userRepository.findPasswordByUsername("nonExistingUser");

        // Assert: Ensure no password is found
        assertFalse(password.isPresent(), "Password should not be found for non-existing user.");
    }

    @Test
    void testFindPasswordByEmail_nonExistingUser() {
        // Act: Fetch password for non-existing user
        Optional<String> password = userRepository.findPasswordByEmail("non.existing@example.com");

        // Assert: Ensure no password is found
        assertFalse(password.isPresent(), "Password should not be found for non-existing email.");
    }
}
