package com.waseel.rms.controller;

import com.waseel.rms.entity.NationalIdentity;
import com.waseel.rms.service.NationalIdentityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/dashboard/national-identities")
public class NationalIdentityController {

    @Autowired
    private NationalIdentityService nationalIdentityService;

    @GetMapping
    public List<NationalIdentity> getAllNationalIdentities() {
        return nationalIdentityService.getAllNationalIdentities();
    }

    @GetMapping("/{id}")
    public Optional<NationalIdentity> getNationalIdentityById(@PathVariable Long id) {
        return nationalIdentityService.getNationalIdentityById(id);
    }

    @PostMapping
    public NationalIdentity saveNationalIdentity(@RequestBody NationalIdentity nationalIdentity) {
        return nationalIdentityService.saveNationalIdentity(nationalIdentity);
    }

    @PutMapping("/{id}")
    public NationalIdentity updateNationalIdentity(@PathVariable Long id, @RequestBody NationalIdentity newNationalIdentity) {
        return nationalIdentityService.updateNationalIdentity(id, newNationalIdentity);
    }

    @DeleteMapping("/{id}")
    public void deleteNationalIdentity(@PathVariable Long id) {
        nationalIdentityService.deleteNationalIdentity(id);
    }
}
