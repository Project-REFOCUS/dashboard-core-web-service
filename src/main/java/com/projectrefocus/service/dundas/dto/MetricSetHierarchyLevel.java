package com.projectrefocus.service.dundas.dto;

public class MetricSetHierarchyLevel {
    private String caption;
    private String uniqueName;
    private String compatibleUniqueName;
    private String hierarchyUniqueName;
    private String dataSourceId;

    private Integer levelDepth;

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getCaption() {
        return caption;
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

    public void setHierarchyUniqueName(String hierarchyUniqueName) {
        this.hierarchyUniqueName = hierarchyUniqueName;
    }

    public String getHierarchyUniqueName() {
        return hierarchyUniqueName;
    }

    public void setDataSourceId(String dataSourceId) {
        this.dataSourceId = dataSourceId;
    }

    public String getDataSourceId() {
        return dataSourceId;
    }

    public void setLevelDepth(Integer levelDepth) {
        this.levelDepth = levelDepth;
    }

    public Integer getLevelDepth() {
        return levelDepth;
    }
}
