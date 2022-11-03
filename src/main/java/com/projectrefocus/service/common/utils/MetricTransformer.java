package com.projectrefocus.service.common.utils;

import com.projectrefocus.service.common.dto.MetricDto;

import java.util.*;
import java.util.stream.Collectors;

public class MetricTransformer {

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
}
