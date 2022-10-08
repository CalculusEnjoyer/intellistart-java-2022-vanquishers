package com.intellias.intellistart.interviewplanning;

import static org.assertj.core.api.Assertions.assertThat;

import com.intellias.intellistart.interviewplanning.services.InterviewerService;
import com.intellias.intellistart.interviewplanning.util.TimeSlotForm;
import java.time.DayOfWeek;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class InterviewPlanningApplicationTests {

  @Test
  void contextLoads() {
    var message = "Nothing to see here for now...";
    assertThat(message).isNotNull();
  }

  @Autowired
  private InterviewerService interviewerService;

  @Test
  void interviewerSlotMainScenario() {
    /*var slot = interviewerService.createSlot(
        1,
        DayOfWeek.FRIDAY,
        LocalTime.of(9, 0), // 09:00
        LocalTime.of(17, 0) // 17:00
    );
    assertThat(slot).isNotNull();

     */
  }

  @Test
  void interviewerSlotWithFormMainScenario() {
    /*
    var slotForm = TimeSlotForm.builder()
        .from("9:00")
        .to("17:00");
    var slot = interviewerService.createSlot(slotForm);
    assertThat(slot).isNotNull();

     */
  }

}
