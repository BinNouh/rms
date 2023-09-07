package com.waseel.rms.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.Date;

@Getter
@Entity
public class NationalIdentity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long nid_id;

    @NotBlank(message = "ID Number is required.")
    private String idNumber;

    @NotNull(message = "Expiry Date is required.")
    private Date expiryDate;

    @NotBlank(message = "Place of Issue is required.")
    private String placeOfIssue;

    @NotNull(message = "Date of Birth is required.")
    private Date dateOfBirth;

    @NotBlank(message = "Place of Birth is required.")
    private String placeOfBirth;

    @OneToOne
    @JoinColumn(name = "applicant")
    private Applicant applicant;

    // Constructors, getters, setters, etc.

    public NationalIdentity() {}

    public void setNid_id(Long id) {
        this.nid_id = id;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public void setPlaceOfIssue(String placeOfIssue) {
        this.placeOfIssue = placeOfIssue;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setPlaceOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }

    public void setApplicant(Applicant applicant) {
        this.applicant = applicant;
    }
}
