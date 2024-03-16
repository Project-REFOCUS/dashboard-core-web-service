package com.projectrefocus.service.categories.service;

import com.projectrefocus.service.categories.dto.CategoryDto;
import com.projectrefocus.service.categories.repository.*;
import com.projectrefocus.service.dundas.dto.DashboardFileObject;
import com.projectrefocus.service.dundas.service.DundasFileService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final DundasFileService dundasFileService;
    private final CovidCasesRepository covidCasesRepository;
    private final EmploymentStatusRepository employmentStatusRepository;
    private final MotorVehicleCollisionRepository motorVehicleCollisionRepository;
    private final PopulationEstimateRepository populationEstimateRepository;
    private final PoliceShootingRepository policeShootingRepository;
    private final SupplementalNutritionAssistanceProgramRepository supplementalNutritionAssistanceProgramRepository;

    public CategoryServiceImpl(
            DundasFileService dundasFileService, EmploymentStatusRepository employmentStatusRepository,
            MotorVehicleCollisionRepository motorVehicleCollisionRepository, SupplementalNutritionAssistanceProgramRepository supplementalNutritionAssistanceProgramRepository,
            PopulationEstimateRepository populationEstimateRepository, CovidCasesRepository covidCasesRepository, PoliceShootingRepository policeShootingRepository
    ) {
        this.dundasFileService = dundasFileService;
        this.covidCasesRepository = covidCasesRepository;
        this.employmentStatusRepository = employmentStatusRepository;
        this.motorVehicleCollisionRepository = motorVehicleCollisionRepository;
        this.populationEstimateRepository = populationEstimateRepository;
        this.policeShootingRepository = policeShootingRepository;
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
        if (stateIds.stream().allMatch(id -> employmentStatusRepository.doesCategoryIncludeState(id) != 0)) {
            categories.add("Employment Status");
        }
        if (stateIds.stream().allMatch(id -> motorVehicleCollisionRepository.doesCategoryIncludeState(id) != 0)) {
            categories.add("Motor Vehicle Collision");
        }
        if (stateIds.stream().allMatch(id -> supplementalNutritionAssistanceProgramRepository.doesCategoryIncludeState(id) != 0)) {
            categories.add("Supplemental Nutrition Assistance Program");
        }
        if (stateIds.stream().allMatch(id -> populationEstimateRepository.doesCategoryIncludeState(id) != 0)) {
            categories.add("Population Estimates");
        }
        if (stateIds.stream().allMatch(id -> covidCasesRepository.doesCategoryIncludeState(id) != 0)) {
            categories.add("Covid 19 Cases");
        }
        if (stateIds.stream().allMatch(id -> policeShootingRepository.doesCategoryIncludeState(id) != 0)) {
            categories.add("Police Shootings");
        }
        return allCategories.stream().filter(category -> categories.contains(category.getName())).toList();
    }
}
