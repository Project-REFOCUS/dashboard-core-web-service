package com.projectrefocus.service.dundas.entity.hierarchy;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.projectrefocus.service.dundas.enums.ClassType;

public class HierarchyMemberEntity {

    private String uniqueName;
    private String compatibleUniqueName;
    private String caption;
    private String memberKind;
    private String hierarchyUniqueName;
    private String levelUniqueName;
    private String levelCompatibleUniqueName;
    @JsonProperty("__classType")
    private String classType;

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

    public void setCompatibleUniqueName(String compatibleUniqueName) {
        this.compatibleUniqueName = compatibleUniqueName;
    }

    public String getCompatibleUniqueName() {
        return compatibleUniqueName;
    }

    public void setHierarchyUniqueName(String hierarchyUniqueName) {
        this.hierarchyUniqueName = hierarchyUniqueName;
    }

    public String getHierarchyUniqueName() {
        return hierarchyUniqueName;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public String getClassType() {
        return classType;
    }

    public void setMemberKind(String memberKind) {
        this.memberKind = memberKind;
    }

    public String getMemberKind() {
        return memberKind;
    }

    public void setLevelCompatibleUniqueName(String levelCompatibleUniqueName) {
        this.levelCompatibleUniqueName = levelCompatibleUniqueName;
    }

    public String getLevelCompatibleUniqueName() {
        return levelCompatibleUniqueName;
    }

    public void setLevelUniqueName(String levelUniqueName) {
        this.levelUniqueName = levelUniqueName;
    }

    public String getLevelUniqueName() {
        return levelUniqueName;
    }
}
