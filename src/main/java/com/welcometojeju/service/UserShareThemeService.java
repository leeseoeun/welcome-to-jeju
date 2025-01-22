package com.welcometojeju.service;

import com.welcometojeju.domain.*;
import com.welcometojeju.dto.UserShareThemeDTO;

public interface UserShareThemeService {

  void createUserShareTheme(UserShareThemeDTO userShareThemeDTO);

  default UserShareTheme dtoToEntity(User user, Theme theme) {
    UserShareTheme userShareTheme = UserShareTheme.builder()
        .user(user)
        .theme(theme)
        .build();

    return userShareTheme;
  }

}
