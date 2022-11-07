package com.projectrefocus.service.common.entity;

import com.projectrefocus.service.calendar.entity.CalendarDateEntity;
import com.projectrefocus.service.common.dto.MetricDto;

public interface MetricEntity {

    Integer getValue();

    CalendarDateEntity getDate();

    MetricDto toDto();
}
