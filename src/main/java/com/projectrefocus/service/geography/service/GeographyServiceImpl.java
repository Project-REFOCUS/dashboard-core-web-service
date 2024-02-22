package com.projectrefocus.service.geography.service;

import com.projectrefocus.service.dundas.dto.DashboardDetails;
import com.projectrefocus.service.dundas.dto.DashboardFileObject;
import com.projectrefocus.service.dundas.entity.DashboardDetailsAdapterEntity;
import com.projectrefocus.service.dundas.entity.DashboardDetailsMetricSetBindingEntity;
import com.projectrefocus.service.dundas.entity.hierarchy.HierarchyMemberEntity;
import com.projectrefocus.service.dundas.service.DundasDashboardService;
import com.projectrefocus.service.dundas.service.DundasFileService;
import com.projectrefocus.service.dundas.service.DundasHierarchyService;
import com.projectrefocus.service.geography.dto.GeographyDto;
import com.projectrefocus.service.geography.enums.GeographyType;
import com.projectrefocus.service.geography.repository.StateRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

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

    private String getMetricSetIdFromCategory(String categoryId, GeographyType geographyType) throws NoSuchElementException {
        List<DashboardFileObject> categoryDashboardFiles = dundasFileService.getDashboardFilesInFolder(categoryId);
        DashboardFileObject fileObject = categoryDashboardFiles.stream()
                .filter(dashboardFileObject -> geographyType == null || dashboardFileObject.getTags().contains(geographyType.name()))
                .findFirst().orElseThrow();
        DashboardDetails dashboardDetails =  dundasDashboardService.getDashboardById(fileObject.getId());
        DashboardDetailsAdapterEntity metricSetAdapter = dashboardDetails.getAdapters().stream().filter(adapter -> !adapter.getMetricSetBindings().isEmpty()).findFirst().orElseThrow();
        DashboardDetailsMetricSetBindingEntity visualizationMetricSet = metricSetAdapter.getMetricSetBindings().stream().findFirst().orElseThrow();
        return visualizationMetricSet.getMetricSetId();
    }

    public List<GeographyDto> getStates() {
        return stateRepository.findAll().stream().map(stateEntity -> {
            GeographyDto dto = new GeographyDto();
            dto.setId(stateEntity.getId().toString());
            dto.setName(stateEntity.getName());

            return dto;
        }).toList();
    }

    public List<GeographyDto> getGeography(String categoryId, String geographyId, GeographyType geographyType) {
        try {
            String metricSetId = getMetricSetIdFromCategory(categoryId, geographyType);
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
