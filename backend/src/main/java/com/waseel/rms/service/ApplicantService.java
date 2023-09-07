package com.waseel.rms.service;

import com.waseel.rms.entity.Applicant;
import com.waseel.rms.repository.ApplicantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ApplicantService {

    @Autowired
    private ApplicantRepository applicantRepository;

    public Optional<Applicant> getApplication(Long applicantId) {
        return applicantRepository.findById(applicantId);
    }

    public Optional<Applicant> findApplicantById(Long id) {
        return applicantRepository.findById(id);
    }

    public Applicant updateApplicant(Long id, Applicant newApplicant) {
        return applicantRepository.findById(id)
                .map(applicant -> {
                    applicant.setFullName(newApplicant.getFullName());
                    applicant.setGender(newApplicant.getGender());
                    applicant.setBloodType(newApplicant.getBloodType());
                    applicant.setMartialStatus(newApplicant.getMartialStatus());
                    applicant.setNationality(newApplicant.getNationality());
                    return applicantRepository.save(applicant);
                })
                .orElseGet(() -> {
                    newApplicant.setApplicant_id(id);
                    return applicantRepository.save(newApplicant);
                });
    }
}
