package com.projectrefocus.service.dundas.dto;

import com.projectrefocus.service.graph.enums.GraphType;

public class IndicatorCategoryDto {

    private String id;
    private String name;
    private GraphType graphType;

    private String dashboardId;

    public IndicatorCategoryDto() {}

    public IndicatorCategoryDto(String id, String dashboardId, String name, GraphType graphType) {
        this.id = id;
        this.name = name;
        this.dashboardId = dashboardId;
        this.graphType = graphType;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setGraphType(GraphType graphType) {
        this.graphType = graphType;
    }

    public GraphType getGraphType() {
        return graphType;
    }

    public void setDashboardId(String dashboardId) {
        this.dashboardId = dashboardId;
    }

    public String getDashboardId() {
        return dashboardId;
    }
}
