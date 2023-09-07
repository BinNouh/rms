package com.waseel.rms.repository;

import com.waseel.rms.entity.NationalIdentity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NationalIdentityRepository extends JpaRepository<NationalIdentity, Long> {
}
