package com.intellias.intellistart.interviewplanning.models.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FacebookUser {

  private String id;
  private String email;
  private String first_name;
  private String last_name;

  @Override
  public String toString() {
    return "FacebookUser{" +
        "email='" + email + '\'' +
        ", first_name='" + first_name + '\'' +
        ", last_name='" + last_name + '\'' +
        '}';
  }
}