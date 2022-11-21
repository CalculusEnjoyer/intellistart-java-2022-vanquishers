package com.intellias.intellistart.interviewplanning.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Jwt configuration class.
 */
@Getter
@Setter
@NoArgsConstructor
@Component
public class JwtConfig {

  @Value("${security.jwt.uri:}")
  private String uri;

  @Value("${security.jwt.header:}")
  private String header;

  @Value("${security.jwt.prefix:}")
  private String prefix;

  @Value("${security.jwt.expiration:1234}")
  private int expiration;

  @Value("${security.jwt.secret:}")
  private String secret;
}
