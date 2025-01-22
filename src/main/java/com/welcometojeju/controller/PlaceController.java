package com.welcometojeju.controller;

import com.welcometojeju.dto.*;
import com.welcometojeju.security.SecurityUtils;
import com.welcometojeju.service.PlaceService;
import com.welcometojeju.service.ThemePlaceService;
import com.welcometojeju.service.ThemeService;
import com.welcometojeju.service.UserShareThemeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/places")
public class PlaceController {

  private final PlaceService placeService;
  private final ThemePlaceService themePlaceService;
  private final ThemeService themeService;
  private final UserShareThemeService userShareThemeService;
  private final SecurityUtils securityUtils;

  @PostMapping("/create")
  public String createPlace(@Valid PlaceDTO placeDTO, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      log.info("[createPlace > post > error] " + bindingResult);

      return "redirect:/search/place";
    }

    log.info("[createPlace > post > place] " + placeDTO);

    UserDTO user = securityUtils.getAuthenticatedUser();
    log.info("[createPlace > post > user] " + user);
    placeDTO.setUserNo(user.getNo());

    Integer no = placeService.createPlace(placeDTO);
    log.info("[createPlace > post > no] " + no);

    addPlaceToTheme(placeDTO.getThemeNo(), placeDTO.getNo());
    if (user.getNo() != placeDTO.getThemeNo()) {
      addThemeToUser(placeDTO.getThemeNo(), user.getNo());
    }

    return "redirect:/themes/get?no=" + placeDTO.getThemeNo();
  }

  public void addPlaceToTheme(Integer themeNo, Integer placeNo) {
    ThemePlaceDTO themePlaceDTO = new ThemePlaceDTO(themeNo, placeNo);

    themePlaceService.createThemePlace(themePlaceDTO);
  }

  public void addThemeToUser(Integer themeNo, Integer userNo) {
    ThemeDTO theme = themeService.getThemeByNo(themeNo);
    log.info("[addThemeToUser > theme] " + theme);

    if (theme.getIsShare() == 1) {
      UserShareThemeDTO userShareThemeDTO = new UserShareThemeDTO(userNo, themeNo);

      userShareThemeService.createUserShareTheme(userShareThemeDTO);
    }
  }

}
