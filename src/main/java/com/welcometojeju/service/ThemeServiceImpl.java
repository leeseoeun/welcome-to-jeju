package com.welcometojeju.service;

import com.welcometojeju.domain.Theme;
import com.welcometojeju.domain.User;
import com.welcometojeju.dto.ThemeDTO;
import com.welcometojeju.repository.ThemeRepository;
import com.welcometojeju.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

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
  public ThemeDTO getThemeByNo(Integer no) {
    Optional<Theme> result = themeRepository.findById(no);

    Theme theme = result.orElseThrow();

    ThemeDTO themeDTO = entityToDto(theme);

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
  public List<ThemeDTO> getAllCollaborateThemesThemes() {
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

  /* 수정 필요 */
  @Override
  public List<ThemeDTO> getAllCollaborateThemesByUserNo(Integer userNo) {
    List<Theme> result = themeRepository.findAllByUserNoAndIsShare(userNo, 1);

    List<ThemeDTO> themes = result.stream()
        .map(theme -> entityToDto(theme)).collect(Collectors.toList());

    return themes;
  }

  /* 수정 필요 */
  @Override
  public List<ThemeDTO> getAllParticipateThemesByUserNo(Integer userNo) {
    List<Theme> result = themeRepository.findAllByUserNoAndIsShare(userNo, 1);

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

}
