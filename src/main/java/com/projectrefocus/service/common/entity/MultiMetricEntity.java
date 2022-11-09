package com.projectrefocus.service.common.entity;

import com.projectrefocus.service.calendar.entity.CalendarDateEntity;
import com.projectrefocus.service.common.dto.MetricDto;
import com.projectrefocus.service.request.enums.SubCategory;

import java.util.Date;

public interface MultiMetricEntity {
    Integer getValue(SubCategory ...categories);
    CalendarDateEntity getDate();
    MetricDto toDto(SubCategory ...categories);
}
