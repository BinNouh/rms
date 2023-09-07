package com.waseel.rms.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;

@Getter
@Entity
public class Dependency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dependency_id;

    @NotBlank(message = "Name is required.")
    private String name;

    @NotBlank(message = "Kinship is required.")
    private String kinship;

    @NotNull(message = "Date of birth is required.")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dateOfBirth;

    @ManyToOne
    @JoinColumn(name = "applicant")
    private Applicant applicant;

    // Constructors, getters, setters, etc.

    public Dependency() {}

    public void setDependency_id(Long id) {
        this.dependency_id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setKinship(String kinship) {
        this.kinship = kinship;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setApplicant(Applicant applicant) {
        this.applicant = applicant;
    }
}
