package com.intellias.intellistart.interviewplanning.payload;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Facebook login request body.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class FacebookLoginRequest implements Serializable {

  @NotBlank
  private String accessToken;

}
