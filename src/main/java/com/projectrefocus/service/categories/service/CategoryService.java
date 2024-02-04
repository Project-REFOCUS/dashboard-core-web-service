package com.projectrefocus.service.categories.service;

import com.projectrefocus.service.categories.dto.CategoryDto;

import java.util.List;

public interface CategoryService {

    List<CategoryDto> getAllCategories();

    List<CategoryDto> getCategoriesByStates(List<Byte> stateId);
}
