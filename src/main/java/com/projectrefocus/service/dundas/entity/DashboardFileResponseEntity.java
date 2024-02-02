package com.projectrefocus.service.dundas.entity;

import com.projectrefocus.service.dundas.enums.DashboardFileObjectType;

import java.util.List;

public class DashboardFileResponseEntity {
    private String id;
    private String name;
    private String fullName;
    private String description;
    private DashboardFileObjectType objectType;
    private List<String> tags;

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

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setObjectType(DashboardFileObjectType objectType) {
        this.objectType = objectType;
    }

    public DashboardFileObjectType getObjectType() {
        return objectType;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<String> getTags() {
        return tags;
    }
}
