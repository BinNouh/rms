package com.waseel.rms.controller;

import com.waseel.rms.entity.Applicant;
import com.waseel.rms.repository.ApplicantRepository;
import com.waseel.rms.service.ApplicantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/applicant")
public class ApplicantController {

    @Autowired
    private ApplicantService applicantService;

    @Autowired
    private ApplicantRepository applicantRepository;

    @GetMapping
    public List<Applicant> getAllApplicants() {
        return applicantRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Applicant> getApplicant(@PathVariable Long id) {
        Optional<Applicant> applicant = applicantRepository.findById(id);
        if(applicant.isPresent()) {
            return ResponseEntity.ok(applicant.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public Applicant updateApplicant(@PathVariable Long id, @RequestBody Applicant newApplicant) {
        return applicantService.updateApplicant(id, newApplicant);
    }

    @GetMapping("/view/{id}")
    public ResponseEntity<Applicant> viewApplication(@PathVariable Long id) {
        Optional<Applicant> applicant = applicantService.getApplication(id);
        return applicant.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteApplicant(@PathVariable Long id) {

        applicantRepository.deleteById(id);

        return ResponseEntity.ok("Applicant deleted");
    }
}
