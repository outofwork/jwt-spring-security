package org.outofwork.datamodel;

/**
 *
 * @author outofwork
 * @version 1.0
 * @since Jan 2025
 */

public enum ErrorCode {

    USER_EMAIL_NOT_FOUND("Invalid Email Id or UserName. User not found", "USER_NOT_FOUND"),
    USER_INPUT_MISSING("Username or email is required", "USER_INPUT_MISSING"),
    INVALID_CREDENTIALS("Invalid username or password", "INVALID_CREDENTIALS");

    private final String errorMessage;
    private final String errorCode;

    ErrorCode(String errorMessage, String errorCode) {
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
