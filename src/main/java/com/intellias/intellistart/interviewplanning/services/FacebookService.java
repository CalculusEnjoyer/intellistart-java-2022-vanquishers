package com.intellias.intellistart.interviewplanning.services;


import com.intellias.intellistart.interviewplanning.models.Candidate;
import com.intellias.intellistart.interviewplanning.models.User;
import com.intellias.intellistart.interviewplanning.models.enums.Role;
import com.intellias.intellistart.interviewplanning.models.security.FacebookUser;
import com.intellias.intellistart.interviewplanning.models.security.FacebookUserDetails;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

/**
 * Facebook service.
 */
@Service
@Slf4j
public class FacebookService {

  private final FacebookClient facebookClient;
  private final UserService userService;
  private final JwtTokenProvider tokenProvider;

  /**
   * Facebook service constructor.
   */
  @Autowired
  public FacebookService(FacebookClient facebookClient, UserService userService,
      JwtTokenProvider tokenProvider) {
    this.facebookClient = facebookClient;
    this.userService = userService;
    this.tokenProvider = tokenProvider;
  }

  /**
   * Method to get User from database or add if not registered yet.
   *
   * @return Jwt token.
   */
  public String loginUser(String fbAccessToken) {
    var facebookUser = facebookClient.getUser(fbAccessToken);
    Authentication authentication = Optional.ofNullable(
            userService.findUserByEmail(facebookUser.getEmail()))
        .or(() -> Optional.ofNullable(userService.registerCandidate(convertTo(facebookUser))))
        .map(FacebookUserDetails::new)
        .map(userDetails -> new UsernamePasswordAuthenticationToken(facebookUser,
            null, userDetails.getAuthorities())).orElseThrow();
    return tokenProvider.generateToken(authentication, facebookUser);
  }

  private User convertTo(FacebookUser facebookUser) {
    User user = new User();
    user.setEmail(facebookUser.getEmail());
    user.setRole(Role.CANDIDATE);
    return user;
  }
}
