package com.intellias.intellistart.interviewplanning.services;

import com.intellias.intellistart.interviewplanning.models.User;
import com.intellias.intellistart.interviewplanning.repositories.UserRepository;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * User service.
 */
@Service
@Transactional
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

  public List<User> findAllUsersByRole(Role role) {
    return userRepository.findAllByRole(role);
  }

  public void deleteUserById(Long id) {
    userRepository.deleteById(id);
  }

  public User findUserById(Long id) {
    return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
  }
}
