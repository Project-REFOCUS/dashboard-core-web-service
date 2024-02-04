package com.projectrefocus.service.geography.service;

import com.projectrefocus.service.dundas.dto.DashboardDetails;
import com.projectrefocus.service.dundas.dto.DashboardFileObject;
import com.projectrefocus.service.dundas.dto.MetricSetDetails;
import com.projectrefocus.service.dundas.entity.DashboardDetailsAdapterEntity;
import com.projectrefocus.service.dundas.entity.DashboardDetailsMetricSetBindingEntity;
import com.projectrefocus.service.dundas.entity.hierarchy.HierarchyMemberEntity;
import com.projectrefocus.service.dundas.service.DundasDashboardService;
import com.projectrefocus.service.dundas.service.DundasFileService;
import com.projectrefocus.service.dundas.service.DundasHierarchyService;
import com.projectrefocus.service.geography.dto.CityDto;
import com.projectrefocus.service.geography.dto.CountyDto;
import com.projectrefocus.service.geography.dto.GeographyDto;
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

    private final DundasFileService dundasFileService;
    private final DundasDashboardService dundasDashboardService;
    private final DundasHierarchyService dundasHierarchyService;

    private final StateRepository stateRepository;

    public GeographyServiceImpl(StateRepository stateRepository, DundasFileService dundasFileService, DundasDashboardService dundasDashboardService, DundasHierarchyService dundasHierarchyService) {
        this.dundasFileService = dundasFileService;
        this.dundasDashboardService = dundasDashboardService;
        this.stateRepository = stateRepository;
        this.dundasHierarchyService = dundasHierarchyService;
    }

    private String getMetricSetIdFromCategory(String categoryId) {
        List<DashboardFileObject> categoryDashboardFiles = dundasFileService.getDashboardFilesInFolder(categoryId);
        DashboardFileObject fileObject = categoryDashboardFiles.stream().findFirst().orElseThrow();
        DashboardDetails dashboardDetails =  dundasDashboardService.getDashboardById(fileObject.getId());
        DashboardDetailsAdapterEntity metricSetAdapter = dashboardDetails.getAdapters().stream().filter(adapter -> !adapter.getMetricSetBindings().isEmpty()).findFirst().orElseThrow();
        DashboardDetailsMetricSetBindingEntity visualizationMetricSet = metricSetAdapter.getMetricSetBindings().stream().findFirst().orElseThrow();
        return visualizationMetricSet.getMetricSetId();
    }

    public List<StateDto> getStatesByCategory(String categoryId) {
        String metricSetId = getMetricSetIdFromCategory(categoryId);
        List<HierarchyMemberEntity> hierarchyMembers = dundasHierarchyService.getHierarchyMembers(metricSetId, null);
        return hierarchyMembers.stream().map(member -> {
            StateDto dto = new StateDto();
            dto.setId(member.getUniqueName());
            dto.setName(member.getCaption());
            dto.setShortName(member.getLevelUniqueName());

            return dto;
        }).toList();
    }

    public List<CountyDto> getCountiesByCategory(String categoryId, String memberUniqueName) {
        String metricSetId = getMetricSetIdFromCategory(categoryId);
        List<HierarchyMemberEntity> hierarchyMembers = dundasHierarchyService.getHierarchyMembers(metricSetId, memberUniqueName);
        return hierarchyMembers.stream().map(member -> {
            CountyDto dto = new CountyDto();
            dto.setId(member.getUniqueName());
            dto.setName(member.getCaption());

            return dto;
        }).toList();
    }

    public List<CityDto> getCitiesByCategory(String categoryId, String memberUniqueName) {
        String metricSetId = getMetricSetIdFromCategory(categoryId);
        List<HierarchyMemberEntity> hierarchyMembers = dundasHierarchyService.getHierarchyMembers(metricSetId, memberUniqueName);
        return hierarchyMembers.stream().map(member -> {
            CityDto dto = new CityDto();
            dto.setId(member.getUniqueName());
            dto.setName(member.getCaption());

            return dto;
        }).toList();
    }

    public List<GeographyDto> getStates() {
        return stateRepository.findAll().stream().map(stateEntity -> {
            GeographyDto dto = new GeographyDto();
            dto.setId(stateEntity.getId().toString());
            dto.setName(stateEntity.getName());

            return dto;
        }).toList();
    }

    public List<GeographyDto> getGeography(String categoryId, String geographyId) {
        String metricSetId = getMetricSetIdFromCategory(categoryId);
        List<HierarchyMemberEntity> hierarchyMemberEntities = dundasHierarchyService.getHierarchyMembers(metricSetId, geographyId);
        return hierarchyMemberEntities.stream().map(member -> {
            GeographyDto dto = new GeographyDto();
            dto.setId(member.getUniqueName());
            dto.setName(member.getCaption());

            return dto;
        }).toList();
    }
}
