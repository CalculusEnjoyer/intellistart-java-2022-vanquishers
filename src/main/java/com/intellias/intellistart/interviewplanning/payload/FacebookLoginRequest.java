package com.intellias.intellistart.interviewplanning.payload;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Facebook login request body.
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FacebookLoginRequest implements Serializable {

  @NotBlank
  private String accessToken;

}
