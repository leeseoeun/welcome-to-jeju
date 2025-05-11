package com.welcometojeju.repository;

import com.welcometojeju.domain.Place;
import com.welcometojeju.domain.Theme;
import com.welcometojeju.domain.ThemePlace;
import com.welcometojeju.domain.User;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class PlaceRepositoryTests {

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private ThemeRepository themeRepository;
  @Autowired
  private PlaceRepository placeRepository;
  @Autowired
  private ThemePlaceRepository themePlaceRepository;

  @Test
  public void testCreateTheme() {
    Optional<User> userResult = userRepository.findById(1);
    User user = userResult.orElseThrow();

    IntStream.rangeClosed(1, 3000).forEach(i -> {
      Place place = Place.builder()
          .no(i)
          .name("name" + i)
          .address("address" + i)
          .phone("phone" + i)
          .x(String.valueOf(126.51586040620134 + (i * 0.0001)))
          .y(String.valueOf(33.23751957595788 + (i * 0.0001)))
          .registerCount(0)
          .user(user)
          .build();

      Place placeResult = placeRepository.save(place);

      placeResult.incrementRegisterCount();

      Optional<Theme> themeResult = themeRepository.findById(1);
      Theme theme = themeResult.orElseThrow();

      ThemePlace themePlace = ThemePlace.builder()
          .theme(theme)
          .place(placeResult)
          .build();

      themePlaceRepository.save(themePlace);
    });
  }

}
