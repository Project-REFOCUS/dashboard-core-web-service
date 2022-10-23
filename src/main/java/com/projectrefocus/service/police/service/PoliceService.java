package com.projectrefocus.service.police.service;

import com.projectrefocus.service.common.dto.MetricDto;
import com.projectrefocus.service.request.enums.DataOrientation;

import java.util.Date;
import java.util.List;

public interface PoliceService {

    List<MetricDto> getData(List<String> states, DataOrientation orientation, Date startDate);
}
