package com.projectrefocus.service.dundas.entity.metricset;

import java.util.List;

public class MetricSetParameterHierarchyEntity {
    private String uniqueName;
    private String caption;
    private String category;
    private String compatibleUniqueName;
    private List<MetricSetParameterHierarchyLevelEntity> levels;

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getCaption() {
        return caption;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setUniqueName(String uniqueName) {
        this.uniqueName = uniqueName;
    }

    public String getUniqueName() {
        return uniqueName;
    }

    public void setCompatibleUniqueName(String compatibleUniqueName) {
        this.compatibleUniqueName = compatibleUniqueName;
    }

    public String getCompatibleUniqueName() {
        return compatibleUniqueName;
    }

    public List<MetricSetParameterHierarchyLevelEntity> getLevels() {
        return levels;
    }

    public void setLevels(List<MetricSetParameterHierarchyLevelEntity> levels) {
        this.levels = levels;
    }
}
