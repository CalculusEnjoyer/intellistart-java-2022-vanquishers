package com.intellias.intellistart.interviewplanning;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
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
   * modelMapper.getConfiguration().setAmbiguityIgnored(true) is used for
   * determining whether destination properties that match more
   * than one source property should be ignored/
   *
   * @return ModelMapper
   */
  @Bean
  public ModelMapper modelMapper() {
    ModelMapper modelMapper = new ModelMapper();
    modelMapper.getConfiguration().setAmbiguityIgnored(true);
    return modelMapper;
  }

  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }
}
