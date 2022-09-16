package com.projectrefocus.service.covid.service;

import com.projectrefocus.service.covid.dto.CovidMetricDto;
import com.projectrefocus.service.covid.entity.CovidStateTestsEntity;
import com.projectrefocus.service.covid.repository.CovidStateTestsRepository;
import com.projectrefocus.service.covid.utils.CovidServiceUtils;
import com.projectrefocus.service.covid.utils.Transformer;
import com.projectrefocus.service.population.service.PopulationService;
import com.projectrefocus.service.request.enums.DataOrientation;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class CovidTestsService {

    private final CovidStateTestsRepository covidStateTestsRepository;
    private final PopulationService populationService;

    public CovidTestsService(CovidStateTestsRepository covidStateTestsRepository, PopulationService populationService) {
        this.covidStateTestsRepository = covidStateTestsRepository;
        this.populationService = populationService;
    }

    public List<CovidMetricDto> getData(List<String> states, DataOrientation orientation, Date startDate) {
        boolean fetchForAllStates = states.isEmpty();
        List<CovidStateTestsEntity> tests = states.isEmpty() ?
                covidStateTestsRepository.getAllTestsOnOrAfterDate(CovidServiceUtils.adjustedDate(startDate, orientation)) :
                covidStateTestsRepository.getStateCasesOnOrAfterDate(states, CovidServiceUtils.adjustedDate(startDate, orientation));
        switch (orientation) {
            case cumulative:
                Integer startingAggregate = fetchForAllStates ?
                        covidStateTestsRepository.aggregatedTestsUntilDate(startDate) :
                        covidStateTestsRepository.aggregatedStateTestsUntilDate(states, startDate);
                return Transformer.toCumulativeTests(tests, startingAggregate);

            case daily:
                return Transformer.toDailyTests(tests);

            case dailyPer100K:
                Integer denominator = populationService.aggregatedPopulation(states);
                return Transformer.toDailyTestsPer100K(tests, denominator);

            case daily7DayAvg:
                return Transformer.toDailyTestsNDayAverage(tests, 7);

            case daily14DayAvg:
                return Transformer.toDailyTestsNDayAverage(tests, 14);

        }

        return new ArrayList<>();
    }
}
