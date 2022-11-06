package com.intellias.intellistart.interviewplanning.payload;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class FacebookLoginRequest implements Serializable {

  @NotBlank
  private String accessToken;
}
