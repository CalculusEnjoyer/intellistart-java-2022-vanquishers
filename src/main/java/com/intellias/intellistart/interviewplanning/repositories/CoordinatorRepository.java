package com.intellias.intellistart.interviewplanning.repositories;

import com.intellias.intellistart.interviewplanning.models.Coordinator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Coordinator entity repository.
 */
@Repository
public interface CoordinatorRepository extends JpaRepository<Coordinator, Long> {

}
