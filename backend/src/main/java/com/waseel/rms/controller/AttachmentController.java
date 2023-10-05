package com.waseel.rms.controller;

import com.waseel.rms.entity.Applicant;
import com.waseel.rms.entity.Attachment;
import com.waseel.rms.service.ApplicantService;
import com.waseel.rms.service.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

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
    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadAttachment(@PathVariable Long id) {
        try {
            System.out.println("Trying to fetch attachment with ID: " + id);
    
            byte[] data = attachmentService.getAttachmentFile(id);
            if (data == null) {
                System.out.println("Attachment data is null for ID: " + id);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
    
            Optional<Attachment> attachment = attachmentService.findAttachmentById(id);
            if (!attachment.isPresent()) {
                System.out.println("Attachment not found in DB for ID: " + id);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
    
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(attachment.get().getFileType()));
            headers.setContentLength(attachment.get().getFileSize());
            headers.set("Content-Disposition", "attachment; filename=" + attachment.get().getFileName());
    
            return new ResponseEntity<>(data, headers, HttpStatus.OK);
        } catch (IOException ex) {
            System.err.println("Error fetching attachment: " + ex.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    
}
