package com.finances.finances.security;

import org.springframework.security.core.Authentication;

public interface JwtTokenProvider {

  String generateToken(Authentication authentication);

  String getTokenSubject(String token);

  boolean validateToken(String token);

  String getClaimFromTokenByKey(String token, String key);
}
