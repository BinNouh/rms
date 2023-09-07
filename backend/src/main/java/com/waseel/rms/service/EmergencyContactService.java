package com.waseel.rms.service;

import com.waseel.rms.entity.EmergencyContact;
import com.waseel.rms.repository.EmergencyContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmergencyContactService {

    @Autowired
    private EmergencyContactRepository emergencyContactRepository;

    public List<EmergencyContact> getAllEmergencyContacts() {
        return emergencyContactRepository.findAll();
    }

    public Optional<EmergencyContact> getEmergencyContactById(Long id) {
        return emergencyContactRepository.findById(id);
    }

    public EmergencyContact addEmergencyContact(EmergencyContact emergencyContact) {
        return emergencyContactRepository.save(emergencyContact);
    }

    public EmergencyContact updateEmergencyContact(Long id, EmergencyContact newEmergencyContact) {
        return emergencyContactRepository.findById(id)
                .map(emergencyContact -> {
                    emergencyContact.setName(newEmergencyContact.getName());
                    emergencyContact.setKinship(newEmergencyContact.getKinship());
                    emergencyContact.setPhoneNumber(newEmergencyContact.getPhoneNumber());
                    return emergencyContactRepository.save(emergencyContact);
                })
                .orElseGet(() -> {
                    newEmergencyContact.setEc_id(id);
                    return emergencyContactRepository.save(newEmergencyContact);
                });
    }

    public void deleteEmergencyContact(Long id) {
        emergencyContactRepository.deleteById(id);
    }
}
