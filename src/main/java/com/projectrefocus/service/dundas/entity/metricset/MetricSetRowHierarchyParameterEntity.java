package com.projectrefocus.service.dundas.entity.metricset;

public class MetricSetRowHierarchyParameterEntity {
    private String id;
    private String name;
    private String parentEntityId;

    private MetricSetParameterHierarchyEntity hierarchy;

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

    public void setParentEntityId(String parentEntityId) {
        this.parentEntityId = parentEntityId;
    }

    public String getParentEntityId() {
        return parentEntityId;
    }

    public void setHierarchy(MetricSetParameterHierarchyEntity hierarchy) {
        this.hierarchy = hierarchy;
    }

    public MetricSetParameterHierarchyEntity getHierarchy() {
        return hierarchy;
    }
}
