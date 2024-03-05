package org.itstep.services;



import org.itstep.model.Specialization;
import org.itstep.repositories.SpecializationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SpecializationService {
    private final SpecializationRepository  specializationRepository;

    @Autowired
    public SpecializationService(SpecializationRepository specializationRepository) {
        this.specializationRepository = specializationRepository;
    }
    public List<Specialization> findAll(){
        return specializationRepository.findAll();
    }
    public Specialization findById(Long id){
        Optional<Specialization> foundSpecialization= specializationRepository.findById(id);
        return foundSpecialization.orElse(null);
    }
    public void save(Specialization specialization){
        specializationRepository.save(specialization);
    }
    public void update(Long id, Specialization updatedSpecialization){
        updatedSpecialization.setId(id);
        specializationRepository.save(updatedSpecialization);
    }
    public void delete(Long id){
        specializationRepository.deleteById(id);
    }

    public Specialization findBySpecializationName(String specializationName){
        Optional<Specialization> foundSpecialization= Optional.ofNullable(specializationRepository.findBySpecializationName(specializationName));
        return foundSpecialization.orElse(null);
    }
}
