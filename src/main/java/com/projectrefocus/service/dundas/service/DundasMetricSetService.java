package com.projectrefocus.service.dundas.service;

import com.projectrefocus.service.dundas.dto.MetricSetDetails;
import com.projectrefocus.service.geography.enums.GeographyType;

public interface DundasMetricSetService {

    MetricSetDetails getMetricSetDetails(String metricSetId);

    String getMetricSetId(String categoryId, GeographyType geographyType);
}
