package org.outofwork.datamodel;

/**
 *
 * @author outofwork
 * @version 1.0
 * @since Jan 2025
 */
public class JwtAuthenticationResponse {

    private String username;
    private String token;
    private String tokenExpiry;
    private String creationTime;

    public JwtAuthenticationResponse(String username, String token, String tokenExpiry, String creationTime) {
        this.username = username;
        this.token = token;
        this.tokenExpiry = tokenExpiry;
        this.creationTime = creationTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTokenExpiry() {
        return tokenExpiry;
    }

    public void setTokenExpiry(String tokenExpiry) {
        this.tokenExpiry = tokenExpiry;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }
}
