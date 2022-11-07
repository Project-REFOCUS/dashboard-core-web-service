package com.projectrefocus.service.covid.service;

import com.projectrefocus.service.common.dto.MetricDto;
import com.projectrefocus.service.covid.entity.CovidStateTestsEntity;
import com.projectrefocus.service.covid.repository.CovidStateTestsRepository;
import com.projectrefocus.service.covid.utils.CovidServiceUtils;
import com.projectrefocus.service.covid.utils.CovidTestsMetricTransformer;
import com.projectrefocus.service.population.service.PopulationService;
import com.projectrefocus.service.request.enums.DataOrientation;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class CovidTestsService {

    private final CovidStateTestsRepository covidStateTestsRepository;
    private final PopulationService populationService;
    private final EntityManager em;

    public CovidTestsService(CovidStateTestsRepository covidStateTestsRepository, PopulationService populationService, EntityManager em) {
        this.covidStateTestsRepository = covidStateTestsRepository;
        this.populationService = populationService;
        this.em = em;
    }

    private List<CovidStateTestsEntity> getWeeklyTestsOnOrAfterDate(List<String> states, Date startDate, Boolean allStates) {
        TypedQuery<CovidStateTestsEntity> q = em.createNamedQuery(allStates ? "allWeeklyTests" : "stateWeeklyTests", CovidStateTestsEntity.class);
        q.setParameter("startDate", startDate);
        if (!allStates) {
            q.setParameter("states", states);
        }
        return q.getResultList();
    }

    private List<CovidStateTestsEntity> getTestsEntityData(List<String> states, DataOrientation orientation, Date startDate, Boolean allStates) {
        return switch (orientation) {
            case weekly, weeklyPer100K -> getWeeklyTestsOnOrAfterDate(states, startDate, allStates);
            default -> allStates ?
                    covidStateTestsRepository.getAllTestsOnOrAfterDate(CovidServiceUtils.adjustedDate(startDate, orientation)) :
                    covidStateTestsRepository.getStateCasesOnOrAfterDate(states, CovidServiceUtils.adjustedDate(startDate, orientation));
        };
    }

    public List<MetricDto> getData(List<String> states, DataOrientation orientation, Date startDate) {
        boolean fetchForAllStates = states.isEmpty();
        List<CovidStateTestsEntity> tests = getTestsEntityData(states, orientation, startDate, fetchForAllStates);
        switch (orientation) {
            case cumulative:
                Integer startingAggregate = fetchForAllStates ?
                        covidStateTestsRepository.aggregatedTestsUntilDate(startDate) :
                        covidStateTestsRepository.aggregatedStateTestsUntilDate(states, startDate);
                return CovidTestsMetricTransformer.toCumulative(tests, startingAggregate);

            case daily:
                return CovidTestsMetricTransformer.toDaily(tests);

            case dailyPer100K:
                Integer denominator = populationService.aggregatedPopulation(states);
                return CovidTestsMetricTransformer.toDailyPer100K(tests, denominator);

            case daily7DayAvg:
                return CovidTestsMetricTransformer.toDailyNDayAverage(tests, 7);

            case daily14DayAvg:
                return CovidTestsMetricTransformer.toDailyNDayAverage(tests, 14);

            case daily7DayAvgPer100K:
                denominator = populationService.aggregatedPopulation(states);
                return CovidTestsMetricTransformer.toDailyNDayAveragePer100K(tests, 7, denominator);

            case daily14DayAvgPer100K:
                denominator = populationService.aggregatedPopulation(states);
                return CovidTestsMetricTransformer.toDailyNDayAveragePer100K(tests, 14, denominator);

            case weekly:
                return CovidTestsMetricTransformer.toWeekly(tests);

            case weeklyPer100K:
                denominator = populationService.aggregatedPopulation(states);
                return CovidTestsMetricTransformer.toWeeklyPer100K(tests, denominator);

            case percentChangeInDailyOver7:
                return CovidTestsMetricTransformer.toDailyPercentChangeInNDayAverage(tests, 7);

            case percentChangeInDailyOver14:
                return CovidTestsMetricTransformer.toDailyPercentChangeInNDayAverage(tests, 14);
        }

        return new ArrayList<>();
    }
}
