package com.welcometojeju.repository;

import com.welcometojeju.domain.Theme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ThemeRepository extends JpaRepository<Theme, Integer> {

  @Query("SELECT t, u.nickname FROM Theme t JOIN t.user u where t.isPublic = :isPublic")
  List<Theme> findAllByIsPublic(int isPublic);
  List<Theme> findAllByIsShare(int isShare);

  List<Theme> findAllByUserNoAndIsPublic(Integer userNo, int isPublic);
  List<Theme> findAllByUserNoAndIsShare(Integer userNo, int isPublic);

}
