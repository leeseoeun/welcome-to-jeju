package com.welcometojeju.controller;

import com.welcometojeju.dto.ThemeDTO;
import com.welcometojeju.dto.UserDTO;
import com.welcometojeju.security.SecurityUtils;
import com.welcometojeju.service.ThemeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
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

  @GetMapping({"/create", "/update"})
  public String createTheme(Integer no, Model model) {
    log.info("[createTheme > get > no] " + no);

    // 로그인 한 사용자 정보
    UserDTO user = securityUtils.getAuthenticatedUser();
    model.addAttribute("userNickname", user.getNickname());
    log.info("[createTheme > get > user] " + user);

    if (no != null) {
      ThemeDTO theme = themeService.getThemeByNo(no);
      model.addAttribute("theme", theme);
      log.info("[createTheme > theme] " + theme);

      return "theme/update";
    }

    return "theme/create";
  }

  @PostMapping({"/create", "/update"})
  public String createTheme(@Valid ThemeDTO themeDTO, BindingResult bindingResult) {
    log.info("[createTheme > post > theme] " + themeDTO);

    if (bindingResult.hasErrors()) {
      log.info("[createTheme > post > error] " + bindingResult);

      return "redirect:theme/create";
    }

    // 로그인 한 사용자 정보
    UserDTO user = securityUtils.getAuthenticatedUser();
    themeDTO.setUserNo(user.getNo());
    log.info("[createTheme > post > user] " + user);

    Integer no = themeService.createTheme(themeDTO);
    log.info("[createTheme > post > no] " + no);

    return "redirect:/";
  }

  @GetMapping("/delete")
  public String deleteTheme(Integer no) {
    log.info("[deleteTheme > no] " + no);

    themeService.deleteTheme(no);

    return "redirect:/";
  }

  @GetMapping("/get")
  public String getTheme(Integer no, Model model) {
    log.info("[getTheme > no] " + no);

    // 조회 수 증가
    themeService.updateViewCount(no);

    ThemeDTO theme = null;

    // 로그인 한 사용자 정보
    UserDTO user = securityUtils.getAuthenticatedUser();
    if (user != null) {
      theme = themeService.getThemeWithPlacesByNo(no, user.getNo());
    } else {
      theme = themeService.getThemeWithPlacesByNo(no, null);
    }
    log.info("[getTheme > user] " + user);

    model.addAttribute("theme", theme);
    log.info("[getTheme > theme] " + theme);

    // 추가할 장소 검색하기 가능 여부
    int isCreate = (user != null && theme.getIsPublic() == 1 && user.getNo() != theme.getUserNo()) ? 0 : 1;
    model.addAttribute("isCreate", isCreate);
    log.info("[getTheme > isCreate] " + isCreate);

    return "theme/read";
  }

  // 전체 테마 리스트 (공개, 공유)
  @GetMapping({"", "/{themeType}"})
  public String getAllThemes(@PathVariable(required = false) String themeType, Model model) {
    themeType = themeType == null ? "themes" : themeType;
    model.addAttribute("themeType", themeType);
    log.info("[getAllThemes > themeType] " + themeType);

    // 모든, 개인 테마일 때
    if (themeType.equals("themes") || themeType.equals("public")) {
      List<ThemeDTO> publicThemes = themeService.getAllPublicThemes();
      model.addAttribute("publicThemes", publicThemes);
      log.info("[getAllThemes > publicThemes] " + publicThemes);
    }

    // 모든, 공유 테마일 때
    if (themeType.equals("themes") || themeType.equals("collaborate")) {
      List<ThemeDTO> collaborateThemes = themeService.getAllCollaborateThemes();
      model.addAttribute("collaborateThemes", collaborateThemes);
      log.info("[getAllThemes > collaborateThemes] " + collaborateThemes);
    }

    return "theme/list";
  }

}
