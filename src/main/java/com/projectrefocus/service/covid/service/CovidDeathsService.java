package com.projectrefocus.service.covid.service;

import com.projectrefocus.service.covid.dto.CovidMetricDto;
import com.projectrefocus.service.covid.entity.CovidStateDeathsEntity;
import com.projectrefocus.service.covid.repository.CovidStateDeathsRepository;
import com.projectrefocus.service.covid.utils.CovidDeathsMetricTransformer;
import com.projectrefocus.service.covid.utils.CovidServiceUtils;
import com.projectrefocus.service.population.service.PopulationService;
import com.projectrefocus.service.request.enums.DataOrientation;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class CovidDeathsService {

    private final CovidStateDeathsRepository covidStateDeathsRepository;
    private final PopulationService populationService;

    public CovidDeathsService(CovidStateDeathsRepository covidStateDeathsRepository, PopulationService populationService) {
        this.covidStateDeathsRepository = covidStateDeathsRepository;
        this.populationService = populationService;
    }

    private Integer getAccumulatedStartingAggregate(Date startDate, List<String> states) {
        return states.isEmpty() ?
                covidStateDeathsRepository.aggregatedDeathsUntilDate(startDate) :
                covidStateDeathsRepository.aggregatedStateDeathsUntilDate(states, startDate);
    }

    public List<CovidMetricDto> getData(List<String> states, DataOrientation orientation, Date startDate) {
        boolean fetchForAllStates = states.isEmpty();
        List<CovidStateDeathsEntity> deaths = fetchForAllStates ?
                covidStateDeathsRepository.getAllDeathsOnOrAfterDate(CovidServiceUtils.adjustedDate(startDate, orientation)) :
                covidStateDeathsRepository.getStateDeathsOnOrAfterDate(states, CovidServiceUtils.adjustedDate(startDate, orientation));
        switch (orientation) {
            case cumulative:
                Integer startingAggregate = fetchForAllStates ?
                        covidStateDeathsRepository.aggregatedDeathsUntilDate(startDate) :
                        covidStateDeathsRepository.aggregatedStateDeathsUntilDate(states, startDate);
                return CovidDeathsMetricTransformer.toCumulativeDeaths(deaths, startingAggregate);

            case mortalityRate:
                Integer denominator = populationService.aggregatedPopulation(states);
                startingAggregate = getAccumulatedStartingAggregate(startDate, states);
                return CovidDeathsMetricTransformer.toMortalityRate(deaths, startingAggregate, denominator);

            case mortalityRateOver7Days:
                denominator = populationService.aggregatedPopulation(states);
                startingAggregate = getAccumulatedStartingAggregate(startDate, states);
                return CovidDeathsMetricTransformer.toMortalityRateOverNDays(deaths, startingAggregate, denominator, 7);

            case mortalityRateOver14Days:
                denominator = populationService.aggregatedPopulation(states);
                startingAggregate = getAccumulatedStartingAggregate(startDate, states);
                return CovidDeathsMetricTransformer.toMortalityRateOverNDays(deaths, startingAggregate, denominator, 14);

            case percentChangeInMortalityRate:
                denominator = populationService.aggregatedPopulation(states);
                startingAggregate = getAccumulatedStartingAggregate(startDate, states);
                return CovidDeathsMetricTransformer.toMortalityRatePercentChange(deaths, startingAggregate, denominator);

            case percentChangeInMortalityRateOver7:
                denominator = populationService.aggregatedPopulation(states);
                startingAggregate = getAccumulatedStartingAggregate(startDate, states);
                return CovidDeathsMetricTransformer.toMortalityRatePercentChangeOverNDays(deaths, startingAggregate, denominator, 7);

            case percentChangeInMortalityRateOver14:
                denominator = populationService.aggregatedPopulation(states);
                startingAggregate = getAccumulatedStartingAggregate(startDate, states);
                return CovidDeathsMetricTransformer.toMortalityRatePercentChangeOverNDays(deaths, startingAggregate, denominator, 14);

            case daily:
                return CovidDeathsMetricTransformer.toDailyDeaths(deaths);

            case daily7DayAvg:
                return CovidDeathsMetricTransformer.toDailyDeathsNDayAverage(deaths, 7);

            case daily14DayAvg:
                return CovidDeathsMetricTransformer.toDailyDeathsNDayAverage(deaths, 14);

            case daily7DayAvgPer100K:
                denominator = populationService.aggregatedPopulation(states);
                return CovidDeathsMetricTransformer.toDailyDeathsNDayAveragePer100K(deaths, 7, denominator);

            case daily14DayAvgPer100K:
                denominator = populationService.aggregatedPopulation(states);
                return CovidDeathsMetricTransformer.toDailyDeathsNDayAveragePer100K(deaths, 14, denominator);

            case percentChangeInDailyOver7:
                return CovidDeathsMetricTransformer.toDailyDeathsPercentChangeInNDayAverage(deaths, 7);

            case percentChangeInDailyOver14:
                return CovidDeathsMetricTransformer.toDailyDeathsPercentChangeInNDayAverage(deaths, 14);
        }

        return new ArrayList<>();
    }
}
