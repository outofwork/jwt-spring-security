package org.outofwork.datamodel;

/**
 *
 * @author outofwork
 * @version 1.0
 * @since Jan 2025
 */
public class JwtTokenUser {

    private final String username;
    private final String password;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final boolean active;

    private JwtTokenUser(Builder builder) {
        this.username = builder.username;
        this.password = builder.password;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.email = builder.emailId;
        this.active = builder.active;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public boolean isActive() {
        return active;
    }

    public static class Builder {

        private String username;
        private String password;
        private String firstName;
        private String lastName;
        private String emailId;
        private boolean active;

        public Builder setUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder setEmailId(String emailId) {
            this.emailId = emailId;
            return this;
        }

        public Builder setActive(boolean active) {
            this.active = active;
            return this;
        }

        // Build the JwtTokenUser object
        public JwtTokenUser createJwtTokenUser() {
            return new JwtTokenUser(this);
        }
    }

    @Override
    public String toString() {
        return "JwtTokenUser{" +
                "username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", emailId='" + email + '\'' +
                ", active=" + active +
                '}';
    }
}

