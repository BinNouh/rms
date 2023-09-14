
package com.waseel.rms.service;

        import com.waseel.rms.entity.Applicant;
        import com.waseel.rms.repository.ApplicantRepository;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Service;

        import java.time.ZonedDateTime;
        import java.util.List;
        import java.util.Optional;

@Service
public class ApplicantService {

    @Autowired
    private ApplicantRepository applicantRepository;

    // List all applicants
    public List<Applicant> getAllApplicants() {
        return applicantRepository.findAll();
    }

    // List applicant by ID
    public Optional<Applicant> getApplicantById(Long id) {
        return applicantRepository.findById(id);
    }

    // Delete Applicant
    public void deleteApplicant(Long id) {
        applicantRepository.deleteById(id);
    }

    // Update applicant status (Under Review, Reviewed, Rejected)
    public Applicant updateSubmissionStatus(Long id, String status) {
        Optional<Applicant> optionalApplicant = applicantRepository.findById(id);
        if (optionalApplicant.isPresent()) {
            Applicant applicant = optionalApplicant.get();
            applicant.setSubmissionStatus(status);
            return applicantRepository.save(applicant);
        }
        return null;
    }

    public Applicant updateApplicant(Long id, Applicant newApplicant) {
        return applicantRepository.findById(id)
                .map(applicant -> {
                    applicant.setFullName(newApplicant.getFullName());
                    applicant.setGender(newApplicant.getGender());
                    applicant.setBloodType(newApplicant.getBloodType());
                    applicant.setMartialStatus(newApplicant.getMartialStatus());
                    applicant.setNationality(newApplicant.getNationality());

                    // Only set the submission date if it is not already set
                    if (applicant.getSubmissionDate() == null) {
                        applicant.setSubmissionDate(ZonedDateTime.now());
                    }

                    return applicantRepository.save(applicant);
                })
                .orElseGet(() -> {
                    newApplicant.setApplicant_id(id);
                    newApplicant.setSubmissionDate(ZonedDateTime.now());  // set the submission date for new applicants
                    return applicantRepository.save(newApplicant);
                });
    }

    public List<Applicant> filterApplicants(String submissionStatus, String gender) {
        if (submissionStatus != null && gender != null) {
            return applicantRepository.findBySubmissionStatusAndGender(submissionStatus, gender);
        } else if (submissionStatus != null) {
            return applicantRepository.findBySubmissionStatus(submissionStatus);
        } else if (gender != null) {
            return applicantRepository.findByGender(gender);
        } else {
            return applicantRepository.findAll();
        }
    }
    public List<Applicant> getApplicantsBySubmissionStatus(String submissionStatus) {
        return applicantRepository.findBySubmissionStatus(submissionStatus);
    }
}




