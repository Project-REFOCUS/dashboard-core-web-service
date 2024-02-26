package com.projectrefocus.service.graph.service;

import com.projectrefocus.service.dundas.dto.DashboardFileObject;
import com.projectrefocus.service.dundas.service.DundasInternalService;
import com.projectrefocus.service.geography.enums.GeographyType;
import com.projectrefocus.service.graph.enums.GraphType;
import com.projectrefocus.service.dundas.service.DundasFileService;
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

    public GraphServiceImpl(DundasFileService dundasFileService, DundasInternalService dundasInternalService) {
        this.dundasFileService = dundasFileService;
        this.dundasInternalService = dundasInternalService;
    }

    public List<Graph> getGraphsByCategoryId(String categoryId, GeographyType geographyType) {
        List<DashboardFileObject> dashboardFiles = dundasFileService.getDashboardFilesInFolder(categoryId);
        return dashboardFiles.stream().filter(d -> {
            Set<String> tags = new HashSet<>(d.getTags());
            return tags.contains(GraphType.BarChart.name()) || tags.contains(GraphType.LineChart.name()) &&
                    (geographyType == null || tags.contains(geographyType.name()));
        }).map(d -> {
            Graph graph = new Graph();
            GraphType graphType = d.getTags().contains(GraphType.BarChart.name()) ? GraphType.BarChart : GraphType.LineChart;
            graph.setUrl(String.format("%s/Dashboard/%s?sessionId=%s&vo=viewonly", host, d.getId(), dundasInternalService.getSessionId()));
            graph.setType(graphType);

            return graph;
        }).toList();
    }
}
