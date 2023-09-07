package com.waseel.rms.repository;

import com.waseel.rms.entity.Applicant;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface ApplicantRepository extends JpaRepository<Applicant, Long> {
    // This will allow you to find applicants by submission status
    List<Applicant> findBySubmissionStatusAndGender(String submissionStatus, String gender);
    List<Applicant> findBySubmissionStatus(String submissionStatus);

    List<Applicant> findByGender(String gender);


}
