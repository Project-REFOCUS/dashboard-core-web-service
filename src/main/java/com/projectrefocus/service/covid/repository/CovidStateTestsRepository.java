package com.projectrefocus.service.covid.repository;

import com.projectrefocus.service.covid.entity.CovidStateTestsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CovidStateTestsRepository extends JpaRepository<CovidStateTestsEntity, Integer> {

    List<CovidStateTestsEntity> getAllByStateId(Byte stateId);
}
