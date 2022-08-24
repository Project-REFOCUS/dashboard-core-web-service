package com.projectrefocus.service.covid.service;

import com.projectrefocus.service.covid.dto.CovidStateCasesDto;
import com.projectrefocus.service.covid.dto.CovidStateDeathsDto;
import com.projectrefocus.service.covid.dto.CovidStateTestsDto;
import com.projectrefocus.service.covid.entity.CovidStateCasesEntity;
import com.projectrefocus.service.covid.entity.CovidStateDeathsEntity;
import com.projectrefocus.service.covid.entity.CovidStateTestsEntity;
import com.projectrefocus.service.covid.repository.CovidStateCasesRepository;
import com.projectrefocus.service.covid.repository.CovidStateDeathsRepository;
import com.projectrefocus.service.covid.repository.CovidStateTestsRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CovidServiceImpl implements CovidService {

    private final CovidStateCasesRepository covidStateCasesRepository;
    private final CovidStateDeathsRepository covidStateDeathsRepository;
    private final CovidStateTestsRepository covidStateTestsRepository;

    public CovidServiceImpl(CovidStateCasesRepository covidStateCasesRepository, CovidStateDeathsRepository covidStateDeathsRepository, CovidStateTestsRepository covidStateTestsRepository) {
        this.covidStateCasesRepository = covidStateCasesRepository;
        this.covidStateDeathsRepository = covidStateDeathsRepository;
        this.covidStateTestsRepository = covidStateTestsRepository;
    }


    @Override
    public List<CovidStateCasesDto> getCasesByState(Byte stateId) {
        return covidStateCasesRepository.getAllByStateId(stateId)
                .stream()
                .map(CovidStateCasesEntity::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CovidStateDeathsDto> getDeathsByState(Byte stateId) {
        return covidStateDeathsRepository.getAllByStateId(stateId)
                .stream()
                .map(CovidStateDeathsEntity::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CovidStateTestsDto> getTestsByState(Byte stateId) {
        return covidStateTestsRepository.getAllByStateId(stateId)
                .stream()
                .map(CovidStateTestsEntity::toDto)
                .collect(Collectors.toList());
    }
}
