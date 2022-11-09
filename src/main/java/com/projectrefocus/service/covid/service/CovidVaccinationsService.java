package com.projectrefocus.service.covid.service;

import com.projectrefocus.service.common.dto.MetricDto;
import com.projectrefocus.service.covid.entity.CovidStateVaccinationsEntity;
import com.projectrefocus.service.covid.repository.CovidStateVaccinationsRepository;
import com.projectrefocus.service.covid.utils.CovidServiceUtils;
import com.projectrefocus.service.covid.utils.CovidVaccinationsMetricTransformer;
import com.projectrefocus.service.population.service.PopulationService;
import com.projectrefocus.service.request.enums.DataOrientation;
import com.projectrefocus.service.request.enums.SubCategory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class CovidVaccinationsService {

    private final CovidStateVaccinationsRepository covidStateVaccinationsRepository;
    private final PopulationService populationService;

    public CovidVaccinationsService(CovidStateVaccinationsRepository covidStateVaccinationsRepository, PopulationService populationService) {
        this.covidStateVaccinationsRepository = covidStateVaccinationsRepository;
        this.populationService = populationService;
    }

    private Integer getInitialAggregate(List<String> states, Date targetDate, SubCategory ...categories) {
        boolean allStates = states.isEmpty();
        return switch (categories[0]) {
            case distributed -> allStates ?
                    covidStateVaccinationsRepository.aggregatedDistributedVaccinationsUntilDate(targetDate) :
                    covidStateVaccinationsRepository.aggregatedStateDistributedVaccinationsUntilDate(states, targetDate);
            case administered -> allStates ?
                    covidStateVaccinationsRepository.aggregatedAdministeredVaccinationsUntilDate(targetDate) :
                    covidStateVaccinationsRepository.aggregatedStateAdministeredVaccinationsUntilDate(states, targetDate);
            case administeredOneDose -> allStates ?
                    covidStateVaccinationsRepository.aggregatedAdministeredOneDoseVaccinationsUntilDate(targetDate) :
                    covidStateVaccinationsRepository.aggregatedStateAdministeredOneDoseVaccinationsUntilDate(states, targetDate);
            case administeredTwoDose -> allStates ?
                    covidStateVaccinationsRepository.aggregatedAdministeredTwoDoseVaccinationsUntilDate(targetDate) :
                    covidStateVaccinationsRepository.aggregatedStateAdministeredTwoDoseVaccinationsUntilDate(states, targetDate);
            default -> null;
        };
    }

    public List<MetricDto> getData(List<String> states, SubCategory subCategory, DataOrientation orientation, Date startDate) {
        boolean fetchForAllStates = states.isEmpty();
        List<CovidStateVaccinationsEntity> vaccinations = fetchForAllStates ?
                covidStateVaccinationsRepository.getAllVaccinationsOnOrAfterDate(CovidServiceUtils.adjustedDate(startDate, orientation)) :
                covidStateVaccinationsRepository.getStateVaccinationsOnOrAfterDate(states, CovidServiceUtils.adjustedDate(startDate, orientation));
        switch (orientation) {
            case cumulative:
                Integer startingAggregate = getInitialAggregate(states, startDate, subCategory);
                return CovidVaccinationsMetricTransformer.toCumulative(vaccinations, startingAggregate, subCategory);

            case daily:
                return CovidVaccinationsMetricTransformer.toDaily(vaccinations, subCategory);

            case dailyPer100K:
                Integer denominator = populationService.aggregatedPopulation(states);
                return CovidVaccinationsMetricTransformer.toDailyPer100K(vaccinations, denominator);

            case daily7DayAvg:
                return CovidVaccinationsMetricTransformer.toDailyNDayAverage(vaccinations, 7, subCategory);

            case daily14DayAvg:
                return CovidVaccinationsMetricTransformer.toDailyNDayAverage(vaccinations, 14, subCategory);

            case daily7DayAvgPer100K:
                denominator = populationService.aggregatedPopulation(states);
                return CovidVaccinationsMetricTransformer.toDailyNDayAveragePer100K(vaccinations, 7, denominator);

            case daily14DayAvgPer100K:
                denominator = populationService.aggregatedPopulation(states);
                return CovidVaccinationsMetricTransformer.toDailyNDayAveragePer100K(vaccinations, 14, denominator);

            case percentChangeInDailyOver7:
                return CovidVaccinationsMetricTransformer.toDailyPercentageChangeNDayAverage(vaccinations, 7, subCategory);

            case percentChangeInDailyOver14:
                return CovidVaccinationsMetricTransformer.toDailyPercentageChangeNDayAverage(vaccinations, 14, subCategory);
        }

        return new ArrayList<>();
    }
}
