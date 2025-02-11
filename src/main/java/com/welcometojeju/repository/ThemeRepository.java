package com.welcometojeju.repository;

import com.welcometojeju.domain.Theme;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ThemeRepository extends JpaRepository<Theme, Integer> {

  // Theme 엔티티의 placeList 필드를 로드. placeList 내부에 있는 place 필드를 추가로 로드
  @EntityGraph(attributePaths = {"placeList", "placeList.place"})
  Optional<Theme> findWithPlacesByNo(Integer no);

  List<Theme> findAllByIsPublic(int isPublic);
  List<Theme> findAllByIsShare(int isShare);

  List<Theme> findAllByUserNoAndIsPublic(Integer userNo, int isPublic);
  List<Theme> findAllByUserNoAndIsShare(Integer userNo, int isPublic);
  @Query("SELECT t FROM Theme t JOIN UserShareTheme ust ON t.no = ust.theme.no WHERE ust.user.no = :userNo")
  List<Theme> findAllParticipateThemesByUserNo(Integer userNo);

  List<Theme> findAllByTitleContainingAndIsPublic(String keyword, int isPublic);
  List<Theme> findAllByTitleContainingAndIsShare(String keyword, int isShare);

  List<Theme> findTop3ByIsPublicOrderByViewCountDesc(int isPublic);
  List<Theme> findTop3ByIsShareOrderByViewCountDesc(int isShare);

}
