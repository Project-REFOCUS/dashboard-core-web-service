package com.projectrefocus.service.dundas.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.projectrefocus.service.dundas.enums.DashboardFileObjectType;

import java.util.List;

public class DashboardDetailsResponseEntity {

    private String id;
    private String name;
    private String parentId;
    private List<String> tags;
    private DashboardFileObjectType objectType;

    private List<DashboardDetailsAdapterEntity> adapters;

    @JsonProperty("__classType")
    private String classType;

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

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public String getClassType() {
        return classType;
    }

    public void setObjectType(DashboardFileObjectType objectType) {
        this.objectType = objectType;
    }

    public DashboardFileObjectType getObjectType() {
        return objectType;
    }

    public void setAdapters(List<DashboardDetailsAdapterEntity> adapters) {
        this.adapters = adapters;
    }

    public List<DashboardDetailsAdapterEntity> getAdapters() {
        return adapters;
    }
}
