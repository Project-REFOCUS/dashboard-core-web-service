package com.projectrefocus.service.covid.utils;

import com.projectrefocus.service.common.dto.MetricDto;
import com.projectrefocus.service.common.entity.MetricEntity;
import com.projectrefocus.service.common.utils.MetricTransformer;
import com.projectrefocus.service.covid.dto.CovidMetricDto;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.*;
import java.util.stream.Collectors;

public class CovidMetricTransformer extends MetricTransformer {

    private static final int PER_100K = 100000;

    protected static Map<Date, Integer> aggregateByDate(List<? extends MetricEntity> entityList) {
        Map<Date, Integer> aggregatedByDate = new HashMap<>();
        entityList.forEach(entity -> {
            Date date = entity.getDate().getDate();
            Integer aggregatedAmount = aggregatedByDate.getOrDefault(date, 0);
            aggregatedByDate.put(date, aggregatedAmount + entity.getValue());
        });
        return aggregatedByDate;
    }

    public static List<MetricDto> toCumulative(List<? extends MetricEntity> entityList, Integer initialAggregate) {
        List<Date> sortedListOfUniqueDates = sortedListOfUniqueDates(entityList.stream().map(e -> e.getDate().getDate()).collect(Collectors.toList()));
        Map<Date, Integer> aggregatedByDate = aggregateByDate(entityList);
        return toAccumulatedMetricDtoList(sortedListOfUniqueDates, aggregatedByDate, initialAggregate);
    }

    public static List<MetricDto> toDaily(List<? extends MetricEntity> entityList) {
        List<Date> sortedListOfUniqueDates = sortedListOfUniqueDates(entityList.stream().map(e -> e.getDate().getDate()).collect(Collectors.toList()));
        return toMetricDtoList(sortedListOfUniqueDates, aggregateByDate(entityList));
    }

    public static List<MetricDto> toWeekly(List<? extends MetricEntity> entityList) {
        return entityList.stream().map(MetricEntity::toDto).collect(Collectors.toList());
    }

    public static List<MetricDto> toMonthly(List<? extends MetricEntity> entityList) {
        return entityList.stream().map(MetricEntity::toDto).collect(Collectors.toList());
    }

    public static List<MetricDto> toDailyPer100K(List<? extends MetricEntity> entityList, Integer denominator) {
        return toDaily(entityList).stream().peek(dto -> dto.setValue(calculateValuePer100K(dto.getValue(), denominator))).collect(Collectors.toList());
    }

    public static List<MetricDto> toWeeklyPer100K(List<? extends MetricEntity> entityList, Integer denominator) {
        return entityList.stream().map(MetricEntity::toDto).peek(dto -> dto.setValue(calculateValuePer100K(dto.getValue(), denominator))).collect(Collectors.toList());
    }

    public static List<MetricDto> toMonthlyPer100K(List<? extends MetricEntity> entityList, Integer denominator) {
        return entityList.stream().map(MetricEntity::toDto).peek(dto -> dto.setValue(calculateValuePer100K(dto.getValue(), denominator))).collect(Collectors.toList());
    }

    public static List<MetricDto> toDailyNDayAverage(List<? extends MetricEntity> entityList, int nDay) {
        List<MetricDto> metrics = toDaily(entityList);
        List<Integer> values = new LinkedList<>();
        int index = 0;
        int sum = 0;

        List<MetricDto> results = new ArrayList<>();
        for (MetricDto metric : metrics) {
            if (index < nDay) {
                values.add(metric.getValue());
                sum += metric.getValue();
                index++;
            }
            else {
                MetricDto metricDto = new MetricDto();
                metricDto.setDate(metric.getDate());
                metricDto.setValue(sum / nDay);
                results.add(metricDto);

                values.add(metric.getValue());
                sum += metric.getValue();
                sum -= values.get(0);
                values.remove(0);
            }
        }
        return results;
    }

    public static List<MetricDto> toDailyNDayAveragePer100K(List<? extends MetricEntity> entityList, int nDay, Integer denominator) {
        return toDailyNDayAverage(entityList, nDay)
                .stream()
                .peek(dto -> dto.setValue(calculateValuePer100K(dto.getValue(), denominator)))
                .collect(Collectors.toList());
    }

    public static List<MetricDto> toDailyPercentChangeInNDayAverage(List<? extends MetricEntity> entityList, int nDay) {
        List<MetricDto> results = toDailyNDayAverage(entityList, nDay);
        return toPercentChangeInValue(results);
    }

    protected static List<MetricDto> toNDayAverageMetricDtoList(List<MetricDto> dtoList, int nDay) {
        List<Integer> values = new LinkedList<>();
        int index = 0;
        int sum = 0;

        List<MetricDto> results = new ArrayList<>();
        for (MetricDto dto : dtoList) {
            if (index < nDay) {
                values.add(dto.getValue());
                sum += dto.getValue();
                index++;
            }
            else {
                MetricDto metricDto = new MetricDto();
                metricDto.setDate(dto.getDate());
                metricDto.setValue(sum / nDay);
                results.add(metricDto);

                values.add(dto.getValue());
                sum += dto.getValue();
                sum -= values.get(0);
                values.remove(0);
            }
        }
        return results;
    }

    protected static Integer percentage(Integer value, Integer denominator) {
        BigDecimal decimalValue = new BigDecimal(value);
        BigDecimal decimalDenominator = new BigDecimal(denominator);
        return decimalValue.divide(decimalDenominator, MathContext.DECIMAL32)
                .multiply(BigDecimal.TEN).multiply(BigDecimal.TEN)
                .intValue();
    }
}
