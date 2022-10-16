package com.intellias.intellistart.interviewplanning.repositories;

import com.intellias.intellistart.interviewplanning.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * User repository.
 */

public interface UserRepository extends JpaRepository<User, Long> {

}