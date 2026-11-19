package com.skbazaarhub.common.security;

public class SecurityConstants {
    public static final String SECRET = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970"; // 256-bit secret key
    public static final long EXPIRATION_TIME = 864_000_000; // 10 days in milliseconds
    public static final long REFRESH_EXPIRATION_TIME = 2_592_000_000L; // 30 days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
}
