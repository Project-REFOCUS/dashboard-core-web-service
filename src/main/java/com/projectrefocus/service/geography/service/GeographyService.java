package com.projectrefocus.service.geography.service;

import com.projectrefocus.service.geography.dto.CityDto;
import com.projectrefocus.service.geography.dto.CountyDto;
import com.projectrefocus.service.geography.dto.GeographyDto;
import com.projectrefocus.service.geography.dto.StateDto;
import com.projectrefocus.service.geography.enums.GeographyType;

import java.util.List;

public interface GeographyService {

    List<GeographyDto> getGeography(String categoryId, String geographyId, GeographyType geographyType);

    List<GeographyDto> getStates();
}
