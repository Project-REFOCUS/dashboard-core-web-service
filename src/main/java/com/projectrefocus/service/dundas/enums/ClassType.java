package com.projectrefocus.service.dundas.enums;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ClassType {

    Chart("dundas.view.controls.Chart"),
    @JsonValue
    Dashboard("dundas.entities.Dashboard"),
    Legend("dundas.view.controls.Legend"),
    ParameterHierarchyPicker("dundas.view.controls.ParameterHierarchyPicker"),
    ParameterHierarchyLevel("dundas.view.controls.ParameterHierarchyLevel");

    private final String id;
    ClassType(String id) {
        this.id = id;
    }

    public String toString() {
        return id;
    }
}
