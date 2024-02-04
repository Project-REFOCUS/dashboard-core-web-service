package com.projectrefocus.service.geography.service;

import com.projectrefocus.service.geography.dto.CityDto;
import com.projectrefocus.service.geography.dto.CountyDto;
import com.projectrefocus.service.geography.dto.GeographyDto;
import com.projectrefocus.service.geography.dto.StateDto;

import java.util.List;

public interface GeographyService {

    List<GeographyDto> getGeography(String categoryId, String geographyId);

    List<GeographyDto> getStates();

    List<StateDto> getStatesByCategory(String categoryId);

    List<CountyDto> getCountiesByCategory(String categoryId, String stateId);

    List<CityDto> getCitiesByCategory(String categoryId, String countyId);
}
