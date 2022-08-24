package com.projectrefocus.service.covid.service;

import com.projectrefocus.service.covid.dto.CovidStateCasesDto;
import com.projectrefocus.service.covid.dto.CovidStateDeathsDto;
import com.projectrefocus.service.covid.dto.CovidStateTestsDto;

import java.util.List;

public interface CovidService {

    List<CovidStateCasesDto> getCasesByState(Byte stateId);
    List<CovidStateDeathsDto> getDeathsByState(Byte stateId);
    List<CovidStateTestsDto> getTestsByState(Byte stateId);
}
