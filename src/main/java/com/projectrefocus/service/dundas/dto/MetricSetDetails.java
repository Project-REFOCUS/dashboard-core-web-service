package com.projectrefocus.service.dundas.dto;

public class MetricSetDetails {
    private String id;
    private String name;
    private String description;
    private String analysisStructureId;

    private MetricSetHierarchy metricSetHierarchy;

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

    public void setAnalysisStructureId(String analysisStructureId) {
        this.analysisStructureId = analysisStructureId;
    }

    public String getAnalysisStructureId() {
        return analysisStructureId;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setMetricSetHierarchy(MetricSetHierarchy metricSetHierarchy) {
        this.metricSetHierarchy = metricSetHierarchy;
    }

    public MetricSetHierarchy getMetricSetHierarchy() {
        return metricSetHierarchy;
    }
}
