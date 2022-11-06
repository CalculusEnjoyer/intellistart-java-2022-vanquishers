package com.intellias.intellistart.interviewplanning.client;

import com.intellias.intellistart.interviewplanning.models.security.FacebookUser;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class FacebookClient extends RestTemplate {

    private final RestTemplate restTemplate;

    @Lazy
    @Autowired
    public FacebookClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public FacebookUser getUser(String accessToken) {
        var path = "/me?fields={fields}&redirect={redirect}&access_token={access_token}";
        var fields = "email,first_name,last_name";

        final Map<String, String> variables = new HashMap<>();
        variables.put("fields", fields);
        variables.put("redirect", "false");
        variables.put("access_token", accessToken);
        String FACEBOOK_GRAPH_API_BASE = "https://graph.facebook.com";
        return restTemplate
            .getForObject(FACEBOOK_GRAPH_API_BASE + path, FacebookUser.class, variables);
    }
}
