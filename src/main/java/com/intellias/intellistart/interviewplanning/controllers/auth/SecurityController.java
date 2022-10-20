package com.intellias.intellistart.interviewplanning.controllers.auth;


import com.intellias.intellistart.interviewplanning.models.User;
import com.intellias.intellistart.interviewplanning.models.enums.Role;
import com.intellias.intellistart.interviewplanning.services.UserService;
import java.security.Principal;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Candidate controller.
 */
@RestController
@RequestMapping("/user")
public class SecurityController {

  private final UserService userService;
  private final ModelMapper mapper;

  @Autowired
  public SecurityController(UserService userService, ModelMapper mapper) {
    this.userService = userService;
    this.mapper = mapper;
  }

  /**
   * Method to register user.
   */
  @RequestMapping("/{role}")
  public void registerUser(final Principal user, @PathVariable String role) {
    String details = ((OAuth2Authentication) user).getUserAuthentication().getDetails().toString();
    String email = details.substring(details.indexOf("=") + 1, details.indexOf(","));
    userService.register(new User(Long.valueOf(
        (String) ((OAuth2Authentication) user).getUserAuthentication().getPrincipal()),
        email,
        Role.valueOf(role)));
  }

}