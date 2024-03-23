package org.itstep.services;

import org.itstep.model.Audience;
import org.itstep.repositories.AudienceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AudienceService {

    private final AudienceRepository audienceRepository;

    @Autowired
    public AudienceService(AudienceRepository audienceRepository) {
        this.audienceRepository = audienceRepository;
    }

    public List<Audience> findAll() {
        return audienceRepository.findAll();
    }

    public Audience findById(Long id) {
        Optional<Audience> foundAudience = audienceRepository.findById(id);
        return foundAudience.orElse(null);
    }

}
