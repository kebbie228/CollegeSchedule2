package org.itstep.repositories;

import org.itstep.model.StatusPari;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusPariRepository extends JpaRepository<StatusPari,Long> {

}
