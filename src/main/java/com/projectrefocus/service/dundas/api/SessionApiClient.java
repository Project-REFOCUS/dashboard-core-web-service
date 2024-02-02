package com.projectrefocus.service.dundas.api;

import com.projectrefocus.service.dundas.entity.SessionDetailsRequestEntity;
import com.projectrefocus.service.dundas.entity.SessionResponseEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "Session", url = "${dundas.api.baseurl}/Session")
public interface SessionApiClient {

    @RequestMapping(method = RequestMethod.POST, value = "/GetSession", headers = {"Content-Type=application/json"})
    ResponseEntity<SessionResponseEntity> getSession(
            @RequestHeader(name = "Authorization") String sessionId,
            @RequestBody SessionDetailsRequestEntity request
    );
}
