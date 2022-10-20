package com.intellias.intellistart.interviewplanning.models;

import com.intellias.intellistart.interviewplanning.models.enums.Role;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * User class.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "facebook_id", nullable = false)
  private Long facebookId;

  @Column(name = "email", nullable = false)
  private String email;

  @Column(name = "role", nullable = false)
  private Role role;

  /**
   * User class constructor.
   */
  public User(Long facebookId, String email, Role role) {
    this.facebookId = facebookId;
    this.email = email;
    this.role = role;
  }
}
