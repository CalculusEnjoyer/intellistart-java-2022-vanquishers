package com.intellias.intellistart.interviewplanning.repositories;

import com.intellias.intellistart.interviewplanning.models.Interviewer;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Interviewer entity repository.
 */
@Repository
public interface InterviewerRepository extends JpaRepository<Interviewer, Long> {

  Optional<Interviewer> findByUserId(Long userId);
}
