package com.intellias.intellistart.interviewplanning.payload;


import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Jwt authentication request class.
 */
@Data
@RequiredArgsConstructor
public class JwtAuthenticationResponse {

  @NonNull
  private String accessToken;
  private String tokenType = "Bearer";
}
