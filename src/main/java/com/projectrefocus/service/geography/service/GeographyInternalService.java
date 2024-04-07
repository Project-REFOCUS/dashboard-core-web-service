package com.projectrefocus.service.geography.service;

import com.projectrefocus.service.geography.dto.GeographyDto;
import com.projectrefocus.service.geography.enums.GeographyType;

import java.util.List;

public interface GeographyInternalService {
    List<GeographyDto> getGeographyByIds(List<Byte> ids);

    List<GeographyDto> getGeography(String categoryId, String geographyId, GeographyType geographyType);
}
