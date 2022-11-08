package com.intellias.intellistart.interviewplanning.repositories;

import com.intellias.intellistart.interviewplanning.models.Interviewer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Interviewer entity repository.
 */
@Repository
public interface InterviewerRepository extends JpaRepository<Interviewer, Long> {

//  @Query("SELECT r FROM Interviewer r where r.user.facebookId = :facebookId")
//  Interviewer findByFacebookId(Long facebookId);

}
