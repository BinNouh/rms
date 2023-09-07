package com.waseel.rms.service;

import com.waseel.rms.entity.NationalIdentity;
import com.waseel.rms.repository.NationalIdentityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NationalIdentityService {

    @Autowired
    private NationalIdentityRepository nationalIdentityRepository;

    public List<NationalIdentity> getAllNationalIdentities() {
        return nationalIdentityRepository.findAll();
    }

    public Optional<NationalIdentity> getNationalIdentityById(Long id) {
        return nationalIdentityRepository.findById(id);
    }

    public NationalIdentity saveNationalIdentity(NationalIdentity nationalIdentity) {
        return nationalIdentityRepository.save(nationalIdentity);
    }

    public NationalIdentity updateNationalIdentity(Long id, NationalIdentity newNationalIdentity) {
        return nationalIdentityRepository.findById(id)
                .map(nationalIdentity -> {
                    nationalIdentity.setIdNumber(newNationalIdentity.getIdNumber());
                    nationalIdentity.setPlaceOfIssue(newNationalIdentity.getPlaceOfIssue());
                    nationalIdentity.setExpiryDate(newNationalIdentity.getExpiryDate());
                    nationalIdentity.setDateOfBirth(newNationalIdentity.getDateOfBirth());
                    nationalIdentity.setPlaceOfBirth(newNationalIdentity.getPlaceOfBirth());
                    return nationalIdentityRepository.save(nationalIdentity);
                })
                .orElseGet(() -> {
                    newNationalIdentity.setNid_id(id);
                    return nationalIdentityRepository.save(newNationalIdentity);
                });
    }

    public void deleteNationalIdentity(Long id) {
        nationalIdentityRepository.deleteById(id);
    }
}
