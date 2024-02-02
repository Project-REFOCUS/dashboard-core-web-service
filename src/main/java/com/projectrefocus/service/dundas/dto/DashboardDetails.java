package com.projectrefocus.service.dundas.dto;

import com.projectrefocus.service.dundas.entity.DashboardDetailsAdapterEntity;
import com.projectrefocus.service.dundas.enums.ClassType;

import java.util.List;

public class DashboardDetails {

    private String id;
    private String name;
    private List<String> tags;
    private String classType;

    private List<DashboardDetailsAdapterEntity> adapters;

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

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public String getClassType() {
        return classType;
    }

    public void setAdapters(List<DashboardDetailsAdapterEntity> adapters) {
        this.adapters = adapters;
    }

    public List<DashboardDetailsAdapterEntity> getAdapters() {
        return adapters;
    }
}
