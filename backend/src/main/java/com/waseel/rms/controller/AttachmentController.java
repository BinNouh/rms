package com.waseel.rms.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.waseel.rms.entity.Applicant;
import com.waseel.rms.entity.Attachment;
import com.waseel.rms.service.ApplicantService;
import com.waseel.rms.service.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.nio.file.Files.copy;
import static java.nio.file.Paths.get;

@RestController
@RequestMapping("/api/dashboard/attachments")
public class AttachmentController {

    @Autowired
    private AttachmentService attachmentService;

    @Autowired
    private ApplicantService applicantService;


    @GetMapping("/{id}")
    public ResponseEntity<Attachment> getAttachmentById(@PathVariable Long id) {
        Optional<Attachment> attachment = attachmentService.findAttachmentById(id);
        return attachment.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAttachment(@PathVariable Long id) {
        attachmentService.deleteAttachment(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/byApplicant/{applicantId}")
    public ResponseEntity<List<Attachment>> getAttachmentsByApplicantId(@PathVariable Long applicantId) {
        Optional<Applicant> applicantOptional = applicantService.getApplicantById(applicantId);
        if (applicantOptional.isPresent()) {
            Applicant applicant = applicantOptional.get();
            List<Attachment> attachments = attachmentService.findAttachmentsByApplicant(applicant);
            return new ResponseEntity<>(attachments, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
