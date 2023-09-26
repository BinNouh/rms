package com.waseel.rms.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("id")
    private Long applicant_id;


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

    // Default value for submitted forms "Under Review"
    private String submissionStatus = "Under Review";

    @JsonIgnore // ignore during serialization
    @OneToMany(mappedBy = "applicant", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Dependency> dependencies;

    @JsonIgnore // ignore during serialization
    @OneToMany(mappedBy = "applicant", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<EmergencyContact> emergencyContacts;

    @JsonIgnore // ignore during serialization
    @OneToMany(mappedBy = "applicant", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Attachment> attachments;

    @JsonIgnore // ignore during serialization
    @OneToOne(mappedBy = "applicant", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Address address;

    @JsonIgnore // ignore during serialization
    @OneToOne(mappedBy = "applicant", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private NationalIdentity nationalIdentity;

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

    @PrePersist
    public void onPrePersist() {
        this.submissionDate = ZonedDateTime.now();
    }
    public void setSubmissionStatus(String submissionStatus) {
        this.submissionStatus = submissionStatus;
    }

    public void setDependencies(List<Dependency> dependencies) {
        this.dependencies = dependencies;
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

}
