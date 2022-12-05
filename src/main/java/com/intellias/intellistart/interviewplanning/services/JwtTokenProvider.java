package com.intellias.intellistart.interviewplanning.services;

import com.intellias.intellistart.interviewplanning.config.JwtConfig;
import com.intellias.intellistart.interviewplanning.models.security.FacebookUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.util.Date;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

/**
 * Jwt token provider service class.
 */
@Service
@Slf4j
public class JwtTokenProvider {

  private final JwtConfig jwtConfig;

  public JwtTokenProvider(JwtConfig jwtConfig) {
    this.jwtConfig = jwtConfig;
  }

  /**
   * Method to generate token.
   *
   * @return token.
   */
  public String generateToken(Authentication authentication, FacebookUser facebookUser) {
    long now = System.currentTimeMillis();
    return Jwts.builder()
        .setSubject(facebookUser.getEmail())
        .claim("first_name", facebookUser.getFirst_name())
        .claim("last_name", facebookUser.getLast_name())
        .claim("authorities", authentication.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
        .setIssuedAt(new Date(now))
        .setExpiration(new Date(now + jwtConfig.getExpiration() * 1000L))  // in milliseconds
        .signWith(SignatureAlgorithm.HS512, jwtConfig.getSecret().getBytes())
        .compact();
  }

  /**
   * Method to get claims from JWT.
   *
   * @return DefaultJwtParser object.
   */
  public Claims getClaimsFromJwt(String token) {
    return Jwts.parser()
        .setSigningKey(jwtConfig.getSecret().getBytes())
        .parseClaimsJws(token)
        .getBody();
  }

  /**
   * Method to validate token.
   */
  public boolean validateToken(String authToken) {
    try {
      Jwts.parser()
          .setSigningKey(jwtConfig.getSecret().getBytes())
          .parseClaimsJws(authToken);

      return true;
    } catch (SignatureException ex) {
      log.error("Invalid JWT signature");
    } catch (MalformedJwtException ex) {
      log.error("Invalid JWT token");
    } catch (ExpiredJwtException ex) {
      log.error("Expired JWT token");
    } catch (UnsupportedJwtException ex) {
      log.error("Unsupported JWT token");
    } catch (IllegalArgumentException ex) {
      log.error("JWT claims string is empty.");
    }
    return false;
  }
}
