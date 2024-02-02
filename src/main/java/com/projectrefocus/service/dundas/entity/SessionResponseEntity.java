package com.projectrefocus.service.dundas.entity;

public class SessionResponseEntity {
    private String accountId;
    private String accountName;
    private String accountDisplayName;
    private String id;
    private String isSeatReserved;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountDisplayName(String accountDisplayName) {
        this.accountDisplayName = accountDisplayName;
    }

    public String getAccountDisplayName() {
        return accountDisplayName;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setIsSeatReserved(String isSeatReserved) {
        this.isSeatReserved = isSeatReserved;
    }

    public String getIsSeatReserved() {
        return isSeatReserved;
    }
}
