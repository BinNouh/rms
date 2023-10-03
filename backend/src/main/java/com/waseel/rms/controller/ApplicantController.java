package com.waseel.rms.controller;

import com.waseel.rms.entity.Applicant;
import com.waseel.rms.service.ApplicantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "http://localhost:4200") // This will allow the Angular app running on localhost:4200 to access this API.
public class ApplicantController {

    @Autowired
    private ApplicantService applicantService;

    @GetMapping
    public List<Applicant> getAllApplicants() {
        return applicantService.getAllApplicants();
    }

    @GetMapping("/filter")
    public List<Applicant> filterApplicants(
            @RequestParam(required = false) String gender,
            @RequestParam(required = false) String submissionStatus) {
        return applicantService.filterApplicants(gender, submissionStatus);
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
        System.out.println("Received ID: " + id + ", Type: " + ((Object)id).getClass().getSimpleName());
        applicantService.deleteApplicant(id);
        return ResponseEntity.ok("Applicant deleted");
    }

    // Update applicant status (Under Review (default after submission), Reviewed, Rejected)
    @PutMapping("/{id}/status")
    public ResponseEntity<Applicant> updateStatus(@PathVariable Long id, @RequestParam String status) {
        Applicant updatedApplicant = applicantService.updateSubmissionStatus(id, status);
        if (updatedApplicant != null) {
            return ResponseEntity.ok(updatedApplicant);
        }
        return ResponseEntity.notFound().build();
    }
}
