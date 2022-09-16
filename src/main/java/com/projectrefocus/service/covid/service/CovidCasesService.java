package com.projectrefocus.service.covid.service;

import com.projectrefocus.service.covid.dto.CovidMetricDto;
import com.projectrefocus.service.covid.entity.CovidStateCasesEntity;
import com.projectrefocus.service.covid.repository.CovidStateCasesRepository;
import com.projectrefocus.service.covid.utils.CovidServiceUtils;
import com.projectrefocus.service.covid.utils.Transformer;
import com.projectrefocus.service.population.service.PopulationService;
import com.projectrefocus.service.request.enums.DataOrientation;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class CovidCasesService {

    private final CovidStateCasesRepository covidStateCasesRepository;
    private final PopulationService populationService;

    public CovidCasesService(CovidStateCasesRepository covidStateCasesRepository, PopulationService populationService) {
        this.covidStateCasesRepository = covidStateCasesRepository;
        this.populationService = populationService;
    }

    public List<CovidMetricDto> getData(List<String> states, DataOrientation orientation, Date startDate) {
        boolean fetchForAllStates = states.isEmpty();
        List<CovidStateCasesEntity> cases = fetchForAllStates ?
                covidStateCasesRepository.getAllCasesOnOrAfterDate(CovidServiceUtils.adjustedDate(startDate, orientation)) :
                covidStateCasesRepository.getStateCasesOnOrAfterDate(states, CovidServiceUtils.adjustedDate(startDate, orientation));
        switch (orientation) {
            case cumulative:
                Integer startingAggregate = fetchForAllStates ?
                        covidStateCasesRepository.aggregatedCasesUntilDate(startDate) :
                        covidStateCasesRepository.aggregatedStateCasesUntilDate(states, startDate);
                return Transformer.toCumulativeCases(cases, startingAggregate);

            case daily:
                return Transformer.toDailyCases(cases);

            case dailyPer100K:
                Integer denominator = populationService.aggregatedPopulation(states);
                return Transformer.toDailyCasesPer100K(cases, denominator);

            case daily7DayAvg:
                return Transformer.toDailyCasesNDayAverage(cases, 7);

            case daily14DayAvg:
                return Transformer.toDailyCasesNDayAverage(cases, 14);
        }

        return new ArrayList<>();
    }
}
