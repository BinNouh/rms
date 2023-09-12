package com.waseel.rms.controller;

import com.waseel.rms.entity.Dependency;
import com.waseel.rms.service.DependencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/applicant/dependencies")
public class DependencyController {

    @Autowired
    private DependencyService dependencyService;

    @GetMapping
    public List<Dependency> getAllDependencies() {
        return dependencyService.getAllDependencies();
    }

    @GetMapping("/{id}")
    public Optional<Dependency> getDependencyById(@PathVariable Long id) {
        return dependencyService.getDependencyById(id);
    }

    @PostMapping
    public Dependency saveDependency(@RequestBody Dependency dependency) {
        return dependencyService.saveDependency(dependency);
    }

    @PutMapping("/{id}")
    public Dependency updateDependency(@PathVariable Long id, @RequestBody Dependency newDependency) {
        return dependencyService.updateDependency(id, newDependency);
    }

    @DeleteMapping("/{id}")
    public void deleteDependency(@PathVariable Long id) {
        dependencyService.deleteDependency(id);
    }
}
