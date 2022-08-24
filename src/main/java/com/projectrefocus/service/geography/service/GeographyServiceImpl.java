package com.projectrefocus.service.geography.service;

import com.projectrefocus.service.geography.dto.CityDto;
import com.projectrefocus.service.geography.dto.CountyDto;
import com.projectrefocus.service.geography.dto.StateDto;
import com.projectrefocus.service.geography.entity.StateEntity;
import com.projectrefocus.service.geography.repository.CityRepository;
import com.projectrefocus.service.geography.repository.CountyRepository;
import com.projectrefocus.service.geography.repository.StateRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GeographyServiceImpl implements GeographyService {

    private final StateRepository stateRepository;
    private final CountyRepository countyRepository;
    private final CityRepository cityRepository;

    public GeographyServiceImpl(StateRepository stateRepository, CountyRepository countyRepository, CityRepository cityRepository) {
        this.stateRepository = stateRepository;
        this.countyRepository = countyRepository;
        this.cityRepository = cityRepository;
    }

    public List<StateDto> getUSStates() {
        return stateRepository.findAll()
                .stream()
                .map(StateEntity::toDto)
                .collect(Collectors.toList());
    }

    public List<CountyDto> getCountiesByState(Byte stateId) {
        return null;
    }

    public List<CityDto> getCitiesByCounty(Short countyId) {
        return null;
    }
}
