package org.itstep.repositories;

import org.itstep.model.Audience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AudienceRepository extends JpaRepository<Audience,Long> {
}
