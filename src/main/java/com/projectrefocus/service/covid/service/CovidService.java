package com.projectrefocus.service.covid.service;

import com.projectrefocus.service.covid.dto.CovidStateDeathsDto;
import com.projectrefocus.service.covid.dto.CovidStateMetricDto;
import com.projectrefocus.service.covid.dto.CovidStateTestsDto;

import java.util.Date;
import java.util.List;

public interface CovidService {

    List<CovidStateMetricDto> getCovidCasesData(List<String> states, Date startDate);
    List<CovidStateMetricDto> getCovidDeathsData(List<String> states, Date startDate);
    List<CovidStateMetricDto> getCovidTestsData(List<String> states, Date startDate);
}
