package com.intellias.intellistart.interviewplanning.services;

import com.intellias.intellistart.interviewplanning.models.User;
import com.intellias.intellistart.interviewplanning.models.enums.Role;
import com.intellias.intellistart.interviewplanning.repositories.UserRepository;
import java.util.Optional;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * User service.
 */
@Service
@Transactional
@Slf4j
public class UserService {

  private final UserRepository userRepository;

  @Autowired
  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public Optional<User> findUserByEmail(String email) {
    return userRepository.findUserByEmail(email);
  }

  public User registerUser(User user, Role role) {
    user.setRole(role);
    return userRepository.save(user);
  }

}
