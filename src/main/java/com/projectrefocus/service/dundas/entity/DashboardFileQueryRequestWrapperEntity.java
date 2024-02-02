package com.projectrefocus.service.dundas.entity;

public class DashboardFileQueryRequestWrapperEntity {

    private DashboardFileQueryRequestEntity queryFileSystemEntriesOptions;

    public DashboardFileQueryRequestWrapperEntity () {}

    public DashboardFileQueryRequestWrapperEntity(DashboardFileQueryRequestEntity queryFileSystemEntriesOptions) {
        this.queryFileSystemEntriesOptions = queryFileSystemEntriesOptions;
    }

    public void setQueryFileSystemEntriesOptions(DashboardFileQueryRequestEntity queryFileSystemEntriesOptions) {
        this.queryFileSystemEntriesOptions = queryFileSystemEntriesOptions;
    }

    public DashboardFileQueryRequestEntity getQueryFileSystemEntriesOptions() {
        return queryFileSystemEntriesOptions;
    }
}
