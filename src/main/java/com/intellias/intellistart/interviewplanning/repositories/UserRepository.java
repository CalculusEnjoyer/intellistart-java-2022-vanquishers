package com.intellias.intellistart.interviewplanning.repositories;

import com.intellias.intellistart.interviewplanning.models.User;
import com.intellias.intellistart.interviewplanning.models.enums.Role;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * User repository.
 */

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  User findByFacebookId(Long facebookId);

  Optional<User> findByEmail(String email);

  List<User> findAllByRole(Role role);


}