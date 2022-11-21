package com.intellias.intellistart.interviewplanning.models;

import com.intellias.intellistart.interviewplanning.models.enums.Role;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
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

  @Column(name = "email", nullable = false)
  @Email
  private String email;

  @Column(name = "role", nullable = false)
  private Role role;

  public User(String email, Role role) {
    this.email = email;
    this.role = role;
  }
}
