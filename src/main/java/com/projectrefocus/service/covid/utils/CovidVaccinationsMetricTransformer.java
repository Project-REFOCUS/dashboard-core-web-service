package com.projectrefocus.service.covid.utils;

import com.projectrefocus.service.common.dto.MetricDto;
import com.projectrefocus.service.common.entity.MultiMetricEntity;
import com.projectrefocus.service.common.utils.MultiMetricTransformer;
import com.projectrefocus.service.request.enums.SubCategory;

import java.util.List;

public class CovidVaccinationsMetricTransformer extends MultiMetricTransformer {

    public static List<MetricDto> toDailyPercentageChangeNDayAverage(List<? extends MultiMetricEntity> entityList, int nDay, SubCategory ...categories) {
        List<MetricDto> metrics = toDailyNDayAverage(entityList, nDay, categories);
        return toPercentChangeInValue(metrics);
    }
}
