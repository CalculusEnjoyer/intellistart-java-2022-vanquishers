package com.intellias.intellistart.interviewplanning.services;

import com.intellias.intellistart.interviewplanning.models.security.FacebookUser;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * FacebookClient service.
 */
@Service
public class FacebookClient extends RestTemplate {

  private final RestTemplate restTemplate;

  @Lazy
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
    return restTemplate
        .getForObject(facebookGraphApiBase + path, FacebookUser.class, variables);
  }
}
