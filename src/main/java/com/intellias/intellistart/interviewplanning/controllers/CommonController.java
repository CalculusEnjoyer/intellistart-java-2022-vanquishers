package com.intellias.intellistart.interviewplanning.controllers;


import com.intellias.intellistart.interviewplanning.controllers.dto.UserDto;
import com.intellias.intellistart.interviewplanning.models.User;
import com.intellias.intellistart.interviewplanning.models.enums.Role;
import com.intellias.intellistart.interviewplanning.models.security.FacebookUserDetails;
import com.intellias.intellistart.interviewplanning.services.WeekService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Common controller. Manages the common, 'any user' endpoints for getting the basic information
 * like current or next week, or info about current user
 */

@RestController
public class CommonController {

  private final WeekService weekService;
  private final ModelMapper mapper;

  @Autowired
  public CommonController(WeekService weekService, ModelMapper mapper) {
    this.weekService = weekService;
    this.mapper = mapper;
  }

  /**
   * Endpoint for getting the information about the current user.
   *
   * @return response status
   */
  @GetMapping("/me")
  public ResponseEntity<Object> getMe(Authentication authentication) {
    FacebookUserDetails facebookUserDetails = (FacebookUserDetails) authentication.getPrincipal();
    User user = facebookUserDetails.getUser();
    if (user.getRole() == Role.CANDIDATE) {
      return ResponseEntity.ok(mapper.map(user, UserDto.class));
    }
    return ResponseEntity.ok(user);
  }

  /**
   * Endpoint for getting the information about the current week number.
   *
   * @return response status
   */
  @GetMapping("/weeks/current")
  public int getCurrentWeek() {
    return weekService.getCurrentWeekNum();
  }

  /**
   * Endpoint for getting the information about the next week number.
   *
   * @return response status
   */
  @GetMapping("/weeks/next")
  public int getNextWeek() {
    return weekService.getNextWeekNum();
  }

}
