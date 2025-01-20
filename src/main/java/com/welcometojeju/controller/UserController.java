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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/me")
public class UserController {

  private final UserService userService;
  private final ThemeService themeService;
  private final SecurityUtils securityUtils;

  @GetMapping("/login")
  public String login(HttpServletRequest request) {
    String prevPage = request.getHeader("Referer");

    log.info("[login > get > prevPage] " + prevPage);

    // 세션에 이전 페이지 정보 저장
    request.getSession().setAttribute("prevPage", prevPage);

    return "user/login";
  }

  @GetMapping("/logout")
  public String logout() {
    return "redirect:/";
  }

  @GetMapping
  public String getUser(Model model) {
    UserDTO user = securityUtils.getAuthenticatedUser();

    log.info("[getUser > user] " + user);

    model.addAttribute("user", user);

    return "user/me";
  }

  @PostMapping("/update")
  public String updateUser(@Valid UserInfoDTO userInfoDTO, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      log.info("[updateUser > error] " + bindingResult);

      return "redirect:/me";
    }

    log.info("[updateUser > user] " + userInfoDTO);

    UserDTO user = securityUtils.getAuthenticatedUser();

    log.info("[updateUser > user] " + user);

    user.setNickname(userInfoDTO.getNickname());
    userService.updateUser(user);

    return "redirect:/";
  }

  @GetMapping("/themes/*")
  public String getAllThemesByAuthenticatedUser(HttpServletRequest request, Model model) {
    String url = request.getRequestURI();
    log.info("[getAllThemesByAuthenticatedUser > url] " + url);

    String themeType = url.substring(url.lastIndexOf("/") + 1);
    log.info("[getAllThemesByAuthenticatedUser > themeType] " + themeType);

    UserDTO user = securityUtils.getAuthenticatedUser();
    log.info("[getAllThemesByAuthenticatedUser > user] " + user);

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

    model.addAttribute("themeType", themeType);
    model.addAttribute("userNo", userNo);

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

    model.addAttribute("themeType", themeType);
    model.addAttribute("userNo", userNo);

    return "user/my-theme-list";
  }

}
