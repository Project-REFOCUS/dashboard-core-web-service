package com.projectrefocus.service.geography.service;

import com.projectrefocus.service.dundas.entity.hierarchy.HierarchyMemberEntity;
import com.projectrefocus.service.dundas.service.DundasHierarchyService;
import com.projectrefocus.service.dundas.service.DundasMetricSetService;
import com.projectrefocus.service.geography.dto.GeographyDto;
import com.projectrefocus.service.geography.enums.GeographyType;
import com.projectrefocus.service.geography.repository.StateRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class GeographyServiceImpl implements GeographyService, GeographyInternalService {

    private final DundasHierarchyService dundasHierarchyService;
    private final DundasMetricSetService dundasMetricSetService;
    private final StateRepository stateRepository;

    public GeographyServiceImpl(StateRepository stateRepository, DundasHierarchyService dundasHierarchyService, DundasMetricSetService dundasMetricSetService) {
        this.stateRepository = stateRepository;
        this.dundasHierarchyService = dundasHierarchyService;
        this.dundasMetricSetService = dundasMetricSetService;
    }

    public List<GeographyDto> getStates() {
        return stateRepository.findAll().stream().map(stateEntity -> {
            GeographyDto dto = new GeographyDto();
            dto.setId(stateEntity.getId().toString());
            dto.setName(stateEntity.getName());

            return dto;
        }).toList();
    }

    public List<GeographyDto> getGeographyByIds(List<Byte> ids) {
        return stateRepository.findAllById(ids).stream().map(stateEntity -> {
            GeographyDto dto = new GeographyDto();
            dto.setId(stateEntity.getId().toString());
            dto.setName(stateEntity.getName());

            return dto;
        }).toList();
    }

    public List<GeographyDto> getGeography(String categoryId, String geographyId, GeographyType geographyType) {
        try {
            String metricSetId = dundasMetricSetService.getMetricSetId(categoryId, geographyType);
            List<HierarchyMemberEntity> hierarchyMemberEntities = dundasHierarchyService.getHierarchyMembers(metricSetId, geographyId);
            return hierarchyMemberEntities.stream().map(member -> {
                GeographyDto dto = new GeographyDto();
                dto.setId(member.getUniqueName());
                dto.setName(member.getCaption());

                return dto;
            }).toList();
        } catch (NoSuchElementException exception) {
            return new ArrayList<>();
        }
    }
}
