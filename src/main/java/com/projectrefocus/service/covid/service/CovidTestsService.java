package com.projectrefocus.service.covid.service;

import com.projectrefocus.service.covid.dto.CovidMetricDto;
import com.projectrefocus.service.covid.entity.CovidStateTestsEntity;
import com.projectrefocus.service.covid.repository.CovidStateTestsRepository;
import com.projectrefocus.service.covid.utils.CovidServiceUtils;
import com.projectrefocus.service.covid.utils.CovidTestsMetricTransformer;
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
                return CovidTestsMetricTransformer.toCumulativeTests(tests, startingAggregate);

            case daily:
                return CovidTestsMetricTransformer.toDailyTests(tests);

            case dailyPer100K:
                Integer denominator = populationService.aggregatedPopulation(states);
                return CovidTestsMetricTransformer.toDailyTestsPer100K(tests, denominator);

            case daily7DayAvg:
                return CovidTestsMetricTransformer.toDailyTestsNDayAverage(tests, 7);

            case daily14DayAvg:
                return CovidTestsMetricTransformer.toDailyTestsNDayAverage(tests, 14);

            case daily7DayAvgPer100K:
                denominator = populationService.aggregatedPopulation(states);
                return CovidTestsMetricTransformer.toDailyTestsNDayAveragePer100K(tests, 7, denominator);

            case daily14DayAvgPer100K:
                denominator = populationService.aggregatedPopulation(states);
                return CovidTestsMetricTransformer.toDailyTestsNDayAveragePer100K(tests, 14, denominator);

            case percentChangeInDailyOver7:
                return CovidTestsMetricTransformer.toDailyTestsPercentChangeInNDayAverage(tests, 7);

            case percentChangeInDailyOver14:
                return CovidTestsMetricTransformer.toDailyTestsPercentChangeInNDayAverage(tests, 14);
        }

        return new ArrayList<>();
    }
}
