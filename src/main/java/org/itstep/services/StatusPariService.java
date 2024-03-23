package org.itstep.services;

import org.itstep.model.StatusPari;
import org.itstep.repositories.StatusPariRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StatusPariService {
    private final StatusPariRepository statusPariRepository;
@Autowired
    public StatusPariService(StatusPariRepository statusPariRepository) {
        this.statusPariRepository = statusPariRepository;
    }

    public List<StatusPari> findAll() {
        return statusPariRepository.findAll();
    }
    public StatusPari findById(Long id) {
        Optional<StatusPari> foundStatusPari = statusPariRepository.findById(id);
        return foundStatusPari.orElse(null);
    }
}
