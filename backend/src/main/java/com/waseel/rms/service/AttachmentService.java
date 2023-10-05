package com.waseel.rms.service;

import com.waseel.rms.entity.Applicant;
import com.waseel.rms.entity.Attachment;
import com.waseel.rms.repository.AttachmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class AttachmentService {

    @Value("${file.upload-dir}")
    private String uploadDir; // Upload directory, make sure it exists

    @Autowired
    private AttachmentRepository attachmentRepository;

    public Attachment saveAttachment(Attachment attachment, byte[] fileBytes) throws IOException {
        // Check if the directory exists, if not, create it
        Path dirPath = Paths.get(uploadDir);
        if (!Files.exists(dirPath)) {
            Files.createDirectories(dirPath);
        }

        // Resolve the full file path
        Path path = dirPath.resolve(attachment.getFileName());

        // Write the bytes to the resolved file path
        Files.write(path, fileBytes);

        // Set the full path to the attachment object and save it
        attachment.setFilePath(path.toString());
        return attachmentRepository.save(attachment);
    }

    public byte[] getAttachmentFile(Long id) throws IOException {
        Optional<Attachment> attachmentOpt = findAttachmentById(id);
        if (attachmentOpt.isPresent()) {
            Path path = Paths.get(attachmentOpt.get().getFilePath());
            return Files.readAllBytes(path);
        }
        return null;
    }

    public Optional<Attachment> findAttachmentById(Long id) {
        return attachmentRepository.findById(id);
    }

    public void deleteAttachment(Long id) {
        attachmentRepository.deleteById(id);
    }

    public List<Attachment> findAttachmentsByApplicant(Applicant applicant) {
        return attachmentRepository.findByApplicant(applicant);
    }
}
