package com.projectrefocus.service.covid.service;

import com.projectrefocus.service.covid.dto.CovidMetricDto;
import com.projectrefocus.service.covid.dto.CovidStateMetricDto;
import com.projectrefocus.service.covid.entity.CovidStateCasesEntity;
import com.projectrefocus.service.covid.entity.CovidStateDeathsEntity;
import com.projectrefocus.service.covid.entity.CovidStateTestsEntity;
import com.projectrefocus.service.covid.repository.CovidStateCasesRepository;
import com.projectrefocus.service.covid.repository.CovidStateDeathsRepository;
import com.projectrefocus.service.covid.repository.CovidStateTestsRepository;
import com.projectrefocus.service.covid.utils.Transformer;
import com.projectrefocus.service.request.enums.DataOrientation;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public List<CovidMetricDto> getCovidCasesData(List<String> states, DataOrientation orientation, Date startDate) {
        boolean fetchForAllStates = states.isEmpty();
        List<CovidStateCasesEntity> cases = fetchForAllStates ?
                covidStateCasesRepository.getAllCasesOnOrAfterDate(startDate) :
                covidStateCasesRepository.getStateCasesOnOrAfterDate(states, startDate);
        switch (orientation) {
            case cumulative:
                Integer startingAggregate = fetchForAllStates ?
                        covidStateCasesRepository.aggregatedCasesUntilDate(startDate) :
                        covidStateCasesRepository.aggregatedStateCasesUntilDate(states, startDate);
                return Transformer.toCumulativeCases(cases, startingAggregate);

            case daily:
                return cases.stream().map(CovidStateCasesEntity::toDto).collect(Collectors.toList());
        }

        return new ArrayList<>();
    }

    public List<CovidMetricDto> getCovidDeathsData(List<String> states, DataOrientation orientation, Date startDate) {
        boolean fetchForAllStates = states.isEmpty();
        List<CovidStateDeathsEntity> deaths = fetchForAllStates ?
                covidStateDeathsRepository.getAllDeathsOnOrAfterDate(startDate) :
                covidStateDeathsRepository.getStateDeathsOnOrAfterDate(states, startDate);
        switch (orientation) {
            case cumulative:
                Integer startingAggregate = fetchForAllStates ?
                        covidStateDeathsRepository.aggregatedDeathsUntilDate(startDate) :
                        covidStateDeathsRepository.aggregatedStateDeathsUntilDate(states, startDate);
                return Transformer.toCumulativeDeaths(deaths, startingAggregate);

            case daily:
                return deaths.stream().map(CovidStateDeathsEntity::toDto).collect(Collectors.toList());
        }

        return new ArrayList<>();
    }

    public List<CovidMetricDto> getCovidTestsData(List<String> states, DataOrientation orientation, Date startDate) {
        boolean fetchForAllStates = states.isEmpty();
        List<CovidStateTestsEntity> tests = states.isEmpty() ?
                covidStateTestsRepository.getAllTestsOnOrAfterDate(startDate) :
                covidStateTestsRepository.getStateCasesOnOrAfterDate(states, startDate);
        switch (orientation) {
            case cumulative:
                Integer startingAggregate = fetchForAllStates ?
                        covidStateTestsRepository.aggregatedDeathsUntilDate(startDate) :
                        covidStateTestsRepository.aggregatedStateDeathsUntilDate(states, startDate);
                return Transformer.toCumulativeTests(tests, startingAggregate);

            case daily:
                return tests.stream().map(CovidStateTestsEntity::toDto).collect(Collectors.toList());

        }

        return new ArrayList<>();
    }
}
