package com.projectrefocus.service.covid.service;

import com.projectrefocus.service.common.dto.MetricDto;
import com.projectrefocus.service.covid.entity.CovidBehindBarsEntity;
import com.projectrefocus.service.covid.repository.CovidBehindBarsRepository;
import com.projectrefocus.service.covid.utils.CovidBehindBarsMetricTransformer;
import com.projectrefocus.service.request.enums.DataOrientation;
import com.projectrefocus.service.request.enums.SubCategory;
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

    private Integer getInitialAggregate(List<String> states, Date targetDate, SubCategory ...categories) {
        boolean allStates = states.isEmpty();
        switch (categories[0]) {
            case residents -> {
                return switch (categories[1]) {
                    case cases -> allStates ?
                            covidBehindBarsRepository.aggregatedResidentCasesUntilDate(targetDate) :
                            covidBehindBarsRepository.aggregatedStateResidentCasesUntilDate(states, targetDate);
                    case deaths -> allStates ?
                            covidBehindBarsRepository.aggregatedResidentDeathsUntilDate(targetDate) :
                            covidBehindBarsRepository.aggregatedStateResidentDeathsUntilDate(states, targetDate);
                    case tests -> allStates ?
                            covidBehindBarsRepository.aggregatedResidentTestsUntilDate(targetDate) :
                            covidBehindBarsRepository.aggregatedStateResidentTestsUntilDate(states, targetDate);
                    case administeredOneDose -> allStates ?
                            covidBehindBarsRepository.aggregatedResidentAdministeredOneDoseUntilDate(targetDate) :
                            covidBehindBarsRepository.aggregatedStateResidentAdministeredTwoDoseUntilDate(states, targetDate);
                    case administeredTwoDose -> allStates ?
                            covidBehindBarsRepository.aggregatedResidentAdministeredTwoDoseUntilDate(targetDate) :
                            covidBehindBarsRepository.aggregatedStateResidentAdministeredTwoDoseUntilDate(states, targetDate);
                    default -> null;
                };
            }
            case staff -> {
                return switch (categories[1]) {
                    case cases -> allStates ?
                            covidBehindBarsRepository.aggregatedStaffCasesUntilDate(targetDate) :
                            covidBehindBarsRepository.aggregatedStateStaffCasesUntilDate(states, targetDate);
                    case deaths -> allStates ?
                            covidBehindBarsRepository.aggregatedStaffDeathsUntilDate(targetDate) :
                            covidBehindBarsRepository.aggregatedStateStaffDeathsUntilDate(states, targetDate);
                    case administeredOneDose -> allStates ?
                            covidBehindBarsRepository.aggregatedStaffAdministeredOneDoseUntilDate(targetDate) :
                            covidBehindBarsRepository.aggregatedStateStaffAdministeredOneDoseUntilDate(states, targetDate);
                    case administeredTwoDose -> allStates ?
                            covidBehindBarsRepository.aggregatedStaffAdministeredTwoDoseUntilDate(targetDate) :
                            covidBehindBarsRepository.aggregatedStateStaffAdministeredTwoDoseUntilDate(states, targetDate);
                    default -> null;
                };
            }
        }
        return null;
    }

    public List<MetricDto> getData(List<String> states, SubCategory primaryCategory, SubCategory secondaryCategory, DataOrientation orientation, Date startDate) {
        boolean fetchForAllStates = states.isEmpty();
        List<CovidBehindBarsEntity> metrics = fetchForAllStates ?
                covidBehindBarsRepository.getAllCovidBehindBarsMetricsOnOrAfterDate(startDate) :
                covidBehindBarsRepository.getAllStateCovidBehindBarsMetricsOnOrAfterDate(states, startDate);
        switch (orientation) {
            case cumulative:
                Integer startingAggregate = getInitialAggregate(states, startDate, primaryCategory, secondaryCategory);
                return CovidBehindBarsMetricTransformer.toCumulative(metrics, startingAggregate, primaryCategory, secondaryCategory);

            case daily:
                return CovidBehindBarsMetricTransformer.toDaily(metrics, primaryCategory, secondaryCategory);

            case daily7DayAvg:
                return CovidBehindBarsMetricTransformer.toDailyNDayAverage(metrics, 7, primaryCategory, secondaryCategory);

            case daily14DayAvg:
                return CovidBehindBarsMetricTransformer.toDailyNDayAverage(metrics, 14, primaryCategory, secondaryCategory);
        }

        return new ArrayList<>();
    }
}
