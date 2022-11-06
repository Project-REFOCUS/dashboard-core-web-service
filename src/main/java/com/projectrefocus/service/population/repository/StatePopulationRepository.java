package com.projectrefocus.service.population.repository;

import com.projectrefocus.service.population.entity.StatePopulationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StatePopulationRepository extends JpaRepository<StatePopulationEntity, Byte> {

    @Query(
            "SELECT spe FROM StatePopulationEntity spe " +
            "INNER JOIN FETCH StateEntity se ON se.id = spe.state.id " +
            "WHERE se.shortName IN (:states)"
    )
    List<StatePopulationEntity> getPopulationForStates(List<String> states);
}
