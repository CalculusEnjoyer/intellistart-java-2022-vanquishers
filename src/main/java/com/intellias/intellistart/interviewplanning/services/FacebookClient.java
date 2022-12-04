package com.intellias.intellistart.interviewplanning.services;

import com.intellias.intellistart.interviewplanning.models.security.FacebookUser;
import com.intellias.intellistart.interviewplanning.util.exceptions.EmptyFacebookEmailException;
import com.intellias.intellistart.interviewplanning.util.exceptions.InvalidJwtTokenException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * FacebookClient service.
 */
@Service
public class FacebookClient extends RestTemplate {

  private final RestTemplate restTemplate;

  @Autowired
  public FacebookClient(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  /**
   * Method to get User from Facebook.
   *
   * @return restTemplate
   */
  public FacebookUser getUser(String accessToken) {
    final var path = "/me?fields={fields}&redirect={redirect}&access_token={access_token}";
    final var fields = "email,first_name,last_name";

    final Map<String, String> variables = new HashMap<>();
    variables.put("fields", fields);
    variables.put("redirect", "false");
    variables.put("access_token", accessToken);
    String facebookGraphApiBase = "https://graph.facebook.com";

    FacebookUser facebookUser;
    try {
      facebookUser = restTemplate
          .getForObject(facebookGraphApiBase + path, FacebookUser.class, variables);
    } catch (Exception exception) {
      String errorInfo = exception.getMessage();
      throw new InvalidJwtTokenException(errorInfo.substring(
          errorInfo.indexOf("message") + 10,
          errorInfo.indexOf("type") - 3));
    }
    if (facebookUser == null || facebookUser.getEmail() == null) {
      throw new EmptyFacebookEmailException();
    }
    return facebookUser;
  }
}
