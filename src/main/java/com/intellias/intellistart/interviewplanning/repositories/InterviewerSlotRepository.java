package com.intellias.intellistart.interviewplanning.repositories;

import com.intellias.intellistart.interviewplanning.models.InterviewerSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interviewer slot repository.
 */
@Repository
public interface InterviewerSlotRepository extends JpaRepository<InterviewerSlot, Long> {

}
