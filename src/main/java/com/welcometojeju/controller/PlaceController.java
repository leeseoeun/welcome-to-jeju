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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/places")
public class PlaceController {

  private final PlaceService placeService;
  private final SecurityUtils securityUtils;

  @PostMapping("/create")
  public String createPlace(@Valid PlaceDTO placeDTO, BindingResult bindingResult) {
    log.info("[createPlace > post > place] " + placeDTO);

    if (bindingResult.hasErrors()) {
      log.info("[createPlace > post > error] " + bindingResult);

      return "redirect:/search/place";
    }

    // 로그인 한 사용자 정보
    UserDTO user = securityUtils.getAuthenticatedUser();
    log.info("[createPlace > post > user] " + user);
    placeDTO.setUserNo(user.getNo());

    Integer no = placeService.createPlaceAndRelations(placeDTO);
    log.info("[createPlace > post > no] " + no);

    return "redirect:/themes/get?no=" + placeDTO.getThemeNo();
  }

  @GetMapping("/delete")
  public String deletePlace(Integer no, Integer themeNo) {
    log.info("[deletePlace > no] " + no);
    log.info("[deletePlace > themeNo] " + themeNo);

    // 로그인 한 사용자 정보
    UserDTO user = securityUtils.getAuthenticatedUser();
    log.info("[deletePlace > user] " + user);

    placeService.deletePlaceAndRelations(no, themeNo, user.getNo());

    return "redirect:/themes/get?no=" + themeNo;
  }

}
