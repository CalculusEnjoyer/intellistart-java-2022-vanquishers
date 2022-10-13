package com.intellias.intellistart.interviewplanning;

import static org.assertj.core.api.Assertions.assertThat;

import com.intellias.intellistart.interviewplanning.controllers.dto.CandidateSlotDto;
import com.intellias.intellistart.interviewplanning.controllers.dto.InterviewerSlotDto;
import com.intellias.intellistart.interviewplanning.models.CandidateSlot;
import com.intellias.intellistart.interviewplanning.models.InterviewerSlot;
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
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class InterviewPlanningApplicationTests {

  @Test
  void contextLoads() {
    int year = Calendar.getInstance().get(Calendar.YEAR);
    InterviewerSlot interviewerSlotDto = new InterviewerSlot(0, DayOfWeek.MONDAY,
        LocalTime.of(9, 30), LocalTime.of(11, 0), Status.NEW);

    CandidateSlot candidateSlotDto = new CandidateSlot(
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
  @Autowired
  private ModelMapper mapper;

  @Test
  void interviewerSlotTests() {
    List<InterviewerSlot> slots = new ArrayList<>(
        Arrays.asList(
            new InterviewerSlot(0, DayOfWeek.MONDAY,
                LocalTime.of(9, 30), LocalTime.of(11, 0), Status.NEW),

            new InterviewerSlot(2, DayOfWeek.TUESDAY,
                LocalTime.of(9, 30), LocalTime.of(11, 0), Status.NEW),

            new InterviewerSlot(1, DayOfWeek.WEDNESDAY,
                LocalTime.of(9, 30), LocalTime.of(11, 0), Status.NEW),

            new InterviewerSlot(1, DayOfWeek.THURSDAY,
                LocalTime.of(9, 30), LocalTime.of(11, 0), Status.NEW)
        )
    );

    interviewerService.registerAll(slots);

    List<InterviewerSlotDto> slotsGot = interviewerService.findAll().stream()
        .map(e -> mapper.map(e, InterviewerSlotDto.class))
        .collect(Collectors.toList());

    assertThat(slotsGot.size()).isNotZero();
    assertThat(slotsGot.size() % slots.size()).isZero();
  }

  @Test
  void candidateSlotTests() {
    int year = Calendar.getInstance().get(Calendar.YEAR);
    List<CandidateSlot> slots = new ArrayList<>(
        Arrays.asList(
            new CandidateSlot(
                LocalDateTime.of(LocalDate.of(year, Month.OCTOBER, 12), LocalTime.of(9, 30)),
                LocalDateTime.of(LocalDate.of(year, Month.OCTOBER, 12), LocalTime.of(11, 0)) ),

            new CandidateSlot(
                LocalDateTime.of(LocalDate.of(year, Month.OCTOBER, 13), LocalTime.of(9, 30)),
                LocalDateTime.of(LocalDate.of(year, Month.OCTOBER, 13), LocalTime.of(11, 0)) ),

            new CandidateSlot(
                LocalDateTime.of(LocalDate.of(year, Month.OCTOBER, 14), LocalTime.of(9, 30)),
                LocalDateTime.of(LocalDate.of(year, Month.OCTOBER, 14), LocalTime.of(11, 0)) ),

            new CandidateSlot(
                LocalDateTime.of(LocalDate.of(year, Month.OCTOBER, 15), LocalTime.of(9, 30)),
                LocalDateTime.of(LocalDate.of(year, Month.OCTOBER, 15), LocalTime.of(11, 0)) )
        )
    );

    candidateService.registerAll(slots);

    List<CandidateSlotDto> slotsGot = candidateService.findAll().stream()
        .map(e -> mapper.map(e, CandidateSlotDto.class))
        .collect(Collectors.toList());

    assertThat(slotsGot.size()).isNotZero();
    assertThat(slotsGot.size() % slots.size()).isZero();
  }


}
