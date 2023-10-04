package com.waseel.rms.repository;

import com.waseel.rms.entity.Applicant;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface ApplicantRepository extends JpaRepository<Applicant, Long>, JpaSpecificationExecutor<Applicant> {
    @Query("SELECT a FROM Applicant a " +
    "JOIN FETCH a.address " +
    "JOIN FETCH a.nationalIdentity " +
    "LEFT JOIN FETCH a.dependencies " +
    "LEFT JOIN FETCH a.emergencyContacts " +
    "LEFT JOIN FETCH a.attachments " +
    "WHERE a.id = :id")
Optional<Applicant> findByIdWithRelations(@Param("id") Long id);

}

