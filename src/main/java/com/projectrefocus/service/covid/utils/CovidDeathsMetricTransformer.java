package com.projectrefocus.service.covid.utils;

import com.projectrefocus.service.common.dto.MetricDto;
import com.projectrefocus.service.covid.entity.CovidStateDeathsEntity;

import java.util.*;
import java.util.stream.Collectors;

public class CovidDeathsMetricTransformer extends CovidMetricTransformer {

    public static List<MetricDto> toMortalityRate(List<CovidStateDeathsEntity> entityList, Integer initialAggregate, Integer denominator) {
        return toCumulative(entityList, initialAggregate)
                .stream()
                .peek(dto -> dto.setValue(calculateValuePer100K(dto.getValue(), denominator)))
                .collect(Collectors.toList());
    }

    public static List<MetricDto> toMortalityRateOverNDays(List<CovidStateDeathsEntity> entityList, Integer initialAggregate, Integer denominator, Integer nDays) {
        List<MetricDto> deaths = toMortalityRate(entityList, initialAggregate, denominator);
        return toNDayAverageMetricDtoList(deaths, nDays);
    }

    public static List<MetricDto> toMortalityRatePercentChange(List<CovidStateDeathsEntity> entityList, Integer initialAggregate, Integer denominator) {
        List<MetricDto> deaths = toMortalityRate(entityList, initialAggregate, denominator);
        return toPercentChangeInValueV2(deaths);
    }

    public static List<MetricDto> toMortalityRatePercentChangeOverNDays(List<CovidStateDeathsEntity> entityList, Integer initialAggregate, Integer denominator, Integer nDays) {
        List<MetricDto> deaths = toMortalityRateOverNDays(entityList, initialAggregate, denominator, nDays);
        return toPercentChangeInValueV2(deaths);
    }
}
