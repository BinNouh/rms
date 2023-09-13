package com.waseel.rms.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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
    @Min(value = 100000000L, message = "Phone number must be at least 10 digits.")
    @Max(value = 9999999999999L, message = "Phone number can be at most 13 digits.")
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
