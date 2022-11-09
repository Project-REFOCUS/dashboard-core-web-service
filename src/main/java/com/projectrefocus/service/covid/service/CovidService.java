package com.projectrefocus.service.covid.service;

import com.projectrefocus.service.common.dto.MetricDto;
import com.projectrefocus.service.covid.dto.CovidMetricDto;
import com.projectrefocus.service.request.enums.DataOrientation;
import com.projectrefocus.service.request.enums.SubCategory;

import java.util.Date;
import java.util.List;

public interface CovidService {

    List<MetricDto> getCovidCasesData(List<String> states, DataOrientation orientation, Date startDate);
    List<MetricDto> getCovidDeathsData(List<String> states, DataOrientation orientation, Date startDate);
    List<MetricDto> getCovidTestsData(List<String> states, DataOrientation orientation, Date startDate);
    List<CovidMetricDto> getCovidVaccinationsData(List<String> states, SubCategory subCategory, DataOrientation orientation, Date startDate);
    List<MetricDto> getCovidBehindBarsData(List<String> states, SubCategory primaryCategory, SubCategory secondaryCategory, DataOrientation orientation, Date startDate);
}
