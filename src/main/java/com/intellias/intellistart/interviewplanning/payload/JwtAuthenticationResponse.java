package com.intellias.intellistart.interviewplanning.payload;


import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class JwtAuthenticationResponse {

  @NonNull
  private String accessToken;
  private String tokenType = "Bearer";
}
