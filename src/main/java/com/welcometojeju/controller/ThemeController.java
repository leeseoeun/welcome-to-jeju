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

import java.util.List;

@Controller
@RequiredArgsConstructor  // 초기화되지 않은 final 필드에 대해 생성자 생성 (의존성 주입)
@Log4j2
@RequestMapping("/themes")
public class ThemeController {

  private final ThemeService themeService;
  private final SecurityUtils securityUtils;

  @PreAuthorize("isAuthenticated()")
  @GetMapping({"/create", "/update"})
  public String createTheme(Integer no, Model model) {
    log.info("[createTheme > get > no] " + no);

    UserDTO user = securityUtils.getAuthenticatedUser();
    log.info("[createTheme > get > user] " + user);
    model.addAttribute("userNickname", user.getNickname());

    if (no != null) {
      ThemeDTO theme = themeService.getThemeByNo(no);
      log.info("[createTheme > theme] " + theme);
      model.addAttribute("theme", theme);

      return "theme/update";
    }

    return "theme/create";
  }

  @PostMapping({"/create", "/update"})
  public String createTheme(@Valid ThemeDTO themeDTO, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      log.info("[createTheme > post > error] " + bindingResult);

      return "redirect:theme/create";
    }

    log.info("[createTheme > post > theme] " + themeDTO);

    UserDTO user = securityUtils.getAuthenticatedUser();
    log.info("[createTheme > post > user] " + user);
    themeDTO.setUserNo(user.getNo());

    Integer no = themeService.createTheme(themeDTO);
    log.info("[createTheme > post > no] " + no);

    return "redirect:/";
  }

  @GetMapping("/delete")
  public String deleteTheme(Integer no) {
    log.info("[deleteTheme > get > no] " + no);

    themeService.deleteTheme(no);

    return "redirect:/";
  }

  @GetMapping("/get")
  public String getThemeByNo(Integer no, Model model) {
    ThemeDTO theme = themeService.getThemeByNo(no);
    log.info("[getThemeByNo > theme] " + theme);

    UserDTO user = securityUtils.getAuthenticatedUser();
    log.info("[getThemeByNo > user] " + user);

    int isUpdate = (user != null && theme.getIsPublic() == 1 && user.getNo() != theme.getUserNo()) ? 0 : 1;
    log.info("[getThemeByNo > isUpdate] " + isUpdate);

    model.addAttribute("theme", theme);
    model.addAttribute("isUpdate", isUpdate);

    return "theme/read";
  }

  // 전체 테마 리스트 (공개, 공유)
  @GetMapping({"", "/{themeType}"})
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
