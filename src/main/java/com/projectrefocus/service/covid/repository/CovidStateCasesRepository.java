package com.projectrefocus.service.covid.repository;

import com.projectrefocus.service.covid.entity.CovidStateCasesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CovidStateCasesRepository extends JpaRepository<CovidStateCasesEntity, Integer> {

    List<CovidStateCasesEntity> getAllByStateId(Byte stateId);
}
