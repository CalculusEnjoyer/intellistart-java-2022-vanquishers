package com.intellias.intellistart.interviewplanning.repositories;

import com.intellias.intellistart.interviewplanning.models.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * User repository.
 */

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findUserByEmail(String email);
}