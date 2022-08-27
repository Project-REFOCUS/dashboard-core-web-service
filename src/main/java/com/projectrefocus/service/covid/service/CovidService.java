package com.projectrefocus.service.covid.service;

import com.projectrefocus.service.covid.dto.CovidStateCasesDto;
import com.projectrefocus.service.covid.dto.CovidStateDeathsDto;
import com.projectrefocus.service.covid.dto.CovidStateTestsDto;

import java.util.List;

public interface CovidService {

    List<CovidStateCasesDto> getCasesByState(List<String> states);
    List<CovidStateDeathsDto> getDeathsByState(List<String> states);
    List<CovidStateTestsDto> getTestsByState(List<String> states);
}
