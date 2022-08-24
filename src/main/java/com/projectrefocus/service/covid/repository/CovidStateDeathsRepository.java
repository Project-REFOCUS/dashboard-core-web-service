package com.projectrefocus.service.covid.repository;

import com.projectrefocus.service.covid.entity.CovidStateDeathsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CovidStateDeathsRepository extends JpaRepository<CovidStateDeathsEntity, Integer> {

    List<CovidStateDeathsEntity> getAllByStateId(Byte stateId);
}
