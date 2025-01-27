package com.welcometojeju.repository;

import com.welcometojeju.domain.ThemePlace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ThemePlaceRepository extends JpaRepository<ThemePlace, Integer> {

  void deleteByThemeNoAndPlaceNo(Integer themeNo, Integer placeNo);

  boolean existsByThemeNoAndPlaceNo(Integer themeNo, Integer placeNo);

  @Query("SELECT CASE WHEN COUNT(tp) > 0 THEN TRUE ELSE FALSE END " +
      "FROM ThemePlace tp " +
      "JOIN Place p ON tp.place.no = p.no " +
      "WHERE tp.theme.no = :themeNo AND p.user.no = :userNo")
  boolean existsByThemeNoAndUserNo(Integer themeNo, Integer userNo);

}
