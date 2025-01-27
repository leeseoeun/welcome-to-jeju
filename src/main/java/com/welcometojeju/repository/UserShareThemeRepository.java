package com.welcometojeju.repository;

import com.welcometojeju.domain.UserShareTheme;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserShareThemeRepository extends JpaRepository<UserShareTheme, Integer> {

  void deleteByUserNoAndThemeNo(Integer userNo, Integer themeNo);

  boolean existsByUserNoAndThemeNo(Integer userNo, Integer themeNo);

}
