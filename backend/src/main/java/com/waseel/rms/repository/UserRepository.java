package com.waseel.rms.repository;

import com.waseel.rms.entity.userEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<userEntity, Long> {
}
