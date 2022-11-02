package com.intellias.intellistart.interviewplanning.repositories;

import com.intellias.intellistart.interviewplanning.models.User;
import java.util.Optional;
import javax.swing.text.html.Option;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * User repository.
 */

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  User findByFacebookId(Long facebookId);

  @Query("SELECT user FROM User user WHERE user.email LIKE %?1%")
  Optional<User> findByEmail(String email);

}