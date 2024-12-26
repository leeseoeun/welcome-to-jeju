package com.welcometojeju.repository;

import com.welcometojeju.domain.Theme;
import com.welcometojeju.domain.User;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class ThemeRepositoryTests {

  @Autowired
  private ThemeRepository themeRepository;
  @Autowired
  private UserRepository userRepository;

  @Test
  public void testCreateTheme() {
    Optional<User> userResult = userRepository.findById(2);
    User user = userResult.orElseThrow();

    IntStream.rangeClosed(1, 3).forEach(i -> {
      Theme theme = Theme.builder()
          .title("title" + i)
          .user(user)
          .isPublic(1)
          .isShare(0)
          .emoji("emoji" + i)
          .viewCount(0)
          .build();

      Theme result = themeRepository.save(theme);

      log.info(result);
    });

    IntStream.rangeClosed(1, 3).forEach(i -> {
      Theme theme = Theme.builder()
          .title("title" + i + i)
          .user(user)
          .isPublic(0)
          .isShare(1)
          .emoji("emoji" + i + i)
          .viewCount(0)
          .build();

      Theme result = themeRepository.save(theme);

      log.info(result);
    });

    IntStream.rangeClosed(1, 3).forEach(i -> {
      Theme theme = Theme.builder()
          .title("title" + i + i + i)
          .user(user)
          .isPublic(0)
          .isShare(0)
          .emoji("emoji" + i + i + i)
          .viewCount(0)
          .build();

      Theme result = themeRepository.save(theme);

      log.info(result);
    });
  }

  @Test
  public void testGetThemeByNo() {
    Optional<Theme> result = themeRepository.findById(2);

    Theme theme = result.orElseThrow();

    log.info(theme);
  }

  @Test
  public void testGetAllPublicThemes() {
    List<Theme> themes = themeRepository.findAllByIsPublic(1);

    log.info(themes);
  }

  @Test
  public void testGetAllShareThemes() {
    List<Theme> themes = themeRepository.findAllByIsShare(1);

    log.info(themes);
  }

}
