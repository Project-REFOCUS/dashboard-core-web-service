package com.projectrefocus.service.dundas.repository;

public class SessionRepository {
    private static String SessionId = null;

    public void setSessionId(String sessionId) {
        SessionRepository.SessionId = sessionId;
    }

    public String getSessionId() {
        return SessionRepository.SessionId;
    }
}
