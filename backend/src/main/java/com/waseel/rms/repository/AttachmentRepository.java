package com.waseel.rms.repository;

import com.waseel.rms.entity.Applicant;
import com.waseel.rms.entity.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
    List<Attachment> findByApplicant(Applicant applicant);

    Optional<Attachment> findByFileName(String filename);
}
