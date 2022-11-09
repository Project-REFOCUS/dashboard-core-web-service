package com.projectrefocus.service.covid.service;

import com.projectrefocus.service.covid.dto.CovidMetricDto;
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

    public List<CovidMetricDto> getData(List<String> states, SubCategory subCategory, DataOrientation orientation, Date startDate) {
        boolean fetchForAllStates = states.isEmpty();
        List<CovidStateVaccinationsEntity> vaccinations = fetchForAllStates ?
                covidStateVaccinationsRepository.getAllVaccinationsOnOrAfterDate(CovidServiceUtils.adjustedDate(startDate, orientation)) :
                covidStateVaccinationsRepository.getStateVaccinationsOnOrAfterDate(states, CovidServiceUtils.adjustedDate(startDate, orientation));
        switch (orientation) {
            case cumulative:

                switch (subCategory) {
                    case distributed -> {
                        Integer distributedStartingAggregate = fetchForAllStates ?
                                covidStateVaccinationsRepository.aggregatedDistributedVaccinationsUntilDate(startDate) :
                                covidStateVaccinationsRepository.aggregatedStateDistributedVaccinationsUntilDate(states, startDate);
                        return CovidVaccinationsMetricTransformer.toCumulativeDistributedVaccinations(vaccinations, distributedStartingAggregate);
                    }
                    case administered -> {
                        Integer administeredStartingAggregate = fetchForAllStates ?
                                covidStateVaccinationsRepository.aggregatedAdministeredVaccinationsUntilDate(startDate) :
                                covidStateVaccinationsRepository.aggregatedStateAdministeredVaccinationsUntilDate(states, startDate);
                        return CovidVaccinationsMetricTransformer.toCumulativeAdministeredVaccinations(vaccinations, administeredStartingAggregate);
                    }
                    case administeredOneDose -> {
                        Integer administeredOneDoseStartingAggregate = fetchForAllStates ?
                                covidStateVaccinationsRepository.aggregatedAdministeredOneDoseVaccinationsUntilDate(startDate) :
                                covidStateVaccinationsRepository.aggregatedStateAdministeredOneDoseVaccinationsUntilDate(states, startDate);
                        return CovidVaccinationsMetricTransformer.toCumulativeAdministeredOneDoseVaccinations(vaccinations, administeredOneDoseStartingAggregate);
                    }
                    case administeredTwoDose -> {
                        Integer administeredTwoDoseStartingAggregate = fetchForAllStates ?
                                covidStateVaccinationsRepository.aggregateAdministeredTwoDoseVaccinationsUntilDate(startDate) :
                                covidStateVaccinationsRepository.aggregatedStateAdministeredTwoDoseVaccinationsUntilDate(states, startDate);
                        return CovidVaccinationsMetricTransformer.toCumulativeAdministeredTwoDoseVaccinations(vaccinations, administeredTwoDoseStartingAggregate);
                    }
                }

            case daily:
                return switch (subCategory) {
                    case distributed -> CovidVaccinationsMetricTransformer.toDailyDistributedVaccinations(vaccinations);
                    case administered ->
                            CovidVaccinationsMetricTransformer.toDailyAdministeredVaccinations(vaccinations);
                    case administeredOneDose ->
                            CovidVaccinationsMetricTransformer.toDailyAdministeredOneDoseVaccinations(vaccinations);
                    case administeredTwoDose ->
                            CovidVaccinationsMetricTransformer.toDailyAdministeredTwoDoseVaccinations(vaccinations);
                    default -> new ArrayList<>();
                };

            case dailyPer100K:

                switch (subCategory) {
                    case distributed:
                        Integer denominator = populationService.aggregatedPopulation(states);
                        return CovidVaccinationsMetricTransformer.toDailyDistributedVaccinationsPer100K(vaccinations, denominator);

                    case administered:
                        denominator = populationService.aggregatedPopulation(states);
                        return CovidVaccinationsMetricTransformer.toDailyAdministeredVaccinationsPer100K(vaccinations, denominator);

                    case administeredOneDose:
                        denominator = populationService.aggregatedPopulation(states);
                        return CovidVaccinationsMetricTransformer.toDailyAdministeredOneDoseVaccinationsPer100K(vaccinations, denominator);

                    case administeredTwoDose:
                        denominator = populationService.aggregatedPopulation(states);
                        return CovidVaccinationsMetricTransformer.toDailyAdministeredTwoDoseVaccinationsPer100K(vaccinations, denominator);
                }

            case daily7DayAvg:
                return switch (subCategory) {
                    case distributed ->
                            CovidVaccinationsMetricTransformer.toDailyDistributedVaccinationsNDayAverage(vaccinations, 7);
                    case administered ->
                            CovidVaccinationsMetricTransformer.toDailyAdministeredVaccinationsNDayAverage(vaccinations, 7);
                    case administeredOneDose ->
                            CovidVaccinationsMetricTransformer.toDailyAdministeredOneDoseVaccinationsNDayAverage(vaccinations, 7);
                    case administeredTwoDose ->
                            CovidVaccinationsMetricTransformer.toDailyAdministeredTwoDoseVaccinationsNDayAverage(vaccinations, 7);
                    default -> new ArrayList<>();
                };

            case daily14DayAvg:
                return switch (subCategory) {
                    case distributed ->
                            CovidVaccinationsMetricTransformer.toDailyDistributedVaccinationsNDayAverage(vaccinations, 14);
                    case administered ->
                            CovidVaccinationsMetricTransformer.toDailyAdministeredVaccinationsNDayAverage(vaccinations, 14);
                    case administeredOneDose ->
                            CovidVaccinationsMetricTransformer.toDailyAdministeredOneDoseVaccinationsNDayAverage(vaccinations, 14);
                    case administeredTwoDose ->
                            CovidVaccinationsMetricTransformer.toDailyAdministeredTwoDoseVaccinationsNDayAverage(vaccinations, 14);
                    default -> new ArrayList<>();
                };

            case daily7DayAvgPer100K:
                Integer denominator = populationService.aggregatedPopulation(states);
                return switch (subCategory) {
                    case distributed -> CovidVaccinationsMetricTransformer.toDailyDistributedNDayAveragePer100K(vaccinations, 7, denominator);
                    case administered -> CovidVaccinationsMetricTransformer.toDailyAdministeredNDayAveragePer100K(vaccinations, 7, denominator);
                    case administeredOneDose -> CovidVaccinationsMetricTransformer.toDailyAdministeredOneDoseNDayAveragePer100K(vaccinations, 7, denominator);
                    case administeredTwoDose -> CovidVaccinationsMetricTransformer.toDailyAdministeredTwoDoseNDayAveragePer100K(vaccinations, 7, denominator);
                    default -> new ArrayList<>();
                };

            case daily14DayAvgPer100K:
                denominator = populationService.aggregatedPopulation(states);
                return switch (subCategory) {
                    case distributed -> CovidVaccinationsMetricTransformer.toDailyDistributedNDayAveragePer100K(vaccinations, 14, denominator);
                    case administered -> CovidVaccinationsMetricTransformer.toDailyAdministeredNDayAveragePer100K(vaccinations, 14, denominator);
                    case administeredOneDose -> CovidVaccinationsMetricTransformer.toDailyAdministeredOneDoseNDayAveragePer100K(vaccinations, 14, denominator);
                    case administeredTwoDose -> CovidVaccinationsMetricTransformer.toDailyAdministeredTwoDoseNDayAveragePer100K(vaccinations, 14, denominator);
                    default -> new ArrayList<>();
                };

            case percentChangeInDailyOver7:
                return switch (subCategory) {
                    case distributed -> CovidVaccinationsMetricTransformer.toDailyDistributedVaccinationsPercentChangeNDayAverage(vaccinations, 7);
                    case administered -> CovidVaccinationsMetricTransformer.toDailyAdministeredVaccinationsPercentChangeNDayAverage(vaccinations, 7);
                    case administeredOneDose -> CovidVaccinationsMetricTransformer.toDailyAdministeredOneDoseVaccinationsPercentChangeNDayAverage(vaccinations, 7);
                    case administeredTwoDose -> CovidVaccinationsMetricTransformer.toDailyAdministeredTwoDoseVaccinationsPercentChangeNDayAverage(vaccinations, 7);
                    default -> new ArrayList<>();
                };

            case percentChangeInDailyOver14:
                return switch (subCategory) {
                    case distributed -> CovidVaccinationsMetricTransformer.toDailyDistributedVaccinationsPercentChangeNDayAverage(vaccinations, 14);
                    case administered -> CovidVaccinationsMetricTransformer.toDailyAdministeredVaccinationsPercentChangeNDayAverage(vaccinations, 14);
                    case administeredOneDose -> CovidVaccinationsMetricTransformer.toDailyAdministeredOneDoseVaccinationsPercentChangeNDayAverage(vaccinations, 14);
                    case administeredTwoDose -> CovidVaccinationsMetricTransformer.toDailyAdministeredTwoDoseVaccinationsPercentChangeNDayAverage(vaccinations, 14);
                    default -> new ArrayList<>();
                };
        }

        return new ArrayList<>();
    }
}
