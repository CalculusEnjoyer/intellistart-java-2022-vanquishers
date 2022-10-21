package com.intellias.intellistart.interviewplanning.controllers.auth;


import java.security.Principal;
import java.util.Map;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Candidate controller.
 */
@RestController
@RequestMapping("/user")
public class SecurityController {

  /**
   * Method to take info about authenticated user.
   *
   * @return information.
   */
  @GetMapping
  public String getUser(final Principal user) {
    Map map = (Map) ((OAuth2Authentication) user).getUserAuthentication().getDetails();
    return "" + map;
  }

}