package com.projectrefocus.service.dundas.entity.hierarchy;

public class HierarchyMemberRequestEntity {
    private String hierarchyUniqueName;
    private String levelUniqueName;
    private String memberUniqueName;

    public void setHierarchyUniqueName(String hierarchyUniqueName) {
        this.hierarchyUniqueName = hierarchyUniqueName;
    }

    public String getHierarchyUniqueName() {
        return hierarchyUniqueName;
    }

    public void setLevelUniqueName(String levelUniqueName) {
        this.levelUniqueName = levelUniqueName;
    }

    public String getLevelUniqueName() {
        return levelUniqueName;
    }

    public void setMemberUniqueName(String memberUniqueName) {
        this.memberUniqueName = memberUniqueName;
    }

    public String getMemberUniqueName() {
        return memberUniqueName;
    }
}
