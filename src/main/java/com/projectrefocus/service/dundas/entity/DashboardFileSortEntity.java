package com.projectrefocus.service.dundas.entity;

public class DashboardFileSortEntity {
    private String fileSystemQueryField;
    private String sortDirection;

    public DashboardFileSortEntity() {}

    public DashboardFileSortEntity(String fileSystemQueryField, String sortDirection) {
        this.fileSystemQueryField = fileSystemQueryField;
        this.sortDirection = sortDirection;
    }

    public void setFileSystemQueryField(String fileSystemQueryField) {
        this.fileSystemQueryField = fileSystemQueryField;
    }

    public String getFileSystemQueryField() {
        return fileSystemQueryField;
    }

    public void setSortDirection(String sortDirection) {
        this.sortDirection = sortDirection;
    }

    public String getSortDirection() {
        return sortDirection;
    }
}
