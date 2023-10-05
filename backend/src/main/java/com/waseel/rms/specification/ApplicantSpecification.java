package com.waseel.rms.specification;

import com.waseel.rms.entity.Applicant;
import org.springframework.data.jpa.domain.Specification;

public class ApplicantSpecification {

    public static Specification<Applicant> genderIs(String gender) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("gender"), gender);
    }

    public static Specification<Applicant> submissionStatusIs(String submissionStatus) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("submissionStatus"), submissionStatus);
    }
}
