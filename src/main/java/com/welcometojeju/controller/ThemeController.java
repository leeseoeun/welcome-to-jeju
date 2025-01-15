package com.welcometojeju.controller;

import com.welcometojeju.dto.ThemeDTO;
import com.welcometojeju.dto.UserDTO;
import com.welcometojeju.security.SecurityUtils;
import com.welcometojeju.service.ThemeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor  // 초기화되지 않은 final 필드에 대해 생성자 생성 (의존성 주입)
@Log4j2
@RequestMapping("/themes")
public class ThemeController {

  private final ThemeService themeService;
  private final SecurityUtils securityUtils;

  @PreAuthorize("isAuthenticated()")
  @GetMapping("/create")
  public String createTheme(Model model) {
    UserDTO user = securityUtils.getAuthenticatedUser();

    log.info("[createTheme > get > user] " + user);

    model.addAttribute("userNo", user.getNo());
    model.addAttribute("userNickname", user.getNickname());

    return "theme/create";
  }

  @PostMapping(value = "/create")
  public String createTheme(@Valid ThemeDTO themeDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
    if (bindingResult.hasErrors()) {
      log.info("[createTheme > post > error] " + bindingResult);

      return "redirect:theme/create" + themeDTO.getUserNo();
    }

    log.info("[createTheme > post > theme] " + themeDTO);

    Integer no = themeService.createTheme(themeDTO);

    log.info("[createTheme > post > no] " + no);

    return "redirect:/";
  }

  @GetMapping("/get")
  public void getThemeByNo(Integer no) {
    ThemeDTO theme = themeService.getThemeByNo(no);

    log.info("[getThemeByNo > theme] " + theme);
  }

  // 전체 테마 리스트 (공개, 공유)
  @GetMapping({"", "/public", "/collaborate"})
  @PostMapping({"", "/{themeType}"})
  public String getAllThemes(@PathVariable(required = false) String themeType, Model model) {
    themeType = themeType == null ? "themes" : themeType;
    log.info("[getAllThemes > themeType] " + themeType);

    // 모든, 개인 테마일 때
    if (themeType.equals("themes") || themeType.equals("public")) {
      List<ThemeDTO> publicThemes = themeService.getAllPublicThemes();
      log.info("[getAllThemes > publicThemes] " + publicThemes);
      model.addAttribute("publicThemes", publicThemes);
    }

    // 모든, 공유 테마일 때
    if (themeType.equals("themes") || themeType.equals("collaborate")) {
      List<ThemeDTO> collaborateThemes = themeService.getAllCollaborateThemesThemes();
      log.info("[getAllThemes > collaborateThemes] " + collaborateThemes);
      model.addAttribute("collaborateThemes", collaborateThemes);
    }

    model.addAttribute("themeType", themeType);

    return "theme/list";
  }

}
