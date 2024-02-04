package com.projectrefocus.service.categories.repository;

import com.projectrefocus.service.categories.entity.MotorVehicleCollisionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MotorVehicleCollisionRepository extends JpaRepository<MotorVehicleCollisionEntity, Integer> {
    @Query(
          "SELECT CAST(COALESCE(COUNT(mvce.id), 0) AS integer) FROM MotorVehicleCollisionEntity mvce " +
          "INNER JOIN ZipcodeEntity ze ON ze.id = mvce.zipcode.id " +
          "INNER JOIN ze.cities zec " +
          "INNER JOIN zec.counties zecc " +
          "INNER JOIN StateEntity se ON se.id = zecc.state.id " +
          "WHERE se.id = :stateId"
    )
    Integer doesCategoryIncludeStates(Byte stateId);
}
