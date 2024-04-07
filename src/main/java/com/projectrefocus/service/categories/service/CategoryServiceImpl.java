package com.projectrefocus.service.categories.service;

import com.projectrefocus.service.categories.dto.CategoryDto;
import com.projectrefocus.service.dundas.dto.DashboardFileObject;
import com.projectrefocus.service.dundas.service.DundasFileService;
import com.projectrefocus.service.geography.dto.GeographyDto;
import com.projectrefocus.service.geography.enums.GeographyType;
import com.projectrefocus.service.geography.service.GeographyInternalService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final DundasFileService dundasFileService;
    private final GeographyInternalService geographyInternalService;

    public CategoryServiceImpl(DundasFileService dundasFileService, GeographyInternalService geographyInternalService) {
        this.dundasFileService = dundasFileService;
        this.geographyInternalService = geographyInternalService;
    }

    private static CategoryDto toCategoryDto(DashboardFileObject activeDashboardFile) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(activeDashboardFile.getId());
        categoryDto.setName(activeDashboardFile.getName());

        return categoryDto;
    }

    public List<CategoryDto> getAllCategories() {
        List<DashboardFileObject> activeDashboardFiles = dundasFileService.getActiveDashboardFolders();
        return activeDashboardFiles.stream().map(CategoryServiceImpl::toCategoryDto).toList();
    }

    public List<CategoryDto> getCategoriesByStates(List<Byte> stateIds) {
        List<GeographyDto> geographyList = geographyInternalService.getGeographyByIds(stateIds);
        List<CategoryDto> allCategories = getAllCategories();
        List<CategoryDto> categories = new ArrayList<>();
        allCategories.forEach(categoryDto -> {
            List<GeographyDto> categoryGeographyList = geographyInternalService.getGeography(categoryDto.getId(), null, GeographyType.State);
            Set<String> categoryNames = categoryGeographyList.stream()
                    .map(GeographyDto::getName)
                    .collect(Collectors.toSet());
            if (categoryNames.containsAll(geographyList.stream().map(GeographyDto::getName).toList())) {
                categories.add(categoryDto);
            }
        });
        return categories;
    }
}
