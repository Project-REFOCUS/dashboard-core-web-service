package com.projectrefocus.service.dundas.dto;

import java.util.List;

public class MetricSetHierarchy {

    private String id;
    private String name;
    private String parentEntityId;
    private String entityId;
    private String caption;
    private String uniqueName;
    private String compatibleUniqueName;
    private List<MetricSetHierarchyLevel> levels;

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

    public void setParentEntityId(String parentEntityId) {
        this.parentEntityId = parentEntityId;
    }

    public String getParentEntityId() {
        return parentEntityId;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getCaption() {
        return caption;
    }

    public void setLevels(List<MetricSetHierarchyLevel> levels) {
        this.levels = levels;
    }

    public List<MetricSetHierarchyLevel> getLevels() {
        return levels;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getEntityId() {
        return entityId;
    }
}
