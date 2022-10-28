package com.intellias.intellistart.interviewplanning.controllers;


import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.intellias.intellistart.interviewplanning.models.Booking;
import com.intellias.intellistart.interviewplanning.models.enums.Status;
import com.intellias.intellistart.interviewplanning.repositories.BookingRepository;
import com.intellias.intellistart.interviewplanning.services.BookingService;
import com.intellias.intellistart.interviewplanning.util.exceptions.BookingNotFoundException;
import java.time.LocalDateTime;
import java.time.Month;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc(addFilters = false)
class CoordinatorControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ModelMapper mapper;

  @Autowired
  private BookingRepository bookingRepository;

  @Autowired
  private BookingService bookingService;

  @Autowired
  private WebApplicationContext webApplicationContext;

  @Before("")
  public void setup() {
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
  }

  @Test
  void deleteBookingTest() throws Exception {
    Booking booking = new Booking(LocalDateTime.of(2015,
        Month.JULY, 29, 19, 30), LocalDateTime.of(2015,
        Month.JULY, 29, 21, 30), "check", "check", Status.BOOKED);
    bookingService.registerBooking(booking);
    booking = bookingService.getAllBookings().get(bookingService.getAllBookings().size() - 1);

    mockMvc.perform(delete("/bookings/{bookingId}", booking.getId()))
        .andExpect(status().isOk());

    try {
      bookingService.getBookingById(booking.getId());
      fail();
    } catch (BookingNotFoundException e) {
      assertNotNull(e);
    } catch (Exception e) {
      fail();
    }
  }
}
