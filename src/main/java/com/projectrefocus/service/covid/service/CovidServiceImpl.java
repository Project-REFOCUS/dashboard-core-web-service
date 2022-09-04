package com.projectrefocus.service.covid.service;

import com.projectrefocus.service.covid.dto.CovidStateMetricDto;
import com.projectrefocus.service.covid.entity.CovidStateCasesEntity;
import com.projectrefocus.service.covid.entity.CovidStateDeathsEntity;
import com.projectrefocus.service.covid.entity.CovidStateTestsEntity;
import com.projectrefocus.service.covid.repository.CovidStateCasesRepository;
import com.projectrefocus.service.covid.repository.CovidStateDeathsRepository;
import com.projectrefocus.service.covid.repository.CovidStateTestsRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
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

    public List<CovidStateMetricDto> getCovidCasesData(List<String> states, Date startDate) {
        List<CovidStateCasesEntity> cases = states.isEmpty() ?
                covidStateCasesRepository.getAllCasesOnOrAfterDate(startDate) :
                covidStateCasesRepository.getStateCasesOnOrAfterDate(states, startDate);

        return cases
                .stream()
                .map(CovidStateCasesEntity::toDto)
                .collect(Collectors.toList());
    }

    public List<CovidStateMetricDto> getCovidDeathsData(List<String> states, Date startDate) {
        List<CovidStateDeathsEntity> deaths = states.isEmpty() ?
                covidStateDeathsRepository.getAllDeathsOnOrAfterDate(startDate) :
                covidStateDeathsRepository.getStateDeathsOnOrAfterDate(states, startDate);

        return deaths
                .stream()
                .map(CovidStateDeathsEntity::toDto)
                .collect(Collectors.toList());
    }

    public List<CovidStateMetricDto> getCovidTestsData(List<String> states, Date startDate) {
        List<CovidStateTestsEntity> tests = states.isEmpty() ?
                covidStateTestsRepository.getAllTestsOnOrAfterDate(startDate) :
                covidStateTestsRepository.getStateCasesOnOrAfterDate(states, startDate);

        return tests
                .stream()
                .map(CovidStateTestsEntity::toDto)
                .collect(Collectors.toList());
    }
}
