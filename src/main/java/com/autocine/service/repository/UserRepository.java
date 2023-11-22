package com.autocine.service.repository;

import com.autocine.service.entity.UserCine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserCine, Long> {
  UserCine findByEmail(String email);
}
