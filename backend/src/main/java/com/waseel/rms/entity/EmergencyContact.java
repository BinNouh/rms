package com.waseel.rms.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
@Entity
public class EmergencyContact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ec_id;

    @NotBlank(message = "Name is required.")
    private String name;

    @NotBlank(message = "Kinship is required.")
    private String kinship;

    @NotNull(message = "Phone number is required.")
    @Size(min = 10, max = 13, message = "Mobile number must be at least 10 digits.")
    private Long phoneNumber;

    @ManyToOne
    @JoinColumn(name = "applicant_id")
    private Applicant applicant;

    // Constructors, getters, setters, etc.

    public EmergencyContact() {}

    public void setEc_id(Long id) {
        this.ec_id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setKinship(String kinship) {
        this.kinship = kinship;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setApplicant(Applicant applicant) {
        this.applicant = applicant;
    }
}
