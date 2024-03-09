package com.projectrefocus.service.dundas.entity.metricset;

import java.util.List;

public class MetricSetParameterHierarchyLevelEntity {
    private String uniqueName;
    private String hierarchyUniqueName;
    private String dataSourceId;
    private String compatibleUniqueName;
    private String caption;
    private Integer levelDepth;
    private List<MetricSetParameterHierarchyMemberColumnEntity> memberColumns;

    public void setUniqueName(String uniqueName) {
        this.uniqueName = uniqueName;
    }

    public String getUniqueName() {
        return uniqueName;
    }

    public void setHierarchyUniqueName(String hierarchyUniqueName) {
        this.hierarchyUniqueName = hierarchyUniqueName;
    }

    public String getHierarchyUniqueName() {
        return hierarchyUniqueName;
    }

    public void setCompatibleUniqueName(String compatibleUniqueName) {
        this.compatibleUniqueName = compatibleUniqueName;
    }

    public String getCompatibleUniqueName() {
        return compatibleUniqueName;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getCaption() {
        return caption;
    }

    public void setLevelDepth(Integer levelDepth) {
        this.levelDepth = levelDepth;
    }

    public Integer getLevelDepth() {
        return levelDepth;
    }

    public void setDataSourceId(String dataSourceId) {
        this.dataSourceId = dataSourceId;
    }

    public String getDataSourceId() {
        return dataSourceId;
    }

    public void setMemberColumns(List<MetricSetParameterHierarchyMemberColumnEntity> memberColumns) {
        this.memberColumns = memberColumns;
    }

    public List<MetricSetParameterHierarchyMemberColumnEntity> getMemberColumns() {
        return memberColumns;
    }
}
