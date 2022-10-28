package com.intellias.intellistart.interviewplanning.controllers.auth;


import com.intellias.intellistart.interviewplanning.controllers.dto.UserDto;
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
  public static Role userRole;
  public static Long id;

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
    Long facebookId = Long.valueOf(
        (String) ((OAuth2Authentication) user).getUserAuthentication().getPrincipal());

    // checks if user exists. Needs at least one created user.
    //    UserDto account = findUser(facebookId);
    //    if (account == null) {
    //      userService.register(new User(facebookId, email, Role.valueOf(role)));
    //    } else {
    //      userRole = account.getRole();
    //    }

    userService.register(new User(facebookId, email, Role.valueOf(role)));
  }

  /**
   * Method to get User id.
   */
  @RequestMapping("/getInfo")
  public void getId(final Principal user) {
    Long facebookId = Long.valueOf(
        (String) ((OAuth2Authentication) user).getUserAuthentication().getPrincipal());
    id = findUserId(facebookId);
    userRole = findUserRole(facebookId);
    System.out.println(id + " " + userRole);
  }

  public Long findUserId(Long facebookId) {
    return userService.findUserByFacebookId(facebookId).getId();
  }

  public Role findUserRole(Long facebookId) {
    return userService.findUserByFacebookId(facebookId).getRole();
  }

  public UserDto findUser(Long facebookId) {
    return convertToUserDto(userService.findUserByFacebookId(facebookId));
  }

  private User convertToUser(UserDto userDto) {
    return mapper.map(userDto, User.class);
  }

  private UserDto convertToUserDto(User user) {
    return mapper.map(user, UserDto.class);
  }

}