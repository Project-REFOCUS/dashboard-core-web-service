package com.projectrefocus.service.common.utils;

import com.projectrefocus.service.common.dto.MetricDto;
import com.projectrefocus.service.common.entity.MultiMetricEntity;
import com.projectrefocus.service.request.enums.SubCategory;

import java.util.*;
import java.util.stream.Collectors;

public class MultiMetricTransformer extends MetricTransformer {

    public static Map<Date, Integer> aggregationByDate(List<? extends MultiMetricEntity> entityList, SubCategory...categories) {
        Map<Date, Integer> aggregatedByDate = new HashMap<>();
        entityList.forEach(entity -> {
            Date date = entity.getDate().getDate();
            Integer aggregatedAmount = aggregatedByDate.getOrDefault(date, 0);
            aggregatedByDate.put(date, aggregatedAmount + entity.getValue(categories));
        });
        return aggregatedByDate;
    }

    public static List<MetricDto> toCumulative(List<? extends MultiMetricEntity> entityList, Integer initialAggregate, SubCategory ...categories) {
        List<Date> sortedListOfUniqueDates = sortedListOfUniqueDates(entityList.stream().map(e -> e.getDate().getDate()).collect(Collectors.toList()));
        Map<Date, Integer> aggregatedByDate = aggregationByDate(entityList, categories);
        return toAccumulatedMetricDtoList(sortedListOfUniqueDates, aggregatedByDate, initialAggregate);
    }

    public static List<MetricDto> toDaily(List<? extends MultiMetricEntity> entityList, SubCategory ...categories) {
        List<Date> sortedListOfUniqueDates = sortedListOfUniqueDates(entityList.stream().map(e -> e.getDate().getDate()).collect(Collectors.toList()));
        Map<Date, Integer> aggregatedByDate = aggregationByDate(entityList, categories);
        return toMetricDtoList(sortedListOfUniqueDates, aggregatedByDate);
    }

    public static List<MetricDto> toDailyPer100K(List<? extends MultiMetricEntity> entityList, Integer denominator, SubCategory...categories) {
        List<MetricDto> metrics = toDaily(entityList, categories);
        return metrics.stream().peek(metric -> metric.setValue(calculateValuePer100K(metric.getValue(), denominator))).collect(Collectors.toList());
    }

    public static List<MetricDto> toDailyNDayAverage(List<? extends MultiMetricEntity> entityList, int nDay, SubCategory ...categories) {
        List<MetricDto> metricList = toDaily(entityList, categories);
        List<Integer> values = new LinkedList<>();
        int index = 0;
        int sum = 0;

        List<MetricDto> results = new ArrayList<>();
        for (MetricDto metric : metricList) {
            if (index < nDay) {
                values.add(metric.getValue());
                sum += metric.getValue();
                index++;
            }
            else {
                MetricDto dto = new MetricDto();
                dto.setDate(metric.getDate());
                dto.setValue(sum / nDay);
                results.add(dto);

                values.add(metric.getValue());
                sum += metric.getValue();
                sum -= values.get(0);
                values.remove(0);
            }
        }
        return results;
    }

    public static List<MetricDto> toDailyNDayAveragePer100K(List<? extends MultiMetricEntity> entityList, int nDay, int denominator) {
        return toDailyNDayAverage(entityList, nDay)
                .stream()
                .peek(dto -> dto.setValue(calculateValuePer100K(dto.getValue(), denominator)))
                .collect(Collectors.toList());
    }
}
