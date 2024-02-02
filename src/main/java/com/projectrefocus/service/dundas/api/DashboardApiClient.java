package com.projectrefocus.service.dundas.api;

import com.projectrefocus.service.dundas.entity.DashboardDetailsResponseEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "Dashboard", url = "${dundas.api.baseurl}/Dashboard")
public interface DashboardApiClient {

    @RequestMapping(method = RequestMethod.GET, value = "/{dashboardId}")
    DashboardDetailsResponseEntity getDashboardDetails(
            @RequestHeader(name = "Authorization") String sessionId,
            @PathVariable(name = "dashboardId") String dashboardId
    );
}
