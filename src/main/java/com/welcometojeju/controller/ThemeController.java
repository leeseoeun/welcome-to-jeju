package com.welcometojeju.controller;

import com.welcometojeju.domain.ThemeType;
import com.welcometojeju.dto.ThemeDTO;
import com.welcometojeju.service.ThemeService;
import com.welcometojeju.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
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
  private final UserService userService;

  @PreAuthorize("isAuthenticated()")
  @GetMapping("/create")
  public String createTheme(Integer userNo, Model model) {
    log.info("[createTheme > get > userNO] " + userNo);

    String userNickname = userService.getUserNicknameByNo(userNo);
    log.info("[createTheme > get > userNickname] " + userNickname);

    model.addAttribute("userNo", userNo);
    model.addAttribute("userNickname", userNickname);

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
    redirectAttributes.addAttribute("themeNo", no);

    log.info("[createTheme > post > no] " + no);

    return "redirect:/theme/detail" + themeDTO.getNo();
  }

  @GetMapping("/get")
  public void getThemeByNo(Integer no) {
    ThemeDTO theme = themeService.getThemeByNo(no);

    log.info("[getThemeByNo > theme] " + theme);
  }

  @GetMapping({"", "/personal", "/public", "/private", "/shared", "/collaborate", "/participate"})
  public String getTypeAndUserNo(HttpServletRequest request, Integer userNo, Model model) {
    String url = request.getRequestURI().substring(request.getRequestURI().lastIndexOf("/") + 1);
    log.info("[getTypeAndUserNo > url] " + url);

    log.info("[getAllPublicOrCollaborateThemes > userNo] " + userNo);

    String themeType = ThemeType.valueOf(url.toUpperCase()).name().toLowerCase();
    log.info("[getTypeAndUserNo > themeType] " + themeType);

    if (userNo != null) {
      switch (themeType) {
        case "personal":
        case "public":
        case "private":
          return getAllPersonalThemes(themeType, userNo, model);
        case "shared":
        case "collaborate":
        case "participate":
          return getAllSharedThemes(themeType, userNo, model);
      }
    }

    return getAllThemes(themeType, model);
  }

  // 전체 테마 리스트 (공개, 공유)
  public String getAllThemes(String themeType, Model model) {
    // 모든, 개인 테마일 때
    if (!themeType.equals("collaborate")) {
      List<ThemeDTO> publicThemes = themeService.getAllPublicThemes();
      log.info("[getAllThemes > publicThemes] " + publicThemes);
      model.addAttribute("publicThemes", publicThemes);
    }

    // 모든, 공유 테마일 때
    if (!themeType.equals("public")) {
      List<ThemeDTO> collaborateThemes = themeService.getAllCollaborateThemesThemes();
      log.info("[getAllThemes > collaborateThemes] " + collaborateThemes);
      model.addAttribute("collaborateThemes", collaborateThemes);
    }

    model.addAttribute("themeType", themeType);

    return "theme/list";
  }

  // 유저 > 개인 테마 리스트 (공개, 비공개)
  public String getAllPersonalThemes(String themeType, Integer userNo, Model model) {
    // 모든, 공개 테마일 때
    if (!themeType.equals("private")) {
      List<ThemeDTO> publicThemes = themeService.getAllPublicThemesByUserNo(userNo);
      log.info("[getAllPersonalThemes > publicThemes] " + publicThemes);
      model.addAttribute("publicThemes", publicThemes);
    }

    // 모든, 비공개 테마일 때
    if (!themeType.equals("public")) {
      List<ThemeDTO> privateThemes = themeService.getAllPrivateThemesByUserNo(userNo);
      log.info("[getAllPersonalThemes > privateThemes] " + privateThemes);
      model.addAttribute("privateThemes", privateThemes);
    }

    model.addAttribute("themeType", themeType);
    model.addAttribute("userNo", userNo);

    return "theme/personal-list";
  }

  // 유저 > 공유 테마 리스트 (공유, 참여)
  public String getAllSharedThemes(String themeType, Integer userNo, Model model) {
    // 모든, 공유 테마일 때
    if (!themeType.equals("participate")) {
      List<ThemeDTO> collaborateThemes = themeService.getAllCollaborateThemesByUserNo(userNo);
      log.info("[getAllSharedThemes > collaborateThemes] " + collaborateThemes);
      model.addAttribute("collaborateThemes", collaborateThemes);
    }

    // 모든, 참여 테마일 때
    if (!themeType.equals("collaborate")) {
      List<ThemeDTO> participateThemes = themeService.getAllParticipateThemesByUserNo(userNo);
      log.info("[getAllSharedThemes > participateThemes] " + participateThemes);
      model.addAttribute("participateThemes", participateThemes);
    }

    model.addAttribute("themeType", themeType);
    model.addAttribute("userNo", userNo);

    return "theme/shared-list";
  }

}
