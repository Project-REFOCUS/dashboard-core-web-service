package com.projectrefocus.service.dundas.entity;

public class LogOnResponseEntity {

    //	"logOnFailureReason": "None",
    //	"message": "Successful",
    //	"sessionId": "4669503d-68b6-485c-9bdf-2732754e9fa0"
    private String logOnFailureReason;
    private String message;
    private String sessionId;

    public void setLogOnFailureReason(String logOnFailureReason) {
        this.logOnFailureReason = logOnFailureReason;
    }

    public String getLogOnFailureReason() {
        return logOnFailureReason;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
