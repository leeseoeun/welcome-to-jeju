package com.welcometojeju.controller;

import com.welcometojeju.dto.SearchDTO;
import com.welcometojeju.dto.SearchPlaceDTO;
import com.welcometojeju.dto.ThemeDTO;
import com.welcometojeju.dto.UserDTO;
import com.welcometojeju.service.ThemeService;
import com.welcometojeju.service.UserService;
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
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/search")
public class SearchController {

  private final ThemeService themeService;
  private final UserService userService;

  @GetMapping
  public String search() {
    return "search/theme-user";
  }

  @PostMapping({"", "/{searchType}"})
  public String getListBySearchTypeAndKeyword(@PathVariable(required = false) String searchType, @Valid SearchDTO searchDTO, Model model) {
    searchType = searchType == null ? "search" : searchType;
    model.addAttribute("searchType", searchType);
    log.info("[getListBySearchTypeAndKeyword > searchType] " + searchType);

    String keyword = searchDTO.getKeyword();
    model.addAttribute("keyword", keyword);
    log.info("[getListBySearchTypeAndKeyword > keyword] " + keyword);

    if (searchType.equals("search") || searchType.equals("themes") || searchType.equals("public")) {
      List<ThemeDTO> publicThemes = themeService.getAllPublicThemesByKeyword(keyword);
      model.addAttribute("publicThemes", publicThemes);
      log.info("[getListBySearchTypeAndKeyword > publicThemes] " + publicThemes);
    }

    if (searchType.equals("search") || searchType.equals("themes") || searchType.equals("collaborate")) {
      List<ThemeDTO> collaborateThemes = themeService.getAllCollaborateThemesByKeyword(keyword);
      model.addAttribute("collaborateThemes", collaborateThemes);
      log.info("[getListBySearchTypeAndKeyword > collaborateThemes] " + collaborateThemes);
    }

    if (searchType.equals("search") || searchType.equals("users")) {
      List<UserDTO> users = userService.getAllUsersByKeyword(keyword);
      model.addAttribute("users", users);
      log.info("[getListBySearchTypeAndKeyword > users] " + users);
    }

    return "search/theme-user";
  }

  @PostMapping("/place")
  public String searchPlaceByKeyword(@Valid SearchPlaceDTO searchPlaceDTO, BindingResult bindingResult, Model model) {
    log.info("[searchPlaceByKeyword > searchPlace] " + searchPlaceDTO);
    model.addAttribute("themeNo", searchPlaceDTO.getThemeNo());

    if (bindingResult.hasErrors()) {
      log.info("[searchPlaceByKeyword > error] " + bindingResult);

      return "redirect:/themes/get?no=" + searchPlaceDTO.getThemeNo();
    }

    return "search/place";
  }

}
