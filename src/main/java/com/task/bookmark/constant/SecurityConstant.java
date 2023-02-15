package com.task.bookmark.constant;

public class SecurityConstant {
    public static final Long EXPIRATION_TIME = 432_000_000L; // 5 Days expressed in milliseconds
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String JWT_TOKEN_HEADER = "access_token";
    public static final String TOKEN_CANNOT_BE_VERIFIED = "Token cannot be verified!";
    public static final String AUTHORITIES = "authorities";
    public static final String FORBIDDEN_MESSAGE = "You need to login to access this page.";
    public static final String[] PUBLIC_URLS = {"/api/v1/auth/**"};
}
