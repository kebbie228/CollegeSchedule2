package org.itstep.repositories;


import org.itstep.model.Specialization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpecializationRepository extends JpaRepository<Specialization,Long> {

   Specialization findBySpecializationName(String specializationName);

}
