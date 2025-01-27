package com.welcometojeju.service;

import com.welcometojeju.domain.Place;
import com.welcometojeju.domain.User;
import com.welcometojeju.dto.PlaceDTO;
import com.welcometojeju.dto.ThemeDTO;
import com.welcometojeju.dto.ThemePlaceDTO;
import com.welcometojeju.dto.UserShareThemeDTO;
import com.welcometojeju.repository.PlaceRepository;
import com.welcometojeju.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class PlaceServiceImpl implements PlaceService {

  private final PlaceRepository placeRepository;
  private final UserRepository userRepository;
  private final ThemeService themeService;
  private final ThemePlaceService themePlaceService;
  private final UserShareThemeService userShareThemeService;

  @Override
  public Integer createPlace(PlaceDTO placeDTO, User user) {
    Place place = dtoToEntity(placeDTO, user);

    Integer no = placeRepository.save(place).getNo();

    return no;
  }

  @Override
  public Integer createPlaceAndRelations(PlaceDTO placeDTO) {
    Integer no = null;
    Integer placeNo = placeDTO.getNo();
    Integer userNo = placeDTO.getUserNo();
    Integer themeNo = placeDTO.getThemeNo();

    Optional<User> result = userRepository.findById(userNo);
    User user = result.orElseThrow();

    // 1. Place 저장
    if (!placeRepository.existsByNo(placeNo)) {
      no = createPlace(placeDTO, user);
    }

    // 2. Theme-Place 관계 저장
    if (!themePlaceService.existsByThemeNoAndPlaceNo(themeNo, placeNo)) {
      ThemePlaceDTO themePlaceDTO = new ThemePlaceDTO(themeNo, placeNo, placeDTO);
      themePlaceService.createThemePlace(themePlaceDTO);
    }

    // 3. User-ShareTheme 관계 저장
    ThemeDTO theme = themeService.getThemeByNo(themeNo);
    if (theme.getIsShare() == 1 && userNo != theme.getUserNo() && !userShareThemeService.existsByUserNoAndThemeNo(userNo, themeNo)) {
      UserShareThemeDTO userShareThemeDTO = new UserShareThemeDTO(userNo, themeNo);
      userShareThemeService.createUserShareTheme(userShareThemeDTO);
    }

    return no;
  }

  @Transactional
  @Override
  public void deletePlaceAndRelations(Integer no, Integer themeNo, Integer userNo) {
    // 1. Theme-Place 관계 삭제
    themePlaceService.deleteThemePlace(themeNo, no);

    // 2. User-ShareTheme 관계 삭제 (유저가 테마에 등록한 다른 장소가 없을 경우)
    if (!themePlaceService.existsByThemeNoAndUserNo(themeNo, userNo)) {
      userShareThemeService.deleteUserShareTheme(userNo, themeNo);
    }
  }

}
