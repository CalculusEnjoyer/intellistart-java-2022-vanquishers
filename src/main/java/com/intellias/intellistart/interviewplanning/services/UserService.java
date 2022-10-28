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

  public void register(User user) {
    userRepository.save(user);
  }

  public User findUserByFacebookId(Long facebookId) {
    return userRepository.findUserByFacebookId(facebookId);
  }

}
