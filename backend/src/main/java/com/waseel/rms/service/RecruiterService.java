package com.waseel.rms.service;

import com.waseel.rms.entity.Applicant;
import com.waseel.rms.repository.ApplicantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecruiterService {

    @Autowired
    private ApplicantRepository applicantRepository;

    public List<Applicant> getAllApplicants() {
        return applicantRepository.findAll();
    }

    public Optional<Applicant> getApplicantById(Long id) {
        return applicantRepository.findById(id);
    }

    public void deleteApplicant(Long id) {
        applicantRepository.deleteById(id);
    }

    public Applicant updateSubmissionStatus(Long id, String status) {
        Optional<Applicant> optionalApplicant = applicantRepository.findById(id);
        if (optionalApplicant.isPresent()) {
            Applicant applicant = optionalApplicant.get();
            applicant.setSubmissionStatus(status);
            return applicantRepository.save(applicant);
        }
        return null;
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
    }


