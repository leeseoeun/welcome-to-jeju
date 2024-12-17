package com.welcometojeju.service;

import com.welcometojeju.domain.Theme;
import com.welcometojeju.dto.ThemeDTO;
import com.welcometojeju.repository.ThemeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class ThemeServiceImpl implements ThemeService {

  private final ThemeRepository themeRepository;

  @Override
  public List<ThemeDTO> getAllPublicThemes() {
    List<Theme> result = themeRepository.findAllByIsPublic(1);

    List<ThemeDTO> themes = result.stream()
        .map(theme -> entityToDto(theme)).collect(Collectors.toList());

    return themes;
  }

  @Override
  public List<ThemeDTO> getAllShareThemes() {
    List<Theme> result = themeRepository.findAllByIsShare(1);

    List<ThemeDTO> themes = result.stream()
        .map(theme -> entityToDto(theme)).collect(Collectors.toList());

    return themes;
  }
}
