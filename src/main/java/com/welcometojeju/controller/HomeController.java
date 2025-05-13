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
import org.springframework.web.servlet.View;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Controller
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/")
public class HomeController {

  private final UserService userService;
  private final ThemeService themeService;
  private final PlaceService placeService;
  private final View error;

  @GetMapping
  public String home(Model model) {
    long beforeTime = System.currentTimeMillis();

    // 병렬로 여러 작업 수행 후 결과를 모아야 하기 때문에 CompletableFuture 사용
    CompletableFuture<List<UserDTO>> users =
        CompletableFuture.supplyAsync(() -> userService.getTop3UsersByViewCount())
            .whenComplete((result, error) -> {
              if (error == null) {
                model.addAttribute("users", result);
                log.info("[home > users] {}", result);
              } else {
                log.error("[home > users] ", error);
              }
            });

    CompletableFuture<List<ThemeDTO>> publicThemes =
        CompletableFuture.supplyAsync(() -> themeService.getTop3PublicThemesByViewCount())
            .whenComplete((result, error) -> {
              if (error == null) {
                model.addAttribute("publicThemes", result);
                log.info("[home > publicThemes] {}", result);
              } else {
                log.error("[home > publicThemes] ", error);
              }
            });

    CompletableFuture<List<ThemeDTO>> collaborateThemes =
        CompletableFuture.supplyAsync(() -> themeService.getTop3CollaborateThemesByViewCount())
            .whenComplete((result, error) -> {
              if (error == null) {
                model.addAttribute("collaborateThemes", result);
                log.info("[home > collaborateThemes] {}", result);
              } else {
                log.error("[home > collaborateThemes] ", error);
              }
            });

    CompletableFuture<List<PlaceDTO>> places =
        CompletableFuture.supplyAsync(() -> placeService.getTop3PlacesByRegisterCount())
            .whenComplete((result, error) -> {
              if (error == null) {
                model.addAttribute("places", result);
                log.info("[home > places] {}", result);
              } else {
                log.error("[home > places] ", error);
              }
            });

    CompletableFuture.allOf(
        users,
        publicThemes,
        collaborateThemes,
        places
    ).join();

    long afterTime = System.currentTimeMillis();
    long diffTime = afterTime - beforeTime;
    log.info("[home > diffTime] 실행 시간 : " + diffTime + "ms");

    return "home";
  }

}
