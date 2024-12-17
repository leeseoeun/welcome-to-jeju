package com.welcometojeju.repository;

import com.welcometojeju.domain.Theme;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Log4j2
public class ThemeRepositoryTests {

  @Autowired
  private ThemeRepository themeRepository;

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
