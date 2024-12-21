package com.welcometojeju.controller;

import com.welcometojeju.dto.ThemeDTO;
import com.welcometojeju.service.ThemeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor  // 초기화되지 않은 final 필드에 대해 생성자 생성 (의존성 주입)
@Log4j2
public class ThemeController {

  private final ThemeService themeService;

  @PostMapping(value = "/themes", consumes = MediaType.APPLICATION_JSON_VALUE)
  public String createTheme(@Valid ThemeDTO themeDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
    if (bindingResult.hasErrors()) {
      return "";
    }

    log.info("[createTheme > theme] " + themeDTO);

    Integer no = themeService.createTheme(themeDTO);
    redirectAttributes.addAttribute("themeNo", no);

    log.info("[createTheme > no] " + no);

    return "";
  }

  @GetMapping("/themes/get")
  public void getThemeByNo(Integer no) {
    ThemeDTO theme = themeService.getThemeByNo(no);

    log.info("[getThemeByNo > theme] " + theme);
  }

  // 전체 테마 리스트 (공개, 공유)
  @GetMapping("/themes")
  public String getAllThemes(Model model) {
    List<ThemeDTO> publicThemes = themeService.getAllPublicThemes();
    List<ThemeDTO> shareThemes = themeService.getAllShareThemes();

    log.info("[getAllThemes > publicThemes] " + publicThemes);
    log.info("[getAllThemes > shareThemes] " + shareThemes);

    model.addAttribute("themeType", "all");
    model.addAttribute("publicThemes", publicThemes);
    model.addAttribute("shareThemes", shareThemes);

    return "themeList";
  }

}
