package com.intellias.intellistart.interviewplanning;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Main application.
 */
@SpringBootApplication
public class InterviewPlanningApplication {

  public static void main(String[] args) {
    SpringApplication.run(InterviewPlanningApplication.class, args);
  }

  @Bean
  public ModelMapper modelMapper() {
    return new ModelMapper();
  }

}
