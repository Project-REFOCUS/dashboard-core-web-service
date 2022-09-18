package com.projectrefocus.service.covid.utils;

import com.projectrefocus.service.covid.dto.CovidMetricDto;
import com.projectrefocus.service.covid.entity.CovidStateTestsEntity;

import java.util.*;
import java.util.stream.Collectors;

public class CovidTestsMetricTransformer extends CovidMetricTransformer {

    public static List<CovidMetricDto> toCumulativeTests(List<CovidStateTestsEntity> entityList, Integer initialAggregate) {
        List<Date> sortedListOfUniqueDates = sortedListOfUniqueDates(entityList.stream().map(e -> e.getDate().getDate()).collect(Collectors.toList()));
        Map<Date, Integer> aggregatedByDate = new HashMap<>();

        entityList.forEach(entity -> {
            Date date = entity.getDate().getDate();
            Integer aggregatedAmount = aggregatedByDate.getOrDefault(date, 0);
            aggregatedByDate.put(date, aggregatedAmount + entity.getTests());
        });

        List<CovidMetricDto> results = new ArrayList<>();
        Integer accumulation = initialAggregate;
        for (Date uniqueDate : sortedListOfUniqueDates) {
            CovidMetricDto metricDto = new CovidMetricDto();
            accumulation += aggregatedByDate.get(uniqueDate);
            metricDto.setValue(accumulation);
            metricDto.setDate(uniqueDate);

            results.add(metricDto);
        }

        return results;
    }

    public static List<CovidMetricDto> toDailyTests(List<CovidStateTestsEntity> entityList) {
        List<Date> sortedListOfUniqueDates = sortedListOfUniqueDates(entityList.stream().map(e -> e.getDate().getDate()).collect(Collectors.toList()));
        Map<Date, Integer> aggregatedByDate = new HashMap<>();

        entityList.forEach(entity -> {
            Date date = entity.getDate().getDate();
            Integer aggregatedAmount = aggregatedByDate.getOrDefault(date, 0);
            aggregatedByDate.put(date, aggregatedAmount + entity.getTests());
        });

        return toCovidMetricDtoList(sortedListOfUniqueDates, aggregatedByDate);
    }

    public static List<CovidMetricDto> toDailyTestsPer100K(List<CovidStateTestsEntity> entityList, Integer denominator) {
        return toDailyTests(entityList).stream().peek(dto -> dto.setValue(calculateValuePer100K(dto.getValue(), denominator))).collect(Collectors.toList());
    }

    public static List<CovidMetricDto> toDailyTestsNDayAverage(List<CovidStateTestsEntity> entityList, int nDay) {
        List<CovidMetricDto> dailyTests = toDailyTests(entityList);
        List<Integer> values = new LinkedList<>();
        int index = 0;
        int sum = 0;

        List<CovidMetricDto> results = new LinkedList<>();
        for (CovidMetricDto test : dailyTests) {
            if (index < nDay) {
                values.add(test.getValue());
                sum += test.getValue();
                index++;
            }
            else {
                CovidMetricDto metricDto = new CovidMetricDto();
                metricDto.setDate(test.getDate());
                metricDto.setValue(sum / nDay);
                results.add(metricDto);

                values.add(test.getValue());
                sum += test.getValue();
                sum -= values.get(0);
                values.remove(0);
            }
        }
        return results;
    }

    public static List<CovidMetricDto> toDailyTestsNDayAveragePer100K(List<CovidStateTestsEntity> entityList, int nDay, int denominator) {
        return toDailyTestsNDayAverage(entityList, nDay)
                .stream()
                .peek(dto -> dto.setValue(calculateValuePer100K(dto.getValue(), denominator)))
                .collect(Collectors.toList());
    }
}
