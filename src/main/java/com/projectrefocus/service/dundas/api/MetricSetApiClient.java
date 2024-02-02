package com.projectrefocus.service.dundas.api;

import com.projectrefocus.service.dundas.entity.MetricSetDetailsEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "MetricSet", url = "${dundas.api.baseurl}/MetricSet")
public interface MetricSetApiClient {

    @RequestMapping(method = RequestMethod.GET, value = "/{metricSetId}")
    MetricSetDetailsEntity getMetricSetById(
            @RequestHeader(name = "Authorization") String sessionId,
            @PathVariable(name = "metricSetId") String metricSetId
    );
}
