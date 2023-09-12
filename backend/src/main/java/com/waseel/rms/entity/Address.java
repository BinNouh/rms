package com.waseel.rms.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;

@Getter
@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long address_id;

    @NotBlank(message = "Address line is required.")
    private String addressLine;

    @NotBlank(message = "Country is required.")
    private String country;

    @NotBlank(message = "City is required.")
    private String city;

    @NotNull(message = "Zip code is required.")
    @Size(min = 5, max = 10, message = "Zip code must be at least 5 digits.")
    private Integer zipCode;

    @NotNull(message = "Additional code is required.")
    @Min(value = 1, message = "Additional code must be at least 1.")
    private Integer additionalCode;

    @NotNull(message = "Mobile number is required.")
    @Size(min = 10, max = 13, message = "Mobile number must be at least 10 digits.")
    private Long mobileNumber;

    @Email(message = "Email address is invalid.")
    @NotBlank(message = "Email address is required.")
    private String emailAddress;

    @OneToOne
    @JoinColumn(name = "applicant_id")
    private Applicant applicant;

    // Constructors, getters, setters, etc.

    public Address() {}

    public void setAddress_id(Long id) {
        this.address_id = id;
    }

    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setZipCode(Integer zipCode) {
        this.zipCode = zipCode;
    }

    public void setAdditionalCode(Integer additionalCode) {
        this.additionalCode = additionalCode;
    }

    public void setMobileNumber(Long mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public void setEmailAddress(String email) {
        this.emailAddress = email;
    }
    public void setApplicant(Applicant applicant) {
        this.applicant = applicant;
    }
}
