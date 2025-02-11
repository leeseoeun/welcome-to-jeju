package com.welcometojeju.repository;

import com.welcometojeju.domain.Place;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlaceRepository extends JpaRepository<Place, Integer> {

  boolean existsByNo(Integer no);

  List<Place> findTop3ByOrderByRegisterCountDesc();

}
