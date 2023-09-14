package com.waseel.rms.controller;

import com.waseel.rms.entity.Applicant;
import com.waseel.rms.service.ApplicantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/applicant")
public class ApplicantController {

    @Autowired
    private ApplicantService applicantService;

    @GetMapping
    public List<Applicant> getAllApplicants() {
        return applicantService.getAllApplicants();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Applicant> getApplicant(@PathVariable Long id) {
        return applicantService.getApplicantById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Applicant> updateApplicant(@PathVariable Long id, @RequestBody Applicant newApplicant) {
        return ResponseEntity.ok(applicantService.updateApplicant(id, newApplicant));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteApplicant(@PathVariable Long id) {
        applicantService.deleteApplicant(id);
        return ResponseEntity.ok("Applicant deleted");
    }

    // Filtering by submission status
    @GetMapping("/filter-by-status")
    public List<Applicant> filterApplicantsBySubmissionStatus(@RequestParam String status) {
        return applicantService.getApplicantsBySubmissionStatus(status);
    }
}
