package com.welcometojeju.repository;

import com.welcometojeju.domain.User;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class UserRepositoryTests {

  @Autowired
  private UserRepository userRepository;

  @Test
  public void testCreateUser() {
    IntStream.rangeClosed(1, 3).forEach(i -> {
      User user = User.builder()
          .email("email" + i + "@test.com")
          .password("0000")
          .nickname("nickname" + i)
          .isActive(1)
          .viewCount(0)
          .build();

      User result = userRepository.save(user);

      log.info(result);
    });
  }

  @Test
  public void testGetUserByNo() {
    Optional<User> result = userRepository.findById(2);

    User user = result.orElseThrow();

    log.info(user);
  }

}
