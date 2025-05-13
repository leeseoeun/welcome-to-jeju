package com.welcometojeju.service;

import com.welcometojeju.domain.Theme;
import com.welcometojeju.domain.User;
import com.welcometojeju.dto.PlaceDTO;
import com.welcometojeju.dto.ThemeDTO;
import com.welcometojeju.dto.ThemePlaceDTO;
import com.welcometojeju.repository.ThemeRepository;
import com.welcometojeju.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class ThemeServiceImpl implements ThemeService {

  private final ThemeRepository themeRepository;
  private final UserRepository userRepository;

  @Override
  public Integer createTheme(ThemeDTO themeDTO) {
    Optional<User> result = userRepository.findById(themeDTO.getUserNo());

    User user = result.orElseThrow();

    Theme theme = dtoToEntity(themeDTO, user);

    Integer no = themeRepository.save(theme).getNo();

    return no;
  }

  @Override
  public void updateViewCount(Integer no) {
    Optional<Theme> result = themeRepository.findById(no);

    Theme theme = result.orElseThrow();

    theme.incrementViewCount();

    themeRepository.save(theme);
  }

  @Override
  public void deleteTheme(Integer no) {
    themeRepository.deleteById(no);
  }

  @Override
  public ThemeDTO getThemeByNo(Integer no) {
    Optional<Theme> result = themeRepository.findById(no);

    Theme theme = result.orElseThrow();

    ThemeDTO themeDTO = entityToDto(theme);

    return themeDTO;
  }

  @Override
  public ThemeDTO getThemeWithPlacesByNo(Integer no, Integer userNo) {
    Optional<Theme> result = themeRepository.findWithPlacesByNo(no);
    Theme theme = result.orElseThrow();
    ThemeDTO themeDTO = entityToDto(theme);

    List<ThemePlaceDTO> placeList = theme.getPlaceList().stream()
        .map(pl -> {
          // PlaceDTO 생성
          PlaceDTO placeDTO = PlaceDTO.builder()
              .no(pl.getPlace().getNo())
              .name(pl.getPlace().getName())
              .address(pl.getPlace().getAddress())
              .phone(pl.getPlace().getPhone())
              .x(pl.getPlace().getX())
              .y(pl.getPlace().getY())
              .userNo(pl.getPlace().getUser().getNo())
              .build();

          // 조건에 따라 isDelete 설정
          if (userNo != null && userNo.equals(pl.getPlace().getUser().getNo())) {
            placeDTO.setIsDelete(1); // isDelete 값을 1로 설정
          } else {
            placeDTO.setIsDelete(0);
          }

          // ThemePlaceDTO 생성
          return ThemePlaceDTO.builder()
              .themeNo(pl.getTheme().getNo())
              .placeNo(pl.getPlace().getNo())
              .placeDTO(placeDTO)
              .build();
        })
        .collect(Collectors.toList());

    themeDTO.setPlaceList(placeList);

    return themeDTO;
  }

  @Override
  public List<ThemeDTO> getAllPublicThemes() {
    List<Theme> result = themeRepository.findAllByIsPublic(1);

    List<ThemeDTO> themes = result.stream()
        .map(theme -> entityToDto(theme)).collect(Collectors.toList());

    return themes;
  }

  @Override
  public List<ThemeDTO> getAllCollaborateThemes() {
    List<Theme> result = themeRepository.findAllByIsShare(1);

    List<ThemeDTO> themes = result.stream()
        .map(theme -> entityToDto(theme)).collect(Collectors.toList());

    return themes;
  }

  @Override
  public List<ThemeDTO> getAllPublicThemesByUserNo(Integer userNo) {
    List<Theme> result = themeRepository.findAllByUserNoAndIsPublic(userNo, 1);

    List<ThemeDTO> themes = result.stream()
        .map(theme -> entityToDto(theme)).collect(Collectors.toList());

    return themes;
  }

  @Override
  public List<ThemeDTO> getAllPrivateThemesByUserNo(Integer userNo) {
    List<Theme> result = themeRepository.findAllByUserNoAndIsPublic(userNo, 0);

    List<ThemeDTO> themes = result.stream()
        .map(theme -> entityToDto(theme)).collect(Collectors.toList());

    return themes;
  }

  @Override
  public List<ThemeDTO> getAllCollaborateThemesByUserNo(Integer userNo) {
    List<Theme> result = themeRepository.findAllByUserNoAndIsShare(userNo, 1);

    List<ThemeDTO> themes = result.stream()
        .map(theme -> entityToDto(theme)).collect(Collectors.toList());

    return themes;
  }

  @Override
  public List<ThemeDTO> getAllParticipateThemesByUserNo(Integer userNo) {
    List<Theme> result = themeRepository.findAllParticipateThemesByUserNo(userNo);

    List<ThemeDTO> themes = result.stream()
        .map(theme -> entityToDto(theme)).collect(Collectors.toList());

    return themes;
  }

  @Override
  public List<ThemeDTO> getAllPublicThemesByKeyword(String keyword) {
    List<Theme> result = themeRepository.findAllByTitleContainingAndIsPublic(keyword, 1);

    List<ThemeDTO> themes = result.stream()
        .map(theme -> entityToDto(theme)).collect(Collectors.toList());

    return themes;
  }

  @Override
  public List<ThemeDTO> getAllCollaborateThemesByKeyword(String keyword) {
    List<Theme> result = themeRepository.findAllByTitleContainingAndIsShare(keyword, 1);

    List<ThemeDTO> themes = result.stream()
        .map(theme -> entityToDto(theme)).collect(Collectors.toList());

    return themes;
  }

  @Override
  @Transactional(readOnly = true) // LAZY 관계(user.nickname)에 접근해야 되기 때문에
  public List<ThemeDTO> getTop3PublicThemesByViewCount() {
    List<Theme> result = themeRepository.findTop3ByIsPublicOrderByViewCountDesc(1);

    List<ThemeDTO> themes = result.stream()
        .map(theme -> entityToDto(theme)).collect(Collectors.toList());

    return themes;
  }

  @Override
  @Transactional(readOnly = true) // LAZY 관계(user.nickname)에 접근해야 되기 때문에
  public List<ThemeDTO> getTop3CollaborateThemesByViewCount() {
    List<Theme> result = themeRepository.findTop3ByIsShareOrderByViewCountDesc(1);

    List<ThemeDTO> themes = result.stream()
        .map(theme -> entityToDto(theme)).collect(Collectors.toList());

    return themes;
  }

}
