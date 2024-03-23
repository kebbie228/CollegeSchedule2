package org.itstep.repositories;

import org.itstep.model.Para;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ParaRepository extends JpaRepository<Para,Long> {
}