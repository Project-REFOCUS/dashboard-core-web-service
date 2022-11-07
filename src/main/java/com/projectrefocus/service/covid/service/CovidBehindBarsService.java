package com.projectrefocus.service.covid.service;

import com.projectrefocus.service.common.dto.MetricDto;
import com.projectrefocus.service.covid.entity.CovidBehindBarsEntity;
import com.projectrefocus.service.covid.enums.PrimaryCategory;
import com.projectrefocus.service.covid.enums.SecondaryCategory;
import com.projectrefocus.service.covid.repository.CovidBehindBarsRepository;
import com.projectrefocus.service.covid.utils.CovidBehindBarsMetricTransformer;
import com.projectrefocus.service.request.enums.DataOrientation;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class CovidBehindBarsService {

    private final CovidBehindBarsRepository covidBehindBarsRepository;

    public CovidBehindBarsService(CovidBehindBarsRepository covidBehindBarsRepository) {
        this.covidBehindBarsRepository = covidBehindBarsRepository;
    }

    public List<MetricDto> getData(List<String> states, PrimaryCategory primaryCategory, SecondaryCategory secondaryCategory, DataOrientation orientation, Date startDate) {
        boolean fetchForAllStates = states.isEmpty();
        List<CovidBehindBarsEntity> metrics = fetchForAllStates ?
                covidBehindBarsRepository.getAllCovidBehindBarsMetricsOnOrAfterDate(startDate) :
                covidBehindBarsRepository.getAllStateCovidBehindBarsMetricsOnOrAfterDate(states, startDate);
        switch (orientation) {
            case cumulative:

                switch (primaryCategory) {
                    case residents:

                        switch (secondaryCategory) {
                            case cases -> {
                                Integer residentCasesStartingAggregate = fetchForAllStates ?
                                        covidBehindBarsRepository.aggregatedResidentCasesUntilDate(startDate) :
                                        covidBehindBarsRepository.aggregatedStateResidentCasesUntilDate(states, startDate);
                                return CovidBehindBarsMetricTransformer.toCumulativeResidentCases(metrics, residentCasesStartingAggregate);
                            }
                            case deaths -> {
                                Integer residentDeathsStartingAggregate = fetchForAllStates ?
                                        covidBehindBarsRepository.aggregatedResidentDeathsUntilDate(startDate) :
                                        covidBehindBarsRepository.aggregatedStateResidentDeathsUntilDate(states, startDate);
                                return CovidBehindBarsMetricTransformer.toCumulativeResidentDeaths(metrics, residentDeathsStartingAggregate);
                            }
                            case tests -> {
                                Integer residentTestsStartingAggregate = fetchForAllStates ?
                                        covidBehindBarsRepository.aggregatedResidentTestsUntilDate(startDate) :
                                        covidBehindBarsRepository.aggregatedStateResidentTestsUntilDate(states, startDate);
                                return CovidBehindBarsMetricTransformer.toCumulativeResidentTests(metrics, residentTestsStartingAggregate);
                            }
                            case administeredOneDose -> {
                                Integer residentAdministeredOneDoseStartingAggregate = fetchForAllStates ?
                                        covidBehindBarsRepository.aggregatedResidentAdministeredOneDoseUntilDate(startDate) :
                                        covidBehindBarsRepository.aggregatedStateResidentAdministeredOneDoseUntilDate(states, startDate);
                                return CovidBehindBarsMetricTransformer.toCumulativeResidentAdministeredOneDose(metrics, residentAdministeredOneDoseStartingAggregate);
                            }
                            case administeredTwoDose -> {
                                Integer residentAdministeredTwoDoseStartingAggregate = fetchForAllStates ?
                                        covidBehindBarsRepository.aggregatedResidentAdministeredTwoDoseUntilDate(startDate) :
                                        covidBehindBarsRepository.aggregatedStateResidentAdministeredTwoDoseUntilDate(states, startDate);
                                return CovidBehindBarsMetricTransformer.toCumulativeResidentAdministeredTwoDose(metrics, residentAdministeredTwoDoseStartingAggregate);
                            }
                    }
                    case staff:

                        switch (secondaryCategory) {
                            case cases -> {
                                Integer staffCasesStartingAggregate = fetchForAllStates ?
                                        covidBehindBarsRepository.aggregatedStaffCasesUntilDate(startDate) :
                                        covidBehindBarsRepository.aggregatedStateStaffCasesUntilDate(states, startDate);
                                return CovidBehindBarsMetricTransformer.toCumulativeStaffCases(metrics, staffCasesStartingAggregate);
                            }
                            case deaths -> {
                                Integer staffDeathsStartingAggregate = fetchForAllStates ?
                                        covidBehindBarsRepository.aggregatedStaffDeathsUntilDate(startDate) :
                                        covidBehindBarsRepository.aggregatedStateStaffDeathsUntilDate(states, startDate);
                                return CovidBehindBarsMetricTransformer.toCumulativeStaffDeaths(metrics, staffDeathsStartingAggregate);
                            }
                            case administeredOneDose -> {
                                Integer staffAdministeredOneDoseStartingAggregate = fetchForAllStates ?
                                        covidBehindBarsRepository.aggregatedStaffAdministeredOneDoseUntilDate(startDate) :
                                        covidBehindBarsRepository.aggregatedStateStaffAdministeredOneDoseUntilDate(states, startDate);
                                return CovidBehindBarsMetricTransformer.toCumulativeStaffAdministeredOneDose(metrics, staffAdministeredOneDoseStartingAggregate);
                            }
                            case administeredTwoDose -> {
                                Integer staffAdministeredTwoDoseStartingAggregate = fetchForAllStates ?
                                        covidBehindBarsRepository.aggregatedStaffAdministeredTwoDoseUntilDate(startDate) :
                                        covidBehindBarsRepository.aggregatedStateStaffAdministeredTwoDoseUntilDate(states, startDate);
                                return CovidBehindBarsMetricTransformer.toCumulativeStaffAdministeredTwoDose(metrics, staffAdministeredTwoDoseStartingAggregate);
                            }
                        }
                }

            case daily:

                return switch (primaryCategory) {
                    case residents -> switch (secondaryCategory) {
                        case cases -> CovidBehindBarsMetricTransformer.toDailyResidentCases(metrics);
                        case deaths -> CovidBehindBarsMetricTransformer.toDailyResidentDeaths(metrics);
                        case tests -> CovidBehindBarsMetricTransformer.toDailyResidentTests(metrics);
                        case administeredOneDose ->
                                CovidBehindBarsMetricTransformer.toDailyResidentAdministeredOneDose(metrics);
                        case administeredTwoDose ->
                                CovidBehindBarsMetricTransformer.toDailyResidentAdministeredTwoDose(metrics);
                    };
                    case staff -> switch (secondaryCategory) {
                        case cases -> CovidBehindBarsMetricTransformer.toDailyStaffCases(metrics);
                        case deaths -> CovidBehindBarsMetricTransformer.toDailyStaffDeaths(metrics);
                        case tests -> new ArrayList<>();
                        case administeredOneDose ->
                                CovidBehindBarsMetricTransformer.toDailyStaffAdministeredOneDose(metrics);
                        case administeredTwoDose ->
                                CovidBehindBarsMetricTransformer.toDailyStaffAdministeredTwoDose(metrics);
                    };
                };

            case daily7DayAvg:

                return switch (primaryCategory) {
                    case residents -> switch (secondaryCategory) {
                        case cases -> CovidBehindBarsMetricTransformer.toDailyResidentCasesNDayAverage(metrics, 7);
                        case deaths -> CovidBehindBarsMetricTransformer.toDailyResidentDeathsNDayAverage(metrics, 7);
                        case tests -> CovidBehindBarsMetricTransformer.toDailyResidentTestsNDayAverage(metrics, 7);
                        case administeredOneDose -> CovidBehindBarsMetricTransformer.toDailyResidentAdministeredOneDoseNDayAverage(metrics, 7);
                        case administeredTwoDose -> CovidBehindBarsMetricTransformer.toDailyResidentAdministeredTwoDoseNDayAverage(metrics, 7);
                    };
                    case staff -> switch (secondaryCategory) {
                        case cases -> CovidBehindBarsMetricTransformer.toDailyStaffCasesNDayAverage(metrics, 7);
                        case deaths -> CovidBehindBarsMetricTransformer.toDailyStaffDeathsNDayAverage(metrics, 7);
                        case tests -> new ArrayList<>();
                        case administeredOneDose -> CovidBehindBarsMetricTransformer.toDailyStaffAdministeredOneDoseNDayAverage(metrics, 7);
                        case administeredTwoDose -> CovidBehindBarsMetricTransformer.toDailyStaffAdministeredTwoDoseNDayAverage(metrics, 7);
                    };
                };

            case daily14DayAvg:

                return switch (primaryCategory) {
                    case residents -> switch (secondaryCategory) {
                        case cases -> CovidBehindBarsMetricTransformer.toDailyResidentCasesNDayAverage(metrics, 14);
                        case deaths -> CovidBehindBarsMetricTransformer.toDailyResidentDeathsNDayAverage(metrics, 14);
                        case tests -> CovidBehindBarsMetricTransformer.toDailyResidentTestsNDayAverage(metrics, 14);
                        case administeredOneDose -> CovidBehindBarsMetricTransformer.toDailyResidentAdministeredOneDoseNDayAverage(metrics, 14);
                        case administeredTwoDose -> CovidBehindBarsMetricTransformer.toDailyResidentAdministeredTwoDoseNDayAverage(metrics, 14);
                    };
                    case staff -> switch (secondaryCategory) {
                        case cases -> CovidBehindBarsMetricTransformer.toDailyStaffCasesNDayAverage(metrics, 14);
                        case deaths -> CovidBehindBarsMetricTransformer.toDailyStaffDeathsNDayAverage(metrics, 14);
                        case tests -> new ArrayList<>();
                        case administeredOneDose -> CovidBehindBarsMetricTransformer.toDailyStaffAdministeredOneDoseNDayAverage(metrics, 14);
                        case administeredTwoDose -> CovidBehindBarsMetricTransformer.toDailyStaffAdministeredTwoDoseNDayAverage(metrics, 14);
                    };
                };
        }

        return new ArrayList<>();
    }
}