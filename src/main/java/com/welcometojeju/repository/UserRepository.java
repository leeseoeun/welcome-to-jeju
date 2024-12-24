package com.welcometojeju.repository;

import com.welcometojeju.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

  User findByNo(Integer no);

}
