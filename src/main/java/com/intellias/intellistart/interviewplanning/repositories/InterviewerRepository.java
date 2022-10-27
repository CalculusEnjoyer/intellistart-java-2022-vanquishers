package com.intellias.intellistart.interviewplanning.repositories;

import com.intellias.intellistart.interviewplanning.models.Interviewer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interviewer entity repository.
 */
@Repository
public interface InterviewerRepository extends JpaRepository<Interviewer, Long> {

}
