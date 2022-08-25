package com.projectrefocus.service.ethnicity.service;

import com.projectrefocus.service.ethnicity.dto.RaceEthnicityDto;
import com.projectrefocus.service.ethnicity.entity.RaceEthnicityEntity;
import com.projectrefocus.service.ethnicity.repository.RaceEthnicityRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RaceEthnicityServiceImpl implements RaceEthnicityService {

    private final RaceEthnicityRepository raceEthnicityRepository;

    public RaceEthnicityServiceImpl(RaceEthnicityRepository raceEthnicityRepository) {
        this.raceEthnicityRepository = raceEthnicityRepository;
    }

    public List<RaceEthnicityDto> getRaceEthnicityCategories() {
        return raceEthnicityRepository.findAll()
                .stream()
                .map(RaceEthnicityEntity::toDto)
                .collect(Collectors.toList());
    }
}
