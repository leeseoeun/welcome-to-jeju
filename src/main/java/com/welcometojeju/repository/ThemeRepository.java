package com.welcometojeju.repository;

import com.welcometojeju.domain.Theme;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ThemeRepository extends JpaRepository<Theme, Integer> {

  List<Theme> findAllByIsPublic(int i);
  List<Theme> findAllByIsShare(int i);

}
