package com.intellias.intellistart.interviewplanning.controllers;

import com.intellias.intellistart.interviewplanning.controllers.dto.CandidateSlotDto;
import com.intellias.intellistart.interviewplanning.models.CandidateSlot;
import com.intellias.intellistart.interviewplanning.services.CandidateService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Candidate controller.
 */
@RestController
@RequestMapping("/candidates")
public class CandidateController {

  public final CandidateService candidateService;
  public final ModelMapper mapper;

  @Autowired
  public CandidateController(CandidateService candidateService, ModelMapper mapper) {
    this.candidateService = candidateService;
    this.mapper = mapper;
  }

  /**
   * Test generating of Candidate time slots.
   * /candidates/addSlots

   * @return string status
   */
  @GetMapping("/addSlots")
  public String addSlots() {

    int year = Calendar.getInstance().get(Calendar.YEAR);
    List<CandidateSlot> slots = new ArrayList<>(
        Arrays.asList(
            new CandidateSlot(
                LocalDateTime.of(LocalDate.of(year, Month.OCTOBER, 12), LocalTime.of(9, 30)),
                LocalDateTime.of(LocalDate.of(year, Month.OCTOBER, 12), LocalTime.of(11, 0))
            ),
            new CandidateSlot(
                LocalDateTime.of(LocalDate.of(year, Month.OCTOBER, 13), LocalTime.of(9, 30)),
                LocalDateTime.of(LocalDate.of(year, Month.OCTOBER, 13), LocalTime.of(11, 0))
            ),
            new CandidateSlot(
                LocalDateTime.of(LocalDate.of(year, Month.OCTOBER, 14), LocalTime.of(9, 30)),
                LocalDateTime.of(LocalDate.of(year, Month.OCTOBER, 14), LocalTime.of(11, 0))
            ),
            new CandidateSlot(
                LocalDateTime.of(LocalDate.of(year, Month.OCTOBER, 15), LocalTime.of(9, 30)),
                LocalDateTime.of(LocalDate.of(year, Month.OCTOBER, 15), LocalTime.of(11, 0)))
        )
    );
    candidateService.registerAll(slots);

    return "OK";
  }

  /**
   * Test getting of Candidate time slots.
   * /candidates/getSlots

   * @return list of Candidate slots in DB
   */
  @GetMapping("/getSlots")
  public List<CandidateSlotDto> getSlots() {
    return candidateService.findAll().stream()
        .map(e -> mapper.map(e, CandidateSlotDto.class))
        .collect(Collectors.toList());
  }

  /**
   * Test deleting of Candidate time slots.
   * /candidates/delSlots

   * @return list of Candidate slots in DB
   */
  @GetMapping("/delSlots")
  public List<CandidateSlotDto> delSlots() {
    candidateService.deleteAll();
    return candidateService.findAll().stream()
        .map(e -> mapper.map(e, CandidateSlotDto.class))
        .collect(Collectors.toList());
  }

}
