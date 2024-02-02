package com.projectrefocus.service.dundas.api;

import com.projectrefocus.service.dundas.entity.DashboardFileQueryRequestWrapperEntity;
import com.projectrefocus.service.dundas.entity.DashboardFileResponseEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "File", url = "${dundas.api.baseurl}/File")
public interface FileApiClient {

    @RequestMapping(method = RequestMethod.POST, value = "/query")
    List<DashboardFileResponseEntity> getDashboardFiles(
            @RequestParam (name = "sessionId") String sessionId,
            @RequestBody DashboardFileQueryRequestWrapperEntity request
    );
}
