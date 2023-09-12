package com.waseel.rms.controller;

import com.waseel.rms.entity.EmergencyContact;
import com.waseel.rms.service.EmergencyContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/applicant/emergency-contacts")
public class EmergencyContactController {


    @Autowired
    private EmergencyContactService emergencyContactService;

    @GetMapping
    public List<EmergencyContact> getAllEmergencyContacts() {
        return emergencyContactService.getAllEmergencyContacts();
    }

    @GetMapping("/{id}")
    public Optional<EmergencyContact> getEmergencyContactById(@PathVariable Long id) {
        return emergencyContactService.getEmergencyContactById(id);
    }

    @PostMapping
    public EmergencyContact addEmergencyContact(@RequestBody EmergencyContact emergencyContact) {
        return emergencyContactService.addEmergencyContact(emergencyContact);
    }

    @PutMapping("/{id}")
    public EmergencyContact updateEmergencyContact(@PathVariable Long id, @RequestBody EmergencyContact emergencyContact) {
        return emergencyContactService.updateEmergencyContact(id, emergencyContact);
    }

    @DeleteMapping("/{id}")
    public void deleteEmergencyContact(@PathVariable Long id) {
        emergencyContactService.deleteEmergencyContact(id);
    }
}
