package com.intellias.intellistart.interviewplanning.dto;

import com.intellias.intellistart.interviewplanning.models.User;
import com.intellias.intellistart.interviewplanning.models.enums.Role;
import javax.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * User class.
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class UserDto {

  @NotEmpty
  private final String email;
  @NotEmpty
  private final Role role;

  /**
   * Quick DTO creation.

   * @param user entity
   * @return DTO object
   */
  public static UserDto of(User user) {
    return new UserDto(
      user.getEmail(),
      user.getRole()
    );
  }

}
