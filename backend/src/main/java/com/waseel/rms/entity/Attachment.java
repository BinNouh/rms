package com.waseel.rms.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class Attachment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long attachment_id;

    private String fileName;

    private String fileType;

    private Long fileSize;

    // Path attribute for the file system storage
    private String filePath;

//    private String attachmentType;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="applicant_id")
    private Applicant applicant;

    // Constructors, getters, setters, etc.

    public Attachment() {}

    public void setAttachment_id(Long id) {
        this.attachment_id = id;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    // suppose to be determinant of which attachment is (CV, Certificates, etc ...)
//    public void setAttachmentType(String attachmentType) {
//        this.attachmentType = attachmentType;
//    }

    public void setApplicant(Applicant applicant) {
        this.applicant = applicant;
    }
}