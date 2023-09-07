package com.waseel.rms.service;

import com.waseel.rms.entity.Dependency;
import com.waseel.rms.repository.DependencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DependencyService {

    @Autowired
    private DependencyRepository dependencyRepository;

    public List<Dependency> getAllDependencies() {
        return dependencyRepository.findAll();
    }

    public Optional<Dependency> getDependencyById(Long id) {
        return dependencyRepository.findById(id);
    }

    public Dependency saveDependency(Dependency dependency) {
        return dependencyRepository.save(dependency);
    }

    public Dependency updateDependency(Long id, Dependency newDependency) {
        return dependencyRepository.findById(id)
                .map(dependency -> {
                    dependency.setName(newDependency.getName());
                    dependency.setKinship(newDependency.getKinship());
                    dependency.setDateOfBirth(newDependency.getDateOfBirth());
                    return dependencyRepository.save(dependency);
                })
                .orElseGet(() -> {
                    newDependency.setDependency_id(id);
                    return dependencyRepository.save(newDependency);
                });
    }

    public void deleteDependency(Long id) {
        dependencyRepository.deleteById(id);
    }
}
