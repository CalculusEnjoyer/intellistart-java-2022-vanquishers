package com.intellias.intellistart.interviewplanning.payload;


import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Jwt authentication request class.
 */
@Getter
@RequiredArgsConstructor
public class JwtAuthenticationResponse {

  @NonNull
  private String accessToken;
  private final String tokenType = "Bearer";
}
