# jwt-spring-security
This repository contains  Spring Boot 3 applications designed for secure user authentication and authorization using JSON Web Tokens (JWT)
 
### Prerequisites

Ensure you have the following installed on your system:

    Java 17 (OpenJDK 17.0.13 LTS)
    Maven 3+ (for building the project)

The application has been tested with the below java --version
    
    openjdk 17.0.13 2024-10-15 LTS
    OpenJDK Runtime Environment Zulu17.54+21-CA (build 17.0.13+11-LTS)
    OpenJDK 64-Bit Server VM Zulu17.54+21-CA (build 17.0.13+11-LTS, mixed mode, sharing)


### Setup

Clone this repository to your local machine:
The application will start on port 8080 by default.

        git clone <repository-url>

Navigate to the project directory:

    cd <project-directory>

Build the project using Maven:

    mvn clean install

Run the application:

    mvn spring-boot:run

## Exposed APIs

 - POST -  /api/users/register

This API allows users to register by providing their details.

Request:

    curl --location 'http://localhost:8080/api/users/register' \
    --data-raw '{
    "username": "john_doe",
    "password": "securePassword123",
    "firstName": "John",
    "lastName": "Doe",
    "email": "john.doe@example.com",
    "active": true
    }'

Successful Response:

    true

 - POST - /api/auth/generate-token

Request using username:
    
    curl --location 'http://localhost:8080/api/auth/generate-token' \
    --data '{
    "username": "john_doe",
    "password": "securePassword123"
    }'

Request using email:

    curl --location 'http://localhost:8080/api/auth/generate-token' \
    --data '{
    "email": "john.doe@example.com",
    "password": "securePassword123"
    }'


Success Response:

    {
        "username": "john_doe",
        "token": "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqb2huX2RvZSIsImF1dGgiOiIiLCJleHAiOjE3MzYxMDA2Njh9.b3ybho0H11jpKH-T_xsEqFk95P0DMwhrclP20HiRW8U3HcE-68NxmsDTHBwAZ62T8CPm0dXVJRK40El8VR6Iyw",
        "tokenExpiry": "2025-01-05 18:11:08 GMT",
        "creationTime": "2025-01-04 18:11:08 GMT"
    } 

Error Response if User Doesn't exist

    {
        "errorMessage": "Invalid Email Id or UserName. User not found",
        "errorCode": "USER_NOT_FOUND"
    }

Request Doesn't have username or Email

    {
        "errorMessage": "Username or email is required",
        "errorCode": "USER_INPUT_MISSING"
    }

Notes
    
- Ensure that the application is running on localhost:8080.
- The token generated in the Generate Token API can be used to authenticate subsequent requests requiring authorization.

License

- This project is licensed under the MIT License