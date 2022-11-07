package com.projectrefocus.service.covid.service;

import com.projectrefocus.service.common.dto.MetricDto;
import com.projectrefocus.service.covid.entity.CovidStateDeathsEntity;
import com.projectrefocus.service.covid.repository.CovidStateDeathsRepository;
import com.projectrefocus.service.covid.utils.CovidDeathsMetricTransformer;
import com.projectrefocus.service.covid.utils.CovidMetricTransformer;
import com.projectrefocus.service.covid.utils.CovidServiceUtils;
import com.projectrefocus.service.population.service.PopulationService;
import com.projectrefocus.service.request.enums.DataOrientation;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class CovidDeathsService {

    private final CovidStateDeathsRepository covidStateDeathsRepository;
    private final PopulationService populationService;
    private final EntityManager em;

    public CovidDeathsService(CovidStateDeathsRepository covidStateDeathsRepository, PopulationService populationService, EntityManager em) {
        this.covidStateDeathsRepository = covidStateDeathsRepository;
        this.populationService = populationService;
        this.em = em;
    }

    private Integer getAccumulatedStartingAggregate(Date startDate, List<String> states) {
        return states.isEmpty() ?
                covidStateDeathsRepository.aggregatedDeathsUntilDate(startDate) :
                covidStateDeathsRepository.aggregatedStateDeathsUntilDate(states, startDate);
    }

    private List<CovidStateDeathsEntity> getWeeklyDeathsOnOrAfterDate(List<String> states, Date startDate, Boolean allStates) {
        TypedQuery<CovidStateDeathsEntity> q = em.createNamedQuery(allStates ? "allWeeklyDeaths" : "stateWeeklyDeaths", CovidStateDeathsEntity.class);
        q.setParameter("startDate", startDate);
        if (!allStates) {
            q.setParameter("states", states);
        }
        return q.getResultList();
    }

    private List<CovidStateDeathsEntity> getDeathsEntityData(List<String> states, DataOrientation orientation, Date startDate, Boolean allStates) {
        return switch (orientation) {
            case weekly,weeklyPer100K -> getWeeklyDeathsOnOrAfterDate(states, startDate, allStates);
            default -> allStates ?
                    covidStateDeathsRepository.getAllDeathsOnOrAfterDate(CovidServiceUtils.adjustedDate(startDate, orientation)) :
                    covidStateDeathsRepository.getStateDeathsOnOrAfterDate(states, CovidServiceUtils.adjustedDate(startDate, orientation));
        };
    }

    public List<MetricDto> getData(List<String> states, DataOrientation orientation, Date startDate) {
        boolean fetchForAllStates = states.isEmpty();
        List<CovidStateDeathsEntity> deaths = getDeathsEntityData(states, orientation, startDate, fetchForAllStates);
        switch (orientation) {
            case cumulative:
                Integer startingAggregate = fetchForAllStates ?
                        covidStateDeathsRepository.aggregatedDeathsUntilDate(startDate) :
                        covidStateDeathsRepository.aggregatedStateDeathsUntilDate(states, startDate);
                return CovidMetricTransformer.toCumulative(deaths, startingAggregate);

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
                return CovidMetricTransformer.toDaily(deaths);

            case daily7DayAvg:
                return CovidMetricTransformer.toDailyNDayAverage(deaths, 7);

            case daily14DayAvg:
                return CovidMetricTransformer.toDailyNDayAverage(deaths, 14);

            case daily7DayAvgPer100K:
                denominator = populationService.aggregatedPopulation(states);
                return CovidMetricTransformer.toDailyNDayAveragePer100K(deaths, 7, denominator);

            case daily14DayAvgPer100K:
                denominator = populationService.aggregatedPopulation(states);
                return CovidMetricTransformer.toDailyNDayAveragePer100K(deaths, 14, denominator);

            case weekly:
                return CovidDeathsMetricTransformer.toWeekly(deaths);

            case weeklyPer100K:
                denominator = populationService.aggregatedPopulation(states);
                return CovidDeathsMetricTransformer.toWeeklyPer100K(deaths, denominator);

            case percentChangeInDailyOver7:
                return CovidMetricTransformer.toDailyPercentChangeInNDayAverage(deaths, 7);

            case percentChangeInDailyOver14:
                return CovidMetricTransformer.toDailyPercentChangeInNDayAverage(deaths, 14);
        }

        return new ArrayList<>();
    }
}
