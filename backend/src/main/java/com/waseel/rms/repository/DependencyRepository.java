package com.waseel.rms.repository;

import com.waseel.rms.entity.Dependency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DependencyRepository extends JpaRepository<Dependency, Long> {
}
