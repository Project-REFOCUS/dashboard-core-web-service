package com.projectrefocus.service.covid.service;

import com.projectrefocus.service.covid.dto.CovidMetricDto;
import com.projectrefocus.service.covid.entity.CovidStateCasesEntity;
import com.projectrefocus.service.covid.entity.CovidStateDeathsEntity;
import com.projectrefocus.service.covid.entity.CovidStateTestsEntity;
import com.projectrefocus.service.covid.entity.CovidStateVaccinationsEntity;
import com.projectrefocus.service.covid.repository.CovidStateCasesRepository;
import com.projectrefocus.service.covid.repository.CovidStateDeathsRepository;
import com.projectrefocus.service.covid.repository.CovidStateTestsRepository;
import com.projectrefocus.service.covid.repository.CovidStateVaccinationsRepository;
import com.projectrefocus.service.covid.utils.CovidVaccinationsMetricTransformer;
import com.projectrefocus.service.covid.utils.Transformer;
import com.projectrefocus.service.population.service.PopulationService;
import com.projectrefocus.service.request.enums.DataOrientation;
import com.projectrefocus.service.request.enums.SubCategory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CovidServiceImpl implements CovidService {

    private final CovidStateCasesRepository covidStateCasesRepository;
    private final CovidStateDeathsRepository covidStateDeathsRepository;
    private final CovidStateTestsRepository covidStateTestsRepository;
    private final CovidStateVaccinationsRepository covidStateVaccinationsRepository;
    private final PopulationService populationService;

    public CovidServiceImpl(
            CovidStateCasesRepository covidStateCasesRepository, CovidStateDeathsRepository covidStateDeathsRepository,
            CovidStateTestsRepository covidStateTestsRepository, CovidStateVaccinationsRepository covidStateVaccinationsRepository,
            PopulationService populationService) {
        this.covidStateCasesRepository = covidStateCasesRepository;
        this.covidStateDeathsRepository = covidStateDeathsRepository;
        this.covidStateTestsRepository = covidStateTestsRepository;
        this.covidStateVaccinationsRepository = covidStateVaccinationsRepository;

        this.populationService = populationService;
    }

    private Date adjustedDate(Date startDate, DataOrientation orientation) {
        switch (orientation) {
            case daily7DayAvg:
                return new Date(startDate.getTime() - (86400000 * 7));

            case daily14DayAvg:
                return new Date(startDate.getTime() - (86400000 * 14));
        }
        return startDate;
    }

    public List<CovidMetricDto> getCovidCasesData(List<String> states, DataOrientation orientation, Date startDate) {
        boolean fetchForAllStates = states.isEmpty();
        List<CovidStateCasesEntity> cases = fetchForAllStates ?
                covidStateCasesRepository.getAllCasesOnOrAfterDate(adjustedDate(startDate, orientation)) :
                covidStateCasesRepository.getStateCasesOnOrAfterDate(states, adjustedDate(startDate, orientation));
        switch (orientation) {
            case cumulative:
                Integer startingAggregate = fetchForAllStates ?
                        covidStateCasesRepository.aggregatedCasesUntilDate(startDate) :
                        covidStateCasesRepository.aggregatedStateCasesUntilDate(states, startDate);
                return Transformer.toCumulativeCases(cases, startingAggregate);

            case daily:
                return Transformer.toDailyCases(cases);

            case dailyPer100K:
                Integer denominator = populationService.aggregatedPopulation(states);
                return Transformer.toDailyCasesPer100K(cases, denominator);

            case daily7DayAvg:
                return Transformer.toDailyCasesNDayAverage(cases, 7);

            case daily14DayAvg:
                return Transformer.toDailyCasesNDayAverage(cases, 14);
        }

        return new ArrayList<>();
    }

    public List<CovidMetricDto> getCovidDeathsData(List<String> states, DataOrientation orientation, Date startDate) {
        boolean fetchForAllStates = states.isEmpty();
        List<CovidStateDeathsEntity> deaths = fetchForAllStates ?
                covidStateDeathsRepository.getAllDeathsOnOrAfterDate(adjustedDate(startDate, orientation)) :
                covidStateDeathsRepository.getStateDeathsOnOrAfterDate(states, adjustedDate(startDate, orientation));
        switch (orientation) {
            case cumulative:
                Integer startingAggregate = fetchForAllStates ?
                        covidStateDeathsRepository.aggregatedDeathsUntilDate(startDate) :
                        covidStateDeathsRepository.aggregatedStateDeathsUntilDate(states, startDate);
                return Transformer.toCumulativeDeaths(deaths, startingAggregate);

            case daily:
                return Transformer.toDailyDeaths(deaths);

            case dailyPer100K:
                Integer denominator = populationService.aggregatedPopulation(states);
                return Transformer.toDailyDeathsPer100K(deaths, denominator);

            case daily7DayAvg:
                return Transformer.toDailyDeathsNDayAverage(deaths, 7);

            case daily14DayAvg:
                return Transformer.toDailyDeathsNDayAverage(deaths, 14);
        }

        return new ArrayList<>();
    }

    public List<CovidMetricDto> getCovidTestsData(List<String> states, DataOrientation orientation, Date startDate) {
        boolean fetchForAllStates = states.isEmpty();
        List<CovidStateTestsEntity> tests = states.isEmpty() ?
                covidStateTestsRepository.getAllTestsOnOrAfterDate(adjustedDate(startDate, orientation)) :
                covidStateTestsRepository.getStateCasesOnOrAfterDate(states, adjustedDate(startDate, orientation));
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

    public List<CovidMetricDto> getCovidVaccinationsData(List<String> states, SubCategory subCategory, DataOrientation orientation, Date startDate) {
        boolean fetchForAllStates = states.isEmpty();
        List<CovidStateVaccinationsEntity> vaccinations = fetchForAllStates ?
                covidStateVaccinationsRepository.getAllVaccinationsOnOrAfterDate(adjustedDate(startDate, orientation)) :
                covidStateVaccinationsRepository.getStateVaccinationsOnOrAfterDate(states, startDate);
        switch (orientation) {
            case cumulative:

                switch (subCategory) {
                    case distributed:
                        Integer distributedStartingAggregate = fetchForAllStates ?
                                covidStateVaccinationsRepository.aggregatedDistributedVaccinationsUntilDate(startDate) :
                                covidStateVaccinationsRepository.aggregatedStateDistributedVaccinationsUntilDate(states, startDate);
                        return CovidVaccinationsMetricTransformer.toCumulativeDistributedVaccinations(vaccinations, distributedStartingAggregate);

                    case administered:
                        Integer administeredStartingAggregate = fetchForAllStates ?
                                covidStateVaccinationsRepository.aggregatedAdministeredVaccinationsUntilDate(startDate) :
                                covidStateVaccinationsRepository.aggregatedStateAdministeredVaccinationsUntilDate(states, startDate);
                        return CovidVaccinationsMetricTransformer.toCumulativeAdministeredVaccinations(vaccinations, administeredStartingAggregate);

                    case administeredOneDose:
                        Integer administeredOneDoseStartingAggregate = fetchForAllStates ?
                                covidStateVaccinationsRepository.aggregatedAdministeredOneDoseVaccinationsUntilDate(startDate) :
                                covidStateVaccinationsRepository.aggregatedStateAdministeredOneDoseVaccinationsUntilDate(states, startDate);
                        return CovidVaccinationsMetricTransformer.toCumulativeAdministeredOneDoseVaccinations(vaccinations, administeredOneDoseStartingAggregate);

                    case administeredTwoDose:
                        Integer administeredTwoDoseStartingAggregate = fetchForAllStates ?
                                covidStateVaccinationsRepository.aggregateAdministeredTwoDoseVaccinationsUntilDate(startDate) :
                                covidStateVaccinationsRepository.aggregatedStateAdministeredTwoDoseVaccinationsUntilDate(states, startDate);
                        return CovidVaccinationsMetricTransformer.toCumulativeAdministeredTwoDoseVaccinations(vaccinations, administeredTwoDoseStartingAggregate);
                }

            case daily:

                switch (subCategory) {
                    case distributed:
                        return CovidVaccinationsMetricTransformer.toDailyDistributedVaccinations(vaccinations);

                    case administered:
                        return CovidVaccinationsMetricTransformer.toDailyAdministeredVaccinations(vaccinations);

                    case administeredOneDose:
                        return CovidVaccinationsMetricTransformer.toDailyAdministeredOneDoseVaccinations(vaccinations);

                    case administeredTwoDose:
                        return CovidVaccinationsMetricTransformer.toDailyAdministeredTwoDoseVaccinations(vaccinations);
                }

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

                switch (subCategory) {
                    case distributed:
                        return CovidVaccinationsMetricTransformer.toDailyDistributedVaccinationsNDayAverage(vaccinations, 7);

                    case administered:
                        return CovidVaccinationsMetricTransformer.toDailyAdministeredVaccinationsNDayAverage(vaccinations, 7);

                    case administeredOneDose:
                        return CovidVaccinationsMetricTransformer.toDailyAdministeredOneDoseVaccinationsNDayAverage(vaccinations, 7);

                    case administeredTwoDose:
                        return CovidVaccinationsMetricTransformer.toDailyAdministeredTwoDoseVaccinationsNDayAverage(vaccinations, 7);
                }

            case daily14DayAvg:

                switch (subCategory) {
                    case distributed:
                        return CovidVaccinationsMetricTransformer.toDailyDistributedVaccinationsNDayAverage(vaccinations, 14);

                    case administered:
                        return CovidVaccinationsMetricTransformer.toDailyAdministeredVaccinationsNDayAverage(vaccinations, 14);

                    case administeredOneDose:
                        return CovidVaccinationsMetricTransformer.toDailyAdministeredOneDoseVaccinationsNDayAverage(vaccinations, 14);

                    case administeredTwoDose:
                        return CovidVaccinationsMetricTransformer.toDailyAdministeredTwoDoseVaccinationsNDayAverage(vaccinations, 14);
                }
        }

        return new ArrayList<>();
    }
}
