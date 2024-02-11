package com.projectrefocus.service.categories.repository;

import com.projectrefocus.service.categories.entity.CovidCasesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CovidCasesRepository extends JpaRepository<CovidCasesEntity, Integer> {
    @Query(
            "SELECT CAST(COALESCE(COUNT(cce.id), 0) AS integer) FROM CovidCasesEntity cce " +
            "INNER JOIN FETCH StateEntity se ON se.id = cce.state.id " +
            "WHERE se.id = :stateId"
    )
    Integer doesCategoryIncludeState(Byte stateId);
}
