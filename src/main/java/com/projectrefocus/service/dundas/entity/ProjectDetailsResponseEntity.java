package com.projectrefocus.service.dundas.entity;

public class ProjectDetailsResponseEntity {

    private String id;
    private String name;
    private ProjectDashboardsRootFolderEntity dashboardsRootFolder;

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

    public void setDashboardsRootFolder(ProjectDashboardsRootFolderEntity dashboardsRootFolder) {
        this.dashboardsRootFolder = dashboardsRootFolder;
    }

    public ProjectDashboardsRootFolderEntity getDashboardsRootFolder() {
        return dashboardsRootFolder;
    }
}
