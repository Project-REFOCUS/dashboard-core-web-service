package com.projectrefocus.service.common.utils;

import com.projectrefocus.service.common.dto.MetricDto;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.*;
import java.util.stream.Collectors;

public class MetricTransformer {

    private static final int PER_100K = 100000;

    public static List<Date> sortedListOfUniqueDates(List<Date> dateList) {
        List<Date> listOfUniqueDates = new ArrayList<>();
        Set<Date> uniqueSetOfDates = new HashSet<>();
        dateList.forEach(date -> {
            if (!uniqueSetOfDates.contains(date)) {
                listOfUniqueDates.add(date);
                uniqueSetOfDates.add(date);
            }
        });
        return listOfUniqueDates.stream().sorted().collect(Collectors.toList());
    }

    public static List<MetricDto> toAccumulatedMetricDtoList(List<Date> dates, Map<Date, Integer> aggregationByDate, Integer initialAggregate) {
        List<MetricDto> results = new ArrayList<>();
        Integer accumulation = initialAggregate;
        for (Date uniqueDate : dates) {
            MetricDto metricDto = new MetricDto();
            accumulation += aggregationByDate.getOrDefault(uniqueDate, 0);
            metricDto.setValue(accumulation);
            metricDto.setDate(uniqueDate);

            results.add(metricDto);
        }

        return results;
    }

    public static List<MetricDto> toMetricDtoList(List<Date> dates, Map<Date, Integer> aggregationByDate) {
        return dates.stream().map(date -> {
            MetricDto metricDto = new MetricDto();
            metricDto.setValue(aggregationByDate.get(date));
            metricDto.setDate(date);

            return metricDto;
        }).collect(Collectors.toList());
    }

    protected static Integer calculateValuePer100K(Integer value, Integer denominator) {
        BigDecimal decimalValue = new BigDecimal(value);
        BigDecimal decimalDenominator = new BigDecimal(denominator);
        BigDecimal result = decimalValue.divide(decimalDenominator, MathContext.DECIMAL32).multiply(new BigDecimal(PER_100K));

        return result.intValue();
    }

    private static Integer percentChange(int initial, int target) {
        if (initial == 0) {
            return initial;
        }
        BigDecimal targetMinusInitialDecimal = new BigDecimal(target - initial);
        BigDecimal initialDecimal = new BigDecimal(initial);
        BigDecimal result = targetMinusInitialDecimal.divide(initialDecimal, MathContext.DECIMAL32)
                .multiply(BigDecimal.TEN).multiply(BigDecimal.TEN);
        return result.intValue();
    }

    protected static List<MetricDto> toPercentChangeInValue(List<MetricDto> metricDtoList) {
        List<MetricDto> results = new ArrayList<>();
        int previousValue = metricDtoList.remove(0).getValue();
        for (MetricDto dto : metricDtoList) {
            int currentValue = dto.getValue();
            dto.setValue(percentChange(previousValue, dto.getValue()));
            results.add(dto);

            previousValue = currentValue;
        }

        return results;
    }
}
