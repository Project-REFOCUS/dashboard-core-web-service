package com.projectrefocus.service.graph.service;

import com.projectrefocus.service.geography.enums.GeographyType;
import com.projectrefocus.service.graph.dto.Graph;

import java.util.List;

public interface GraphService {

    List<Graph> getGraphsByCategoryId(String categoryId, GeographyType geographyType, List<String> geographyIds);
}
