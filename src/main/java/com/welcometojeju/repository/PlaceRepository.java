package com.welcometojeju.repository; 

import com.welcometojeju.domain.Place;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceRepository extends JpaRepository<Place, Integer> {

  boolean existsByNo(Integer no);

}
