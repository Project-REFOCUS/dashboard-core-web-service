package com.projectrefocus.service.common.entity;

import com.projectrefocus.service.calendar.entity.CalendarDateEntity;
import com.projectrefocus.service.common.dto.MetricDto;
import com.projectrefocus.service.request.enums.DataOrientation;

public interface MetricEntity {

    Integer getValue();

    CalendarDateEntity getDate();

    MetricDto toDto();
}
