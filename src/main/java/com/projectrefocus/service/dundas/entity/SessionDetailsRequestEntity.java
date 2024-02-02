package com.projectrefocus.service.dundas.entity;

public class SessionDetailsRequestEntity {

    private String sessionId;
    private Boolean includeSecureAttributes;

    public SessionDetailsRequestEntity() {}

    public SessionDetailsRequestEntity(String sessionId) {
        this.sessionId = sessionId;
        includeSecureAttributes = Boolean.FALSE;
    }
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setIncludeSecureAttributes(Boolean includeSecureAttributes) {
        this.includeSecureAttributes = includeSecureAttributes;
    }

    public Boolean getIncludeSecureAttributes() {
        return includeSecureAttributes;
    }
}
