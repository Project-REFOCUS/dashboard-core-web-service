package com.projectrefocus.service.graph.service;

import com.projectrefocus.service.dundas.dto.DashboardFileObject;
import com.projectrefocus.service.dundas.dto.MetricSetDetails;
import com.projectrefocus.service.dundas.dto.MetricSetHierarchyLevel;
import com.projectrefocus.service.dundas.service.*;
import com.projectrefocus.service.geography.enums.GeographyType;
import com.projectrefocus.service.graph.enums.GraphType;
import com.projectrefocus.service.graph.dto.Graph;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class GraphServiceImpl implements GraphService {

    @Value("${dundas.api.host}")
    private String host;

    private final DundasFileService dundasFileService;
    private final DundasInternalService dundasInternalService;
    private final DundasMetricSetService dundasMetricSetService;

    public GraphServiceImpl(DundasFileService dundasFileService, DundasInternalService dundasInternalService, DundasMetricSetService dundasMetricSetService) {
        this.dundasFileService = dundasFileService;
        this.dundasMetricSetService = dundasMetricSetService;
        this.dundasInternalService = dundasInternalService;
    }

    public List<Graph> getGraphsByCategoryId(String categoryId, GeographyType geographyType, List<String> geographyIds) {
        String metricSetId = dundasMetricSetService.getMetricSetId(categoryId, geographyType);
        MetricSetDetails metricSetDetails = dundasMetricSetService.getMetricSetDetails(metricSetId);
        List<DashboardFileObject> dashboardFiles = dundasFileService.getDashboardFilesInFolder(categoryId);
        return dashboardFiles.stream().filter(d -> {
            Set<String> tags = new HashSet<>(d.getTags());
            return tags.contains(GraphType.BarChart.name()) || tags.contains(GraphType.LineChart.name()) &&
                    (geographyType == null || tags.contains(geographyType.name()));
        }).map(d -> {
            Graph graph = new Graph();
            MetricSetHierarchyLevel hierarchyLevel = metricSetDetails.getMetricSetHierarchy().getLevels()
                    .stream()
                    .filter(level -> geographyType == null || level.getCaption().equals(geographyType.name())).findFirst().orElse(null);

            GraphType graphType = d.getTags().contains(GraphType.BarChart.name()) ? GraphType.BarChart : GraphType.LineChart;
            StringBuilder query = new StringBuilder();
            if (hierarchyLevel != null) {
                query.append(String.format("&$VP_Level=%s", hierarchyLevel.getLevelDepth()));
            }
            if (geographyIds != null) {
                query.append(String.format("&$VP_All3Levels=%s", String.join("|", geographyIds)));
            }
            graph.setUrl(String.format("%s/Dashboard/%s?sessionId=%s&vo=viewonly%s", host, d.getId(), dundasInternalService.getSessionId(), query));
            graph.setType(graphType);

            return graph;
        }).toList();
    }
}
