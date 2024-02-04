package com.projectrefocus.service.categories.repository;

import com.projectrefocus.service.categories.entity.SupplementalNutritionAssistanceProgramEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SupplementalNutritionAssistanceProgramRepository extends JpaRepository<SupplementalNutritionAssistanceProgramEntity, Integer> {
    @Query(
            "SELECT CAST(COALESCE(COUNT(snape.id), 0) AS integer) FROM SupplementalNutritionAssistanceProgramEntity snape " +
            "INNER JOIN FETCH CountyEntity ce ON ce.id = snape.county.id " +
            "INNER JOIN FETCH StateEntity se ON se.id = ce.state.id " +
            "WHERE se.id = :stateId"
    )
    Integer doesCategoryIncludeStates(Byte stateId);
}
