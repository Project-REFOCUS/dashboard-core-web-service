package com.projectrefocus.service.dundas.entity.metricset;

public class MetricSetRowHierarchyEntity {

    private String uniqueName;
    private String caption;
    private String placement;
    private String parentEntityId;
    private MetricSetRowHierarchyParameterEntity parameter;

    public void setUniqueName(String uniqueName) {
        this.uniqueName = uniqueName;
    }

    public String getUniqueName() {
        return uniqueName;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getCaption() {
        return caption;
    }

    public void setParentEntityId(String parentEntityId) {
        this.parentEntityId = parentEntityId;
    }

    public String getParentEntityId() {
        return parentEntityId;
    }

    public void setPlacement(String placement) {
        this.placement = placement;
    }

    public String getPlacement() {
        return placement;
    }

    public void setParameter(MetricSetRowHierarchyParameterEntity parameter) {
        this.parameter = parameter;
    }

    public MetricSetRowHierarchyParameterEntity getParameter() {
        return parameter;
    }
}
