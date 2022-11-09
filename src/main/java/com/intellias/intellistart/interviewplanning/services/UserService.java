package com.intellias.intellistart.interviewplanning.services;

import com.intellias.intellistart.interviewplanning.models.User;
import com.intellias.intellistart.interviewplanning.models.enums.Role;
import com.intellias.intellistart.interviewplanning.repositories.UserRepository;
import com.intellias.intellistart.interviewplanning.util.exceptions.UserNotFoundException;
import java.util.List;
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

  public void register(User user) {
    userRepository.save(user);
  }

  public void registerUserWithRole(User user, Role role){
    user.setRole(role);
    userRepository.save(user);
  }

  public User findUserByFacebookId(Long facebookId) {
    return userRepository.findByFacebookId(facebookId);
  }

  public User findUserByEmail(String email) {
    return userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
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
