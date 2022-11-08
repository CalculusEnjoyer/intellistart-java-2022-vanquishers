package com.intellias.intellistart.interviewplanning.repositories;

import com.intellias.intellistart.interviewplanning.models.Candidate;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Candidate entity repository.
 */
@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Long> {

  //  @Query("SELECT r FROM Candidate r where r.user.facebookId = :facebookId")
  //  Candidate findByFacebookId(@Param("facebookId") Long id);

  Optional<Candidate> findByUserId(Long userId);
}
