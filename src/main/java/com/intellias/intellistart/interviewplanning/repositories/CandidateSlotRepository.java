package com.intellias.intellistart.interviewplanning.repositories;

import com.intellias.intellistart.interviewplanning.models.CandidateSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Candidate slot repository.
 */
@Repository
public interface CandidateSlotRepository extends JpaRepository<CandidateSlot, Long> {

}
