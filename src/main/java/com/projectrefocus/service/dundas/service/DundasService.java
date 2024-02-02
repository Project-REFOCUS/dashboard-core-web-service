package com.projectrefocus.service.dundas.service;

import com.projectrefocus.service.dundas.dto.IndicatorCategoryDto;

import java.util.List;

public interface DundasService {

    List<IndicatorCategoryDto> getCategories();
}
