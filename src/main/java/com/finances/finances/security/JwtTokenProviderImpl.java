package com.finances.finances.security;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.finances.finances.domain.dto.auth.UserLoginResponseDTO;
import com.finances.finances.domain.entities.User;
import com.finances.finances.exception.BadRequestException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import java.util.Map;
import javax.crypto.SecretKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProviderImpl implements JwtTokenProvider {

  private static final String AUTHORITIES_CLAIM_KEY = "authorities";
  private static final String USER_CLAIM_KEY = "user";

  private static final Logger logger = LoggerFactory.getLogger(JwtTokenProviderImpl.class);

  private final ObjectMapper objectMapper;

  @Value("${app.jwt-secret}")
  private String jwtSecret;

  @Value("${app.jwt-expiration-milliseconds}")
  private long jwtExpiration;

  public JwtTokenProviderImpl(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  @Override
  public String generateToken(Authentication authentication) {
    User user = (User) authentication.getPrincipal();

    Date creationDate = new Date();

    Date expirationDate = new Date(creationDate.getTime() + jwtExpiration);

    return Jwts.builder()
        .subject(user.getEmail())
        .issuedAt(creationDate)
        .expiration(expirationDate)
        .signWith(generateKey())
        .claim(AUTHORITIES_CLAIM_KEY, authentication.getAuthorities())
        .claim(USER_CLAIM_KEY, generateUserClaims(user))
        .compact();
  }

  @Override
  public String getTokenSubject(String token) {
    Claims claims =
        Jwts.parser().verifyWith(generateKey()).build().parseSignedClaims(token).getPayload();

    return claims.getSubject();
  }

  @Override
  public boolean validateToken(String token) {
    try {

      Jwts.parser().verifyWith(generateKey()).build().parse(token);

      return true;

    } catch (MalformedJwtException ex) {

      throw new BadRequestException("Token inválido!");
    } catch (ExpiredJwtException ex) {

      throw new BadRequestException("Token expirado!");
    } catch (UnsupportedJwtException ex) {

      throw new BadRequestException("Formato de token inválido!");
    } catch (IllegalArgumentException | SecurityException ex) {

      throw new BadRequestException("Ocorreu um ero ao processar sua requisição!");
    }
  }

  @Override
  public String getClaimFromTokenByKey(String token, String key) {
    try {

      Map<String, Object> claims =
          Jwts.parser().verifyWith(generateKey()).build().parseSignedClaims(token).getPayload();

      Map<String, Object> userClaim =
          objectMapper.convertValue(claims.get(USER_CLAIM_KEY), new TypeReference<>() {});

      return String.valueOf(userClaim.get(key));

    } catch (Exception e) {

      logger.error("Could not get all claims Token from passed token");

      return null;
    }
  }

  private SecretKey generateKey() {

    return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
  }

  private UserLoginResponseDTO generateUserClaims(User user) {

    return new UserLoginResponseDTO(user.getId(), user.getName(), user.getEmail());
  }
}
