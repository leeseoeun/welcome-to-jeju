package com.welcometojeju.controller;

import com.welcometojeju.dto.ThemeDTO;
import com.welcometojeju.service.ThemeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor  // 초기화되지 않은 final 필드에 대해 생성자 생성 (의존성 주입)
@Log4j2
public class ThemeController {

  private final ThemeService themeService;

  // 전체 테마 리스트 (공개, 공유)
  @GetMapping("/themes")
  public void getAllThemes(Model model) {
    List<ThemeDTO> publicThemes = themeService.getAllPublicThemes();
    List<ThemeDTO> shareThemes = themeService.getAllShareThemes();

    log.info("[publicThemes]" + publicThemes);
    log.info("[shareThemes]" + shareThemes);

    model.addAttribute("publicThemes", publicThemes);
    model.addAttribute("shareThemeDTO", shareThemes);
  }

}
