package com.welcometojeju.service;

import com.welcometojeju.domain.Place;
import com.welcometojeju.domain.User;
import com.welcometojeju.dto.*;
import com.welcometojeju.redis.RedisCacheKey;
import com.welcometojeju.repository.PlaceRepository;
import com.welcometojeju.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlaceServiceImpl implements PlaceService {

  private final PlaceRepository placeRepository;
  private final UserRepository userRepository;
  private final ThemeService themeService;
  private final ThemePlaceService themePlaceService;
  private final UserShareThemeService userShareThemeService;
  private final NotificationService notificationService;
  private final RedisService redisService;

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

    Optional<User> userResult = userRepository.findById(userNo);
    User user = userResult.orElseThrow();

    ThemeDTO themeDTO = themeService.getThemeByNo(themeNo);

    // 1. Place 저장
    if (!placeRepository.existsByNo(placeNo)) {
      no = createPlace(placeDTO, user);
    }

    // 2. Theme-Place 관계 저장
    if (!themePlaceService.existsByThemeNoAndPlaceNo(themeNo, placeNo)) {
      Optional<Place> placeResult = placeRepository.findById(placeNo);
      Place place = placeResult.orElseThrow();
      place.incrementRegisterCount();

      ThemePlaceDTO themePlaceDTO = new ThemePlaceDTO(themeNo, placeNo, placeDTO);
      themePlaceService.createThemePlace(themePlaceDTO);

      // 장소 등록 알림 발신
      notificationService.notifyPlaceRegistered(new NotificationDTO(themeDTO.getTitle(), themeDTO.getUserNo()));

      // 캐시 무효화
      redisService.evictCache(RedisCacheKey.TOP_PLACES);
    }

    // 3. User-ShareTheme 관계 저장
    if (themeDTO.getIsShare() == 1 && userNo != themeDTO.getUserNo() && !userShareThemeService.existsByUserNoAndThemeNo(userNo, themeNo)) {
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

  @Override
  public List<PlaceDTO> getTop3PlacesByRegisterCount() {
    // 캐시에서 먼저 조회
    List<PlaceDTO> cached = redisService.getListCache(RedisCacheKey.TOP_PLACES, PlaceDTO.class);

    if (cached != null) return cached;

    // 없으면 데이터베이스에서 조회
    List<Place> result = placeRepository.findTop3ByOrderByRegisterCountDesc();

    List<PlaceDTO> places = result.stream()
        .map(place -> entityToDto(place)).collect(Collectors.toList());

    // 캐시에 저장
    redisService.setCache(RedisCacheKey.TOP_PLACES, places);

    return places;
  }

}
