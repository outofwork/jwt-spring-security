package org.outofwork.config;

/**
 *
 * @author outofwork
 * @version 1.0
 * @since Jan 2025
 */
public class AppConstant {

    //Authorization Constants
    public static final String AUTHORITIES_KEY = "auth";
    public static final String BEARER_AUTH = "Bearer ";

    // This is used to generate the Base64 Encoded Version and used in application.properties
    private static final String SECRET_TOKEN = "SuperComplexSecretKeyForJWTTokenGenerationThatIsDifficultToGuess123!@#2025";

    // Http Header Constants
    public static final String AUTHORIZATION = "Authorization";
    public static final String CACHE_CONTROL = "Cache-Control";
    public static final String CONTENT_TYPE = "Content-Type";
}
