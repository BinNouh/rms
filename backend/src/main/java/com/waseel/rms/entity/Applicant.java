package com.waseel.rms.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.ZonedDateTime;
import java.util.List;


@Getter
@Entity
public class Applicant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long applicant_id;

    // Keycloak Identifier for Applicant
    private String keycloakId;

    @NotBlank(message = "Full name is required.")
    private String fullName;

    @NotNull(message = "Gender is required.")
        private String gender;

    @NotNull(message = "Martial status is required.")
    private String martialStatus;

    @NotNull(message = "Blood type is required.")
    private String bloodType;

    @NotBlank(message = "Nationality is required.")
    private String nationality;

    private ZonedDateTime submissionDate;

    private String submissionStatus;

    @OneToMany(mappedBy = "applicant", cascade = CascadeType.ALL)
    private List<Dependency> dependencies;
    @OneToMany(mappedBy = "applicant", cascade = CascadeType.ALL)
    private List<EmergencyContact> emergencyContacts;

    @OneToMany(mappedBy = "applicant", cascade = CascadeType.ALL)
    private List<Attachment> attachments;

    @OneToOne(mappedBy = "applicant", cascade = CascadeType.ALL)
    private Address address;

    @OneToOne(mappedBy = "applicant", cascade = CascadeType.ALL)
    private NationalIdentity nationalIdentity;

    @OneToOne
    @JoinColumn(name = "user_id")
    private userEntity user;

    // Constructors, getters, setters, etc.

    public Applicant() {}

    public void setApplicant_id(Long id) {
        this.applicant_id = id;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public void setMartialStatus(String martialStatus) {
        this.martialStatus = martialStatus;
    }

    public void setSubmissionDate(ZonedDateTime submissionDate) {
        this.submissionDate = submissionDate;
    }

    public void setSubmissionStatus(String submissionStatus) {
        this.submissionStatus = submissionStatus;
    }

    public void setDependencies(List<Dependency> dependencies) {
        this.dependencies = dependencies;
    }

    // Additional methods for setting keycloakId
    public void setKeycloakId(String keycloakId) {
        this.keycloakId = keycloakId;
    }
    public void setEmergencyContacts(List<EmergencyContact> emergencyContacts) {
        this.emergencyContacts = emergencyContacts;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setNationalIdentity(NationalIdentity nationalIdentity) {
        this.nationalIdentity = nationalIdentity;
    }

    public void setAttachment(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    public void setNationality(String nationality) { // add this method
        this.nationality = nationality;
    }

    public String getNationality() { // add this method
        return nationality;
    }
    public void setUser(userEntity user) {
        this.user = user;
    }
}
