package com.projectrefocus.service.categories.repository;

import com.projectrefocus.service.categories.entity.PopulationEstimateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PopulationEstimateRepository extends JpaRepository<PopulationEstimateEntity, Integer> {
    @Query(
            "SELECT CAST(COALESCE(COUNT(pee.id), 0) AS integer) FROM PopulationEstimateEntity pee " +
            "INNER JOIN FETCH StateEntity se ON se.id = pee.state.id " +
            "WHERE se.id = :stateId"
    )
    Integer doesCategoryIncludeState(Byte stateId);
}
