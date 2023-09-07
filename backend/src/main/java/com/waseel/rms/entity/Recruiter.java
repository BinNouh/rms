package com.waseel.rms.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class Recruiter {
    // Getters and Setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recruiter_id;

    // Keycloak Identifier for Recruiter
    private String keycloakId;

    @OneToOne(mappedBy = "recruiter")
    private userEntity user;

    // Constructors
    public Recruiter() {}
    public void setKeycloakId(String keycloakId) {
        this.keycloakId = keycloakId;
    }
    public void setRecruiter_id(Long id) {
        this.recruiter_id = id;
    }

    public void setUser(userEntity user) {
        this.user = user;
    }
}
