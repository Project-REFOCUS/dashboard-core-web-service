package com.projectrefocus.service.apha.service;

import com.projectrefocus.service.common.dto.MetricDto;
import com.projectrefocus.service.request.enums.DataOrientation;

import java.util.Date;
import java.util.List;

public interface AphaService {

    List<MetricDto> getData(List<String> states, DataOrientation orientation, Date startDate);
}
