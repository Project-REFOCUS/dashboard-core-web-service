package com.projectrefocus.service.dundas.entity;

import com.projectrefocus.service.dundas.dto.LoginDto;

public class LogOnRequestEntity extends LoginDto {
    private Boolean deleteOtherSessions;
    private Boolean isWindowsLogOn;

    public LogOnRequestEntity() {}

    public LogOnRequestEntity(LoginDto loginDto) {
         setAccountName(loginDto.getAccountName());
         setPassword(loginDto.getPassword());
         deleteOtherSessions = Boolean.TRUE;
         isWindowsLogOn = Boolean.FALSE;
    }

    public void setWindowsLogOn(Boolean windowsLogOn) {
        this.isWindowsLogOn = windowsLogOn;
    }

    public Boolean getWindowsLogOn() {
        return isWindowsLogOn;
    }

    public void setDeleteOtherSessions(Boolean deleteOtherSessions) {
        this.deleteOtherSessions = deleteOtherSessions;
    }

    public Boolean getDeleteOtherSessions() {
        return deleteOtherSessions;
    }
}
