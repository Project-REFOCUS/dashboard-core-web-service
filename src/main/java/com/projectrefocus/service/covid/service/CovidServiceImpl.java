package com.projectrefocus.service.covid.service;

import com.projectrefocus.service.common.dto.MetricDto;
import com.projectrefocus.service.covid.dto.CovidMetricDto;
import com.projectrefocus.service.covid.enums.PrimaryCategory;
import com.projectrefocus.service.covid.enums.SecondaryCategory;
import com.projectrefocus.service.request.enums.DataOrientation;
import com.projectrefocus.service.request.enums.SubCategory;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CovidServiceImpl implements CovidService {

    private final CovidCasesService covidCasesService;
    private final CovidDeathsService covidDeathsService;
    private final CovidTestsService covidTestsService;
    private final CovidVaccinationsService covidVaccinationsService;

    private final CovidBehindBarsService covidBehindBarsService;

    public CovidServiceImpl(
            CovidCasesService covidCasesService, CovidDeathsService covidDeathsService,
            CovidTestsService covidTestsService, CovidVaccinationsService covidVaccinationsService,
            CovidBehindBarsService covidBehindBarsService) {
        this.covidCasesService = covidCasesService;
        this.covidDeathsService = covidDeathsService;
        this.covidTestsService = covidTestsService;
        this.covidVaccinationsService = covidVaccinationsService;
        this.covidBehindBarsService = covidBehindBarsService;
    }

    public List<MetricDto> getCovidCasesData(List<String> states, DataOrientation orientation, Date startDate) {
        return covidCasesService.getData(states, orientation, startDate);
    }

    public List<MetricDto> getCovidDeathsData(List<String> states, DataOrientation orientation, Date startDate) {
        return covidDeathsService.getData(states, orientation, startDate);
    }

    public List<MetricDto> getCovidTestsData(List<String> states, DataOrientation orientation, Date startDate) {
        return covidTestsService.getData(states, orientation, startDate);
    }

    public List<CovidMetricDto> getCovidVaccinationsData(List<String> states, SubCategory subCategory, DataOrientation orientation, Date startDate) {
        return covidVaccinationsService.getData(states, subCategory, orientation, startDate);
    }

    public List<MetricDto> getCovidBehindBarsData(List<String> states, PrimaryCategory primaryCategory, SecondaryCategory secondaryCategory, DataOrientation orientation, Date startDate) {
        return covidBehindBarsService.getData(states, primaryCategory, secondaryCategory, orientation, startDate);
    }
}
