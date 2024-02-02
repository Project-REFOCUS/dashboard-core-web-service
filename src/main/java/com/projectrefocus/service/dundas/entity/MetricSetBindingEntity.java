package com.projectrefocus.service.dundas.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.projectrefocus.service.dundas.enums.ClassType;

public class MetricSetBindingEntity {
    private String id;
    private String elementUsageUniqueName;
    private String targetId;
    private String targetName;
    @JsonProperty("__classType")
    private String classType;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setElementUsageUniqueName(String elementUsageUniqueName) {
        this.elementUsageUniqueName = elementUsageUniqueName;
    }

    public String getElementUsageUniqueName() {
        return elementUsageUniqueName;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public String getClassType() {
        return classType;
    }
}
