package com.projectrefocus.service.categories.repository;

import com.projectrefocus.service.categories.entity.PoliceShootingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PoliceShootingRepository extends JpaRepository<PoliceShootingEntity, Integer> {
    @Query(
            "SELECT CAST(COALESCE(COUNT(pse.id), 0) AS integer) FROM PoliceShootingEntity pse " +
            "INNER JOIN CityEntity ce ON ce.id = pse.city.id " +
            "INNER JOIN ce.counties cec " +
            "INNER JOIN StateEntity se ON se.id = cec.state.id " +
            "WHERE se.id = :stateId"
    )
    Integer doesCategoryIncludeState(Byte stateId);
}
