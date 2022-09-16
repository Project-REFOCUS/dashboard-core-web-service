package com.projectrefocus.service.covid.service;

import com.projectrefocus.service.covid.dto.CovidMetricDto;
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

    public CovidServiceImpl(
            CovidCasesService covidCasesService, CovidDeathsService covidDeathsService,
            CovidTestsService covidTestsService, CovidVaccinationsService covidVaccinationsService) {
        this.covidCasesService = covidCasesService;
        this.covidDeathsService = covidDeathsService;
        this.covidTestsService = covidTestsService;
        this.covidVaccinationsService = covidVaccinationsService;
    }

    public List<CovidMetricDto> getCovidCasesData(List<String> states, DataOrientation orientation, Date startDate) {
        return covidCasesService.getData(states, orientation, startDate);
    }

    public List<CovidMetricDto> getCovidDeathsData(List<String> states, DataOrientation orientation, Date startDate) {
        return covidDeathsService.getData(states, orientation, startDate);
    }

    public List<CovidMetricDto> getCovidTestsData(List<String> states, DataOrientation orientation, Date startDate) {
        return covidTestsService.getData(states, orientation, startDate);
    }

    public List<CovidMetricDto> getCovidVaccinationsData(List<String> states, SubCategory subCategory, DataOrientation orientation, Date startDate) {
        return covidVaccinationsService.getData(states, subCategory, orientation, startDate);
    }
}
