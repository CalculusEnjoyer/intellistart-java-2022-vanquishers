package com.intellias.intellistart.interviewplanning.models.security;

import com.intellias.intellistart.interviewplanning.models.User;
import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Getter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Facebook User details class.
 */
@ToString
@Getter
public class FacebookUserDetails implements UserDetails {

  User user;

  public FacebookUserDetails(User user) {
    this.user = user;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return Stream.of(new HashSet<>().add(user.getRole()))
        .map(role -> new SimpleGrantedAuthority("ROLE_" + user.getRole()))
        .collect(Collectors.toSet());
  }

  @Override
  public String getPassword() {
    return null;
  }

  @Override
  public String getUsername() {
    return null;
  }

  @Override
  public boolean isAccountNonExpired() {
    return false;
  }

  @Override
  public boolean isAccountNonLocked() {
    return false;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return false;
  }

  @Override
  public boolean isEnabled() {
    return false;
  }
}
