package com.intellias.intellistart.interviewplanning;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.client.RestTemplate;

/**
 * Main application.
 */
@SpringBootApplication
@Slf4j
public class InterviewPlanningApplication {

  public static void main(String[] args) {
    SpringApplication.run(InterviewPlanningApplication.class, args);
  }

  /**
   * Bean for getting ModelMapper to map entity to DTO and back.
   * modelMapper.getConfiguration().setAmbiguityIgnored(true) is used for determining whether
   * destination properties that match more than one source property should be ignored.
   * modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull()) makes the model
   * mapper to not map null properties into non-null properties of the destination object
   *
   * @return ModelMapper
   */
  @Bean
  public ModelMapper modelMapper() {
    ModelMapper modelMapper = new ModelMapper();
    modelMapper.getConfiguration().setAmbiguityIgnored(true);

    modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
    return modelMapper;
  }

  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }

  @Bean
  public UserDetailsService userDetailsService() {
    return username -> null;
  }
}
