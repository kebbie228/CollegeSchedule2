package org.itstep.services;

import org.itstep.model.Day;
import org.itstep.repositories.DayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DayService {

    private final DayRepository dayRepository;

    @Autowired
    public DayService(DayRepository dayRepository) {
        this.dayRepository = dayRepository;
    }

    public List<Day> findAll() {
        return dayRepository.findAll();
    }

    public Day findById(Long id) {
        Optional<Day> foundDay = dayRepository.findById(id);
        return foundDay.orElse(null);
    }
}
