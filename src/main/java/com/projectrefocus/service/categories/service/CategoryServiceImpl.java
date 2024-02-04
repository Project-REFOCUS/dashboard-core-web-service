package com.projectrefocus.service.categories.service;

import com.projectrefocus.service.categories.dto.CategoryDto;
import com.projectrefocus.service.categories.repository.EmploymentStatusRepository;
import com.projectrefocus.service.categories.repository.MotorVehicleCollisionRepository;
import com.projectrefocus.service.categories.repository.SupplementalNutritionAssistanceProgramRepository;
import com.projectrefocus.service.dundas.dto.DashboardFileObject;
import com.projectrefocus.service.dundas.service.DundasFileService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final DundasFileService dundasFileService;
    private final EmploymentStatusRepository employmentStatusRepository;
    private final MotorVehicleCollisionRepository motorVehicleCollisionRepository;
    private final SupplementalNutritionAssistanceProgramRepository supplementalNutritionAssistanceProgramRepository;

    public CategoryServiceImpl(
            DundasFileService dundasFileService, EmploymentStatusRepository employmentStatusRepository,
            MotorVehicleCollisionRepository motorVehicleCollisionRepository, SupplementalNutritionAssistanceProgramRepository supplementalNutritionAssistanceProgramRepository
    ) {
        this.dundasFileService = dundasFileService;
        this.employmentStatusRepository = employmentStatusRepository;
        this.motorVehicleCollisionRepository = motorVehicleCollisionRepository;
        this.supplementalNutritionAssistanceProgramRepository = supplementalNutritionAssistanceProgramRepository;
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
        List<CategoryDto> allCategories = getAllCategories();
        Set<String> categories = new HashSet<>();
        boolean employmentStatusHasAllStates = stateIds.stream().allMatch(id -> {
            return employmentStatusRepository.doesCategoryIncludeStates(id) != 0;
        });
        if (stateIds.stream().allMatch(id -> employmentStatusRepository.doesCategoryIncludeStates(id) != 0)) {
            categories.add("Employment Status");
        }
        if (stateIds.stream().allMatch(id -> motorVehicleCollisionRepository.doesCategoryIncludeStates(id) != 0)) {
            categories.add("Motor Vehicle Collision");
        }
        if (stateIds.stream().allMatch(id -> supplementalNutritionAssistanceProgramRepository.doesCategoryIncludeStates(id) != 0)) {
            categories.add("Supplemental Nutrition Assistance Program");
        }
        return allCategories.stream().filter(category -> categories.contains(category.getName())).toList();
    }
}
