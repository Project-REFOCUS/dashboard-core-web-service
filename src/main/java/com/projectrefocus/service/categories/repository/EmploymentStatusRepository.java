package com.projectrefocus.service.categories.repository;

import com.projectrefocus.service.categories.entity.EmploymentStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface EmploymentStatusRepository extends JpaRepository<EmploymentStatusEntity, Integer> {
    @Query(
            "SELECT CAST(COALESCE(COUNT(ese.id), 0) AS integer) FROM EmploymentStatusEntity ese " +
            "INNER JOIN FETCH CensusTractEntity cte ON cte.id = ese.censusTract.id " +
            "INNER JOIN FETCH CountyEntity ce ON ce.id = cte.county.id " +
            "INNER JOIN FETCH StateEntity se ON se.id = ce.state.id " +
            "WHERE se.id = :stateId"
    )
    Integer doesCategoryIncludeStates(Byte stateId);
}
