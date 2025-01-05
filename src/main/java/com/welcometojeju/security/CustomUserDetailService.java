package com.welcometojeju.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class CustomUserDetailService implements UserDetailsService {

  private final PasswordEncoder passwordEncoder;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    log.info("[loadUserByUsername > username] " + username);

    UserDetails userDetails = User.builder()
        .username("email1@test.com")
        .password(passwordEncoder.encode("0000"))
        .authorities("ROLE_USER")
        .build();

    return userDetails;
  }
}
