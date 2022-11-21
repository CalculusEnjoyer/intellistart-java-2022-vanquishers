package com.intellias.intellistart.interviewplanning.controllers;


import com.intellias.intellistart.interviewplanning.payload.FacebookLoginRequest;
import com.intellias.intellistart.interviewplanning.payload.JwtAuthenticationResponse;
import com.intellias.intellistart.interviewplanning.services.FacebookService;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Authenticate controller.
 */
@RestController
@Slf4j
public class AuthController {

  private final FacebookService facebookService;

  @Autowired
  @Lazy
  public AuthController(FacebookService facebookService) {
    this.facebookService = facebookService;
  }

  /**
   * Method for login user via Facebook.
   *
   * @return response status
   */
  @PostMapping("/facebook/signin")
  public ResponseEntity<?> facebookAuth(
      @Valid @RequestBody FacebookLoginRequest facebookLoginRequest) {
    log.info("facebook login {}", facebookLoginRequest);
    String token = facebookService.loginUser(facebookLoginRequest.getAccessToken());
    return ResponseEntity.ok(new JwtAuthenticationResponse(token));
  }
}