package com.projectrefocus.service.geography.service;

import com.projectrefocus.service.geography.dto.CityDto;
import com.projectrefocus.service.geography.dto.CountyDto;
import com.projectrefocus.service.geography.dto.StateDto;

import java.util.List;

public interface GeographyService {

    List<StateDto> getUSStates();

    List<CountyDto> getCountiesByState(Byte stateId);

    List<CityDto> getCitiesByCounty(Short countyId);
}
