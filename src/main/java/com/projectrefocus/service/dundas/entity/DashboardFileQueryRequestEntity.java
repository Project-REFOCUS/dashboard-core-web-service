package com.projectrefocus.service.dundas.entity;

import java.util.List;

public class DashboardFileQueryRequestEntity {
    private String accountId;
    private String objectPrivilegeId;
    private List<DashboardFileSortEntity> orderBy;
    private Integer pageNumber;
    private Integer pageSize;
    private List<String> queryRootIds;

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setObjectPrivilegeId(String objectPrivilegeId) {
        this.objectPrivilegeId = objectPrivilegeId;
    }

    public String getObjectPrivilegeId() {
        return objectPrivilegeId;
    }

    public void setOrderBy(List<DashboardFileSortEntity> orderBy) {
        this.orderBy = orderBy;
    }

    public List<DashboardFileSortEntity> getOrderBy() {
        return orderBy;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setQueryRootIds(List<String> queryRootIds) {
        this.queryRootIds = queryRootIds;
    }

    public List<String> getQueryRootIds() {
        return queryRootIds;
    }
}
