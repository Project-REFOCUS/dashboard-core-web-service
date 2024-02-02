package com.projectrefocus.service.dundas.entity;

import com.projectrefocus.service.dundas.entity.metricset.MetricSetRowHierarchyEntity;

import java.util.List;

public class MetricSetDetailsEntity {
    private String id;
    private String name;
    private String analysisStructureId;
    private List<MetricSetRowHierarchyEntity> rowHierarchies;

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

    public void setRowHierarchies(List<MetricSetRowHierarchyEntity> rowHierarchies) {
        this.rowHierarchies = rowHierarchies;
    }

    public List<MetricSetRowHierarchyEntity> getRowHierarchies() {
        return rowHierarchies;
    }
}
