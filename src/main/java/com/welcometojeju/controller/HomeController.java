package com.welcometojeju.controller;

import com.welcometojeju.dto.PlaceDTO;
import com.welcometojeju.dto.ThemeDTO;
import com.welcometojeju.dto.UserDTO;
import com.welcometojeju.service.PlaceService;
import com.welcometojeju.service.ThemeService;
import com.welcometojeju.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/")
public class HomeController {

  private final UserService userService;
  private final ThemeService themeService;
  private final PlaceService placeService;

  @GetMapping
  public String home(Model model) {
    List<UserDTO> users = userService.getTop3UsersByViewCount();
    model.addAttribute("users", users);
    log.info("[home > users] " + users);

    List<ThemeDTO> publicThemes = themeService.getTop3PublicThemesByViewCount();
    model.addAttribute("publicThemes", publicThemes);
    log.info("[home > publicThemes] " + publicThemes);

    List<ThemeDTO> collaborateThemes = themeService.getTop3CollaborateThemesByViewCount();
    model.addAttribute("collaborateThemes", collaborateThemes);
    log.info("[home > collaborateThemes] " + collaborateThemes);

    List<PlaceDTO> places = placeService.getTop3PlacesByRegisterCount();
    model.addAttribute("places", places);
    log.info("[home > places] " + places);

    return "home";
  }

}
