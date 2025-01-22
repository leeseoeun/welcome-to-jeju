package com.welcometojeju.service;

import com.welcometojeju.domain.*;
import com.welcometojeju.dto.ThemePlaceDTO;
import com.welcometojeju.dto.UserShareThemeDTO;
import com.welcometojeju.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserShareThemeServiceImpl implements UserShareThemeService {

  private final UserShareThemeRepository userShareThemeRepository;
  private final UserRepository userRepository;
  private final ThemeRepository themeRepository;

  @Override
  public void createUserShareTheme(UserShareThemeDTO userShareThemeDTO) {
    Optional<User> userResult = userRepository.findById(userShareThemeDTO.getUserNo());
    User user = userResult.orElseThrow();

    Optional<Theme> themeResult = themeRepository.findById(userShareThemeDTO.getThemeNo());
    Theme theme = themeResult.orElseThrow();

    UserShareTheme userShareTheme = dtoToEntity(user, theme);

    userShareThemeRepository.save(userShareTheme);
  }

}
