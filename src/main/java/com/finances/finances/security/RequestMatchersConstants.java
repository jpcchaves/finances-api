package com.finances.finances.security;

public class RequestMatchersConstants {

  public static final String[] PUBLIC_REQUEST_MATCHERS =
      new String[] {
        "/swagger-ui/**",
        "/v3/api-docs/**",
        "/swagger-ui.html",
        "/api/v1/auth/login",
        "/api/v1/auth/register"
      };
}
