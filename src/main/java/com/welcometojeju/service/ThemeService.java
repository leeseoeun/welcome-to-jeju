package com.welcometojeju.service;

import com.welcometojeju.domain.Theme;
import com.welcometojeju.dto.ThemeDTO;

import java.util.List;

public interface ThemeService {

  List<ThemeDTO> getAllPublicThemes();
  List<ThemeDTO> getAllShareThemes();

//  default Theme dtoToEntity(ThemeDTO themeDTO) {
//    Theme theme = Theme.builder()
//        .no(themeDTO.getNo())
//        .title(themeDTO.getTitle())
//        .user(themeDTO.getUser())
//        .categoryNo(themeDTO.getCategoryNo())
//        .isPublic(themeDTO.getIsPublic())
//        .isShare(themeDTO.getIsShare())
//        .emoji(themeDTO.getEmoji())
//        .viewCount(themeDTO.getViewCount())
//        .build();
//
//    return theme;
//  }

  default ThemeDTO entityToDto(Theme theme) {
    ThemeDTO themeDTO = ThemeDTO.builder()
        .no(theme.getNo())
        .title(theme.getTitle())
        .user(theme.getUser())
        .categoryNo(theme.getCategoryNo())
        .isPublic(theme.getIsPublic())
        .isShare(theme.getIsShare())
        .emoji(theme.getEmoji())
        .viewCount(theme.getViewCount())
        .build();

    return themeDTO;
  }

}
