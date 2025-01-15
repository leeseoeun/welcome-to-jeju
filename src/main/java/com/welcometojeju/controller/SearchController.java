package com.welcometojeju.controller;

import com.welcometojeju.dto.SearchDTO;
import com.welcometojeju.dto.ThemeDTO;
import com.welcometojeju.dto.UserDTO;
import com.welcometojeju.service.ThemeService;
import com.welcometojeju.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    return "search";
  }

  @PostMapping({"", "/{searchType}"})
  public String getListBySearchTypeAndKeyword(@PathVariable(required = false) String searchType, @Valid SearchDTO searchDTO, Model model) {
    searchType = searchType == null ? "search" : searchType;
    log.info("[getListBySearchTypeAndKeyword > searchType] " + searchType);

    String keyword = searchDTO.getKeyword();
    log.info("[getListBySearchTypeAndKeyword > keyword] " + keyword);

    if (searchType.equals("search") || searchType.equals("themes") || searchType.equals("public")) {
      List<ThemeDTO> publicThemes = themeService.getAllPublicThemesByKeyword(keyword);
      log.info("[getListBySearchTypeAndKeyword > publicThemes] " + publicThemes);
      model.addAttribute("publicThemes", publicThemes);
    }

    if (searchType.equals("search") || searchType.equals("themes") || searchType.equals("collaborate")) {
      List<ThemeDTO> collaborateThemes = themeService.getAllCollaborateThemesByKeyword(keyword);
      log.info("[getListBySearchTypeAndKeyword > collaborateThemes] " + collaborateThemes);
      model.addAttribute("collaborateThemes", collaborateThemes);
    }

    if (searchType.equals("search") || searchType.equals("users")) {
      List<UserDTO> users = userService.getAllUsersByKeyword(keyword);
      log.info("[getListBySearchTypeAndKeyword > users] " + users);
      model.addAttribute("users", users);
    }

    model.addAttribute("searchType", searchType);
    model.addAttribute("keyword", keyword);

    return "search";
  }

}
