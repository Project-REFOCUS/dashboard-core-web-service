package com.projectrefocus.service.dundas.api;

import com.projectrefocus.service.dundas.entity.LogOnRequestEntity;
import com.projectrefocus.service.dundas.entity.LogOnResponseEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "Login", url = "${dundas.api.baseurl}/LogOn")
public interface LoginApiClient {

    @RequestMapping(method = RequestMethod.POST, value = "/")
    LogOnResponseEntity login(@RequestBody LogOnRequestEntity logOnRequestEntity);
}
