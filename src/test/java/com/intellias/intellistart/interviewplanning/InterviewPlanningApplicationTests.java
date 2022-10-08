package com.intellias.intellistart.interviewplanning;

import static org.assertj.core.api.Assertions.assertThat;

import com.intellias.intellistart.interviewplanning.dto.CandidateSlotDto;
import com.intellias.intellistart.interviewplanning.dto.InterviewerSlotDto;
import com.intellias.intellistart.interviewplanning.models.enums.Status;
import com.intellias.intellistart.interviewplanning.services.CandidateService;
import com.intellias.intellistart.interviewplanning.services.InterviewerService;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class InterviewPlanningApplicationTests {

  @Test
  void contextLoads() {
    int year = Calendar.getInstance().get(Calendar.YEAR);
    InterviewerSlotDto interviewerSlotDto = new InterviewerSlotDto(0, DayOfWeek.MONDAY,
        LocalTime.of(9, 30), LocalTime.of(11, 0), Status.NEW);

    CandidateSlotDto candidateSlotDto = new CandidateSlotDto(
        LocalDateTime.of(LocalDate.of(year, Month.OCTOBER, 12), LocalTime.of(9, 30)),
        LocalDateTime.of(LocalDate.of(year, Month.OCTOBER, 12), LocalTime.of(11, 0))
    );

    assertThat(candidateSlotDto).isNotNull();
    assertThat(interviewerSlotDto).isNotNull();
  }

  @Autowired
  private InterviewerService interviewerService;
  @Autowired
  private CandidateService candidateService;

  @Test
  void interviewerSlotTests() {
    System.out.println("STARTED TEST");

    List<InterviewerSlotDto> dtos = new ArrayList<>(
        Arrays.asList(
            new InterviewerSlotDto(0, DayOfWeek.MONDAY,
                LocalTime.of(9, 30), LocalTime.of(11, 0), Status.NEW),

            new InterviewerSlotDto(2, DayOfWeek.TUESDAY,
                LocalTime.of(9, 30), LocalTime.of(11, 0), Status.NEW),

            new InterviewerSlotDto(1, DayOfWeek.WEDNESDAY,
                LocalTime.of(9, 30), LocalTime.of(11, 0), Status.NEW),

            new InterviewerSlotDto(1, DayOfWeek.THURSDAY,
                LocalTime.of(9, 30), LocalTime.of(11, 0), Status.NEW)
        )
    );

    System.out.println(dtos);

    interviewerService.registerAll(dtos);

    List<InterviewerSlotDto> dtosGot = interviewerService.findAll();
    System.out.println(dtosGot);

    assertThat(dtos).hasSize(4);
  }

  @Test
  void candidateSlotTests() {
    System.out.println("STARTED TEST");

    int year = Calendar.getInstance().get(Calendar.YEAR);
    List<CandidateSlotDto> dtos = new ArrayList<>(
        Arrays.asList(
            new CandidateSlotDto(
                LocalDateTime.of(LocalDate.of(year, Month.OCTOBER, 12), LocalTime.of(9, 30)),
                LocalDateTime.of(LocalDate.of(year, Month.OCTOBER, 12), LocalTime.of(11, 0)) ),

            new CandidateSlotDto(
                LocalDateTime.of(LocalDate.of(year, Month.OCTOBER, 13), LocalTime.of(9, 30)),
                LocalDateTime.of(LocalDate.of(year, Month.OCTOBER, 13), LocalTime.of(11, 0)) ),

            new CandidateSlotDto(
                LocalDateTime.of(LocalDate.of(year, Month.OCTOBER, 14), LocalTime.of(9, 30)),
                LocalDateTime.of(LocalDate.of(year, Month.OCTOBER, 14), LocalTime.of(11, 0)) ),

            new CandidateSlotDto(
                LocalDateTime.of(LocalDate.of(year, Month.OCTOBER, 15), LocalTime.of(9, 30)),
                LocalDateTime.of(LocalDate.of(year, Month.OCTOBER, 15), LocalTime.of(11, 0)) )
        )
    );

    candidateService.registerAll(dtos);

    List<CandidateSlotDto> dtosGot = candidateService.findAll();
    System.out.println(dtosGot);

    assertThat(dtos).hasSize(4);
  }

}
