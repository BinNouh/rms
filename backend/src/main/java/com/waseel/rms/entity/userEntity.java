package com.waseel.rms.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class userEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;

    // Keycloak Identifier for User
    private String keycloakId;

    @OneToOne(mappedBy="user", cascade = CascadeType.ALL)
    private Applicant applicant;

    @OneToOne(mappedBy="user", cascade = CascadeType.ALL)
    private Recruiter recruiter;


    // Constructors, getters, setters, etc.

    public userEntity() {}

    public void setUser_id(Long id) {
        this.user_id = id;
    }

    public void setKeycloakId(String keycloakId) {
        this.keycloakId = keycloakId;
    }

    public void setApplicant(Applicant applicant) {
        this.applicant = applicant;
    }

    public void setRecruiter(Recruiter recruiter) {
        this.recruiter = recruiter;
    }

}
