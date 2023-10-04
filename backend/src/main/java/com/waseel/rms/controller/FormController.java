package com.waseel.rms.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.waseel.rms.entity.*;
import com.waseel.rms.repository.*;
import com.waseel.rms.service.AttachmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.HashMap;
import java.util.Map;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/applicant/form")
public class FormController {

    @Autowired
    private AttachmentService attachmentService;

    @Autowired
    private ApplicantRepository applicantRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private AttachmentRepository attachmentRepository;

    @Autowired
    private DependencyRepository dependencyRepository;

    @Autowired
    private EmergencyContactRepository emergencyContactRepository;

    @Autowired
    private NationalIdentityRepository nationalIdentityRepository;

    @PreAuthorize("hasRole('applicant-spring')")
    @PostMapping("/submit")
    public ResponseEntity<Map<String, String>> submitForm(
            @RequestParam("applicant") @Valid String applicantJson,
            @RequestParam("address") @Valid String addressJson,
            @RequestParam("emergencyContacts") @Valid String emergencyContactsJson,
            @RequestParam("attachments") MultipartFile[] attachmentsFiles,
            @RequestParam("dependencies") @Valid String dependenciesJson,
            @RequestParam("nationalIdentity") @Valid String nationalIdentityJson
    ) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            Applicant applicant = objectMapper.readValue(applicantJson, Applicant.class);
            Address address = objectMapper.readValue(addressJson, Address.class);
            List<EmergencyContact> emergencyContacts = objectMapper.readValue(emergencyContactsJson, new TypeReference<>() {});
            List<Dependency> dependencies = objectMapper.readValue(dependenciesJson, new TypeReference<>() {});
            NationalIdentity nationalIdentity = objectMapper.readValue(nationalIdentityJson, NationalIdentity.class);

            List<Attachment> attachments = new ArrayList<>();
            for (MultipartFile attachmentFile : attachmentsFiles) {
                String fileName = attachmentFile.getOriginalFilename();
                Path path = Paths.get("attachments/" + fileName);

                Attachment attachment = new Attachment();
                attachment.setFileName(fileName);
                attachment.setFileType(attachmentFile.getContentType());
                attachment.setFileSize(attachmentFile.getSize());
                attachment.setFilePath(path.toString());

                Files.write(path, attachmentFile.getBytes());

                attachments.add(attachment);
            }

            applicant.setAddress(address);
            address.setApplicant(applicant);

            applicant.setEmergencyContacts(emergencyContacts);
            for (EmergencyContact ec : emergencyContacts) {
                ec.setApplicant(applicant);
            }

            applicant.setAttachment(attachments);
            for (Attachment a : attachments) {
                a.setApplicant(applicant);
            }

            applicant.setDependencies(dependencies);
            for (Dependency d : dependencies) {
                d.setApplicant(applicant);
            }

            applicant.setNationalIdentity(nationalIdentity);
            nationalIdentity.setApplicant(applicant);

            addressRepository.save(address);
            applicantRepository.save(applicant);
            emergencyContactRepository.saveAll(emergencyContacts);
            attachmentRepository.saveAll(attachments);
            dependencyRepository.saveAll(dependencies);
            nationalIdentityRepository.save(nationalIdentity);

            Map<String, String> response = new HashMap<>();
            response.put("message", "Form submitted successfully");
            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Error occurred: " + e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/upload")
    public ResponseEntity<List<String>> uploadFiles(@RequestParam("attachments") List<MultipartFile> multipartFiles) throws IOException {
        List<String> filenames = new ArrayList<>();
        for (MultipartFile file : multipartFiles) {
            Attachment attachment = new Attachment();
            attachment.setFileName(file.getOriginalFilename());
            attachment.setFileType(file.getContentType());
            attachment.setFileSize(file.getSize());

            attachmentService.saveAttachment(attachment, file.getBytes());

            filenames.add(file.getOriginalFilename());
        }
        return ResponseEntity.ok().body(filenames);
    }

    @GetMapping("/{id}/download")
    public ResponseEntity<byte[]> downloadAttachment(@PathVariable Long id) throws IOException {
        byte[] fileBytes = attachmentService.getAttachmentFile(id);
        if (fileBytes == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Attachment attachment = attachmentService.findAttachmentById(id).orElse(null);
        if (attachment == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=\"" + attachment.getFileName() + "\"")
                .body(fileBytes);
    }
}
