package com.projectrefocus.service.covid.service;

import com.projectrefocus.service.covid.dto.CovidMetricDto;
import com.projectrefocus.service.request.enums.DataOrientation;
import com.projectrefocus.service.request.enums.SubCategory;

import java.util.Date;
import java.util.List;

public interface CovidService {

    List<CovidMetricDto> getCovidCasesData(List<String> states, DataOrientation orientation, Date startDate);
    List<CovidMetricDto> getCovidDeathsData(List<String> states, DataOrientation orientation, Date startDate);
    List<CovidMetricDto> getCovidTestsData(List<String> states, DataOrientation orientation, Date startDate);
    List<CovidMetricDto> getCovidVaccinationsData(List<String> states, SubCategory subCategory, DataOrientation orientation, Date startDate);
}
