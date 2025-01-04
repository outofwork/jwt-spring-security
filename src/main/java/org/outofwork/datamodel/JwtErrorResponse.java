package org.outofwork.datamodel;

/**
 *
 * @author outofwork
 * @version 1.0
 * @since Jan 2025
 */
public class JwtErrorResponse {

    private String errorMessage;
    private String errorCode;

    // Constructor for error responses
    public JwtErrorResponse(String errorMessage, String errorCode) {
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
