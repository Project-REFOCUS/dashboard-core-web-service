package com.projectrefocus.service.dundas.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.projectrefocus.service.dundas.enums.ClassType;

import java.util.List;

public class DashboardDetailsAdapterEntity {

    private String id;
    private String name;
    @JsonProperty("__classType")
    private String classType;
    private String controlData;

    private List<DashboardDetailsMetricSetBindingEntity> metricSetBindings;

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

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public String getClassType() {
        return classType;
    }

    public void setControlData(String controlData) {
        this.controlData = controlData;
    }

    public String getControlData() {
        return controlData;
    }

    public void setMetricSetBindings(List<DashboardDetailsMetricSetBindingEntity> metricSetBindings) {
        this.metricSetBindings = metricSetBindings;
    }

    public List<DashboardDetailsMetricSetBindingEntity> getMetricSetBindings() {
        return metricSetBindings;
    }
}
