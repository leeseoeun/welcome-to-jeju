package com.welcometojeju.controller;

import com.welcometojeju.dto.ThemeDTO;
import com.welcometojeju.dto.UserDTO;
import com.welcometojeju.dto.UserInfoDTO;
import com.welcometojeju.security.SecurityUtils;
import com.welcometojeju.service.ThemeService;
import com.welcometojeju.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Log4j2
public class UserController {

  private final UserService userService;
  private final ThemeService themeService;
  private final SecurityUtils securityUtils;

  @GetMapping("/users/login")
  public String login(HttpServletRequest request) {
    String prevPage = request.getHeader("Referer");

    // 세션에 이전 페이지 정보 저장
    request.getSession().setAttribute("prevPage", prevPage);

    log.info("[login > get > prevPage] " + prevPage);

    return "user/login";
  }

  @GetMapping("/users/logout")
  public String logout() {
    return "redirect:/";
  }

  // 전체 테마 리스트 (공개, 공유)
  @GetMapping({"/users/themes/get", "/users/themes/{themeType}/get"})
  public String getAllThemesByUserNo(@PathVariable(required = false) String themeType, Integer no, Model model) {
    themeType = themeType == null ? "themes" : themeType;
    model.addAttribute("themeType", themeType);
    log.info("[getAllThemesByUserNo > themeType] " + themeType);

    model.addAttribute("userNo", no);
    log.info("[getAllThemesByUserNo > no] " + no);

    // 모든, 개인 테마일 때
    if (themeType.equals("themes") || themeType.equals("public")) {
      List<ThemeDTO> publicThemes = themeService.getAllPublicThemesByUserNo(no);
      model.addAttribute("publicThemes", publicThemes);
      log.info("[getAllThemesByUserNo > publicThemes] " + publicThemes);
    }

    // 모든, 공유 테마일 때
    if (themeType.equals("themes") || themeType.equals("collaborate")) {
      List<ThemeDTO> collaborateThemes = themeService.getAllCollaborateThemesByUserNo(no);
      model.addAttribute("collaborateThemes", collaborateThemes);
      log.info("[getAllThemesByUserNo > collaborateThemes] " + collaborateThemes);
    }

    // 로그인 한 사용자 정보
    UserDTO user = securityUtils.getAuthenticatedUser();
    log.info("[getAllThemesByUserNo > user] " + user);

    // 테마 만들기 가능 여부
    int isCreate = (user != null && no == user.getNo()) ? 1 : 0;
    model.addAttribute("isCreate", isCreate);
    log.info("[getAllThemesByUserNo > isCreate] " + isCreate);

    return "/theme/list";
  }

  @GetMapping("/me")
  public String getUser(Model model) {
    // 로그인 한 사용자 정보
    UserDTO user = securityUtils.getAuthenticatedUser();
    model.addAttribute("user", user);
    log.info("[getUser > user] " + user);

    return "user/me";
  }

  @PostMapping("/me")
  public String updateUser(@Valid UserInfoDTO userInfoDTO, BindingResult bindingResult) {
    log.info("[updateUser > user] " + userInfoDTO);

    if (bindingResult.hasErrors()) {
      log.info("[updateUser > error] " + bindingResult);

      return "redirect:/me";
    }

    // 로그인 한 사용자 정보
    UserDTO user = securityUtils.getAuthenticatedUser();
    log.info("[updateUser > user] " + user);

    user.setNickname(userInfoDTO.getNickname());
    userService.updateUser(user);

    return "redirect:/";
  }

  @GetMapping("/me/themes/{themeType}")
  public String getAllThemesByAuthenticatedUser(@PathVariable(required = false) String themeType, Model model) {
    log.info("[getAllThemesByAuthenticatedUser > themeType] " + themeType);
    model.addAttribute("themeType", themeType);

    // 로그인 한 사용자 정보
    UserDTO user = securityUtils.getAuthenticatedUser();
    log.info("[getAllThemesByAuthenticatedUser > user] " + user);
    model.addAttribute("userNo", user.getNo());

    switch (themeType) {
      case "personal":
      case "public":
      case "private":
        return getAllPersonalThemes(themeType, user.getNo(), model);
      case "shared":
      case "collaborate":
      case "participate":
        return getAllSharedThemes(themeType, user.getNo(), model);
    }

    return null;
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

    return "user/my-theme-list";
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

    return "user/my-theme-list";
  }

}
