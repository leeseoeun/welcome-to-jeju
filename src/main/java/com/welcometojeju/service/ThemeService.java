package com.welcometojeju.service;

import com.welcometojeju.domain.Theme;
import com.welcometojeju.domain.User;
import com.welcometojeju.dto.ThemeDTO;

import java.util.List;

public interface ThemeService {

  Integer createTheme(ThemeDTO themeDTO);

  void updateViewCount(Integer no);

  void deleteTheme(Integer no);

  ThemeDTO getThemeByNo(Integer no);
  ThemeDTO getThemeWithPlacesByNo(Integer no, Integer userNo);

  List<ThemeDTO> getAllPublicThemes();
  List<ThemeDTO> getAllCollaborateThemes();

  List<ThemeDTO> getAllPublicThemesByUserNo(Integer userNo);
  List<ThemeDTO> getAllPrivateThemesByUserNo(Integer userNo);

  List<ThemeDTO> getAllCollaborateThemesByUserNo(Integer userNo);
  List<ThemeDTO> getAllParticipateThemesByUserNo(Integer userNo);

  List<ThemeDTO> getAllCollaborateThemesByKeyword(String keyword);
  List<ThemeDTO> getAllPublicThemesByKeyword(String keyword);

  default Theme dtoToEntity(ThemeDTO themeDTO, User user) {
    Theme theme = Theme.builder()
        .no(themeDTO.getNo())
        .title(themeDTO.getTitle())
        .user(user)
        .isPublic(themeDTO.getIsPublic())
        .isShare(themeDTO.getIsShare())
        .emoji(themeDTO.getEmoji())
        .viewCount(themeDTO.getViewCount())
        .build();

    return theme;
  }

  default ThemeDTO entityToDto(Theme theme) {
    ThemeDTO themeDTO = ThemeDTO.builder()
        .no(theme.getNo())
        .title(theme.getTitle())
        .userNo(theme.getUser().getNo())
        .isPublic(theme.getIsPublic())
        .isShare(theme.getIsShare())
        .emoji(theme.getEmoji())
        .viewCount(theme.getViewCount())
        .userNickname(theme.getUser().getNickname())
        .build();

    return themeDTO;
  }

}
