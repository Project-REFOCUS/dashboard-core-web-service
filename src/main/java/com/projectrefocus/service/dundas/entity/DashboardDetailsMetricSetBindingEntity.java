package com.projectrefocus.service.dundas.entity;

import java.util.List;

public class DashboardDetailsMetricSetBindingEntity {

    private String id;
    private String name;
    private Boolean includeAnnotations;
    private Boolean includeMeasureCorrections;
    private String metricSetId;
    private List<MetricSetBindingEntity> bindings;

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

    public void setMetricSetId(String metricSetId) {
        this.metricSetId = metricSetId;
    }

    public String getMetricSetId() {
        return metricSetId;
    }

    public void setIncludeAnnotations(Boolean includeAnnotations) {
        this.includeAnnotations = includeAnnotations;
    }

    public Boolean getIncludeAnnotations() {
        return includeAnnotations;
    }

    public void setIncludeMeasureCorrections(Boolean includeMeasureCorrections) {
        this.includeMeasureCorrections = includeMeasureCorrections;
    }

    public Boolean getIncludeMeasureCorrections() {
        return includeMeasureCorrections;
    }

    public void setBindings(List<MetricSetBindingEntity> bindings) {
        this.bindings = bindings;
    }

    public List<MetricSetBindingEntity> getBindings() {
        return bindings;
    }
}
