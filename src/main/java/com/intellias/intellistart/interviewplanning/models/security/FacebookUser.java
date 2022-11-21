package com.intellias.intellistart.interviewplanning.models.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Facebook User class.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class FacebookUser {

  private String id;
  private String email;
  @SuppressWarnings("all")
  private String first_name;
  @SuppressWarnings("all")
  private String last_name;

}