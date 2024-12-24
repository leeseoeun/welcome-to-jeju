package com.welcometojeju.repository;

import com.welcometojeju.domain.Theme;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ThemeRepository extends JpaRepository<Theme, Integer> {

  List<Theme> findAllByIsPublic(int isPublic);
  List<Theme> findAllByIsShare(int i);

  List<Theme> findAllByUserNoAndIsPublic(Integer userNo, int isPublic);

  List<Theme> findAllByUserNoAndIsShare(Integer userNo, int isPublic);

}
