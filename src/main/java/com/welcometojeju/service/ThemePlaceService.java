package com.welcometojeju.service;

import com.welcometojeju.domain.Place;
import com.welcometojeju.domain.Theme;
import com.welcometojeju.domain.ThemePlace;
import com.welcometojeju.dto.ThemePlaceDTO;

public interface ThemePlaceService {

  void createThemePlace(ThemePlaceDTO themePlaceDTO);

  void deleteThemePlace(Integer themeNo, Integer placeNo);

  boolean existsByThemeNoAndPlaceNo(Integer themeNo, Integer placeNo);
  boolean existsByThemeNoAndUserNo(Integer themeNo, Integer userNo);

  default ThemePlace dtoToEntity(Theme theme, Place place) {
    ThemePlace themePlace = ThemePlace.builder()
        .theme(theme)
        .place(place)
        .build();

    return themePlace;
  }

}
