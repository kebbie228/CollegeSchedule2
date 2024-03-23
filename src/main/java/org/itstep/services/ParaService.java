package org.itstep.services;

import org.itstep.model.Para;
import org.itstep.repositories.ParaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ParaService {

    private final ParaRepository paraRepository;

    @Autowired
    public ParaService(ParaRepository paraRepository) {
        this.paraRepository = paraRepository;
    }

    public List<Para> findAll() {
        return paraRepository.findAll();
    }

    public Para findById(Long id) {
        Optional<Para> foundPara = paraRepository.findById(id);
        return foundPara.orElse(null);
    }
}
