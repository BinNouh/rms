package com.waseel.rms.controller;

import com.waseel.rms.entity.Applicant;
import com.waseel.rms.service.RecruiterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/recruiter")
public class RecruiterController {

    @Autowired
    private RecruiterService recruiterService;

    @GetMapping("/all-applicants")
    public ResponseEntity<List<Applicant>> getAllApplicants() {
        List<Applicant> applicants = recruiterService.getAllApplicants();
        return ResponseEntity.ok(applicants);
    }

    @GetMapping("/applicant/{id}")
    public ResponseEntity<Applicant> getApplicantById(@PathVariable Long id) {
        Optional<Applicant> applicant = recruiterService.getApplicantById(id);
        return applicant.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/applicant/{id}")
    public ResponseEntity<Void> deleteApplicant(@PathVariable Long id) {
        recruiterService.deleteApplicant(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/applicant/{id}/status")
    public ResponseEntity<Applicant> updateStatus(@PathVariable Long id, @RequestParam String status) {
        Applicant updatedApplicant = recruiterService.updateSubmissionStatus(id, status);
        if (updatedApplicant != null) {
            return ResponseEntity.ok(updatedApplicant);
        }
        return ResponseEntity.notFound().build();
    }


    @GetMapping("/filter-applicants")
    public ResponseEntity<List<Applicant>> filterApplicants(@RequestParam(required = false) String status,
                                                            @RequestParam(required = false) String gender) {
        List<Applicant> applicants = recruiterService.filterApplicants(status, gender);
        return ResponseEntity.ok(applicants);
    }
}
