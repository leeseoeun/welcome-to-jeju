package com.welcometojeju.service;

import com.welcometojeju.domain.Place;
import com.welcometojeju.domain.Theme;
import com.welcometojeju.domain.ThemePlace;
import com.welcometojeju.dto.ThemePlaceDTO;
import com.welcometojeju.repository.PlaceRepository;
import com.welcometojeju.repository.ThemePlaceRepository;
import com.welcometojeju.repository.ThemeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class ThemePlaceServiceImpl implements ThemePlaceService {

  private final ThemePlaceRepository themePlaceRepository;
  private final ThemeRepository themeRepository;
  private final PlaceRepository placeRepository;

  @Override
  public void createThemePlace(ThemePlaceDTO themePlaceDTO) {
    Optional<Theme> themeResult = themeRepository.findById(themePlaceDTO.getThemeNo());
    Theme theme = themeResult.orElseThrow();

    Optional<Place> placeResult = placeRepository.findById(themePlaceDTO.getPlaceNo());
    Place place = placeResult.orElseThrow();

    ThemePlace themePlace = dtoToEntity(theme, place);

    themePlaceRepository.save(themePlace);
  }

  @Override
  public boolean existsByThemeNoAndPlaceNo(Integer themeNo, Integer placeNo) {
    return themePlaceRepository.existsByThemeNoAndPlaceNo(themeNo, placeNo);
  }

}
