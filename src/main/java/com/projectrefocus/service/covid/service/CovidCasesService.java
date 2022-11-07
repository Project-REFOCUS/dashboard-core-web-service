package com.projectrefocus.service.covid.service;

import com.projectrefocus.service.common.dto.MetricDto;
import com.projectrefocus.service.covid.entity.CovidStateCasesEntity;
import com.projectrefocus.service.covid.repository.CovidStateCasesRepository;
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
public class CovidCasesService {

    private final CovidStateCasesRepository covidStateCasesRepository;
    private final PopulationService populationService;
    private final EntityManager em;

    public CovidCasesService(CovidStateCasesRepository covidStateCasesRepository, PopulationService populationService, EntityManager em) {
        this.covidStateCasesRepository = covidStateCasesRepository;
        this.populationService = populationService;
        this.em = em;
    }

    private List<CovidStateCasesEntity> getWeeklyCasesOnOrAfterDate(List<String> states, Date startDate, Boolean allStates) {
        TypedQuery<CovidStateCasesEntity> q = em.createNamedQuery(allStates ? "allWeeklyCases" : "stateWeeklyCases", CovidStateCasesEntity.class);
        q.setParameter("startDate", startDate);
        if (!allStates) {
            q.setParameter("states", states);
        }
        return q.getResultList();
    }

    private List<CovidStateCasesEntity> getMonthlyCasesOnOrAfterDate(List<String> states, Date startDate, Boolean allStates) {
        TypedQuery<CovidStateCasesEntity> q = em.createNamedQuery(allStates ? "allMonthlyCases" : "stateMonthlyCases", CovidStateCasesEntity.class);
        q.setParameter("startDate", startDate);
        if (!allStates) {
            q.setParameter("states", states);
        }
        return q.getResultList();
    }

    private List<CovidStateCasesEntity> getCasesEntityData(List<String> states, DataOrientation orientation, Date startDate, Boolean allStates) {
        return switch (orientation) {
            case weekly, weeklyPer100K -> getWeeklyCasesOnOrAfterDate(states, startDate, allStates);
            case monthly, monthlyPer100K -> getMonthlyCasesOnOrAfterDate(states, startDate, allStates);
            default -> allStates ?
                    covidStateCasesRepository.getAllCasesOnOrAfterDate(CovidServiceUtils.adjustedDate(startDate, orientation)) :
                    covidStateCasesRepository.getStateCasesOnOrAfterDate(states, CovidServiceUtils.adjustedDate(startDate, orientation));
        };
    }

    public List<MetricDto> getData(List<String> states, DataOrientation orientation, Date startDate) {
        boolean fetchForAllStates = states.isEmpty();
        List<CovidStateCasesEntity> cases = getCasesEntityData(states, orientation, startDate, fetchForAllStates);
        switch (orientation) {
            case cumulative:
                Integer startingAggregate = fetchForAllStates ?
                        covidStateCasesRepository.aggregatedCasesUntilDate(startDate) :
                        covidStateCasesRepository.aggregatedStateCasesUntilDate(states, startDate);
                return CovidMetricTransformer.toCumulative(cases, startingAggregate);

            case daily:
                return CovidMetricTransformer.toDaily(cases);

            case dailyPer100K:
                Integer denominator = populationService.aggregatedPopulation(states);
                return CovidMetricTransformer.toDailyPer100K(cases, denominator);

            case daily7DayAvg:
                return CovidMetricTransformer.toDailyNDayAverage(cases, 7);

            case daily14DayAvg:
                return CovidMetricTransformer.toDailyNDayAverage(cases, 14);

            case daily7DayAvgPer100K:
                denominator = populationService.aggregatedPopulation(states);
                return CovidMetricTransformer.toDailyNDayAveragePer100K(cases, 7, denominator);

            case daily14DayAvgPer100K:
                denominator = populationService.aggregatedPopulation(states);
                return CovidMetricTransformer.toDailyNDayAveragePer100K(cases, 14, denominator);

            case weekly:
                return CovidMetricTransformer.toWeekly(cases);

            case weeklyPer100K:
                denominator = populationService.aggregatedPopulation(states);
                return CovidMetricTransformer.toWeeklyPer100K(cases, denominator);

            case monthly:
                return CovidMetricTransformer.toMonthly(cases);

            case monthlyPer100K:
                denominator = populationService.aggregatedPopulation(states);
                return CovidMetricTransformer.toMonthlyPer100K(cases, denominator);

            case percentChangeInDailyOver7:
                return CovidMetricTransformer.toDailyPercentChangeInNDayAverage(cases, 7);

            case percentChangeInDailyOver14:
                return CovidMetricTransformer.toDailyPercentChangeInNDayAverage(cases, 14);
        }

        return new ArrayList<>();
    }
}
