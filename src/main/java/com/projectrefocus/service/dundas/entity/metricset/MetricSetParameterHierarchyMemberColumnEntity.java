package com.projectrefocus.service.dundas.entity.metricset;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.projectrefocus.service.dundas.enums.ClassType;

public class MetricSetParameterHierarchyMemberColumnEntity {

    private String columnId;
    private String uniqueName;
    private String caption;
    private String columnType;

    @JsonProperty("__classType")
    private String classType;

    public void setColumnId(String columnId) {
        this.columnId = columnId;
    }

    public String getColumnId() {
        return columnId;
    }

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

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public String getClassType() {
        return classType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    public String getColumnType() {
        return columnType;
    }
}
