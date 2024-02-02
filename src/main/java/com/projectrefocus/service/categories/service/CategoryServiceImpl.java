package com.projectrefocus.service.categories.service;

import com.projectrefocus.service.categories.dto.CategoryDto;
import com.projectrefocus.service.dundas.dto.DashboardFileObject;
import com.projectrefocus.service.dundas.service.DundasFileService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final DundasFileService dundasFileService;

    public CategoryServiceImpl(DundasFileService dundasFileService) {
        this.dundasFileService = dundasFileService;
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
}
