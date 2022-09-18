package com.projectrefocus.service.covid.utils;

import com.projectrefocus.service.covid.dto.CovidMetricDto;
import com.projectrefocus.service.covid.entity.CovidStateDeathsEntity;

import java.util.*;
import java.util.stream.Collectors;

public class CovidDeathsMetricTransformer extends CovidMetricTransformer {

    public static List<CovidMetricDto> toCumulativeDeaths(List<CovidStateDeathsEntity> entityList, Integer initialAggregate) {
        List<Date> sortedListOfUniqueDates = sortedListOfUniqueDates(entityList.stream().map(e -> e.getDate().getDate()).collect(Collectors.toList()));
        Map<Date, Integer> aggregatedByDate = new HashMap<>();

        entityList.forEach(entity -> {
            Date date = entity.getDate().getDate();
            Integer aggregatedAmount = aggregatedByDate.getOrDefault(date, 0);
            aggregatedByDate.put(date, aggregatedAmount + entity.getDeaths());
        });

        return toCovidMetricDtoList(sortedListOfUniqueDates, aggregatedByDate);
    }

    public static List<CovidMetricDto> toDailyDeaths(List<CovidStateDeathsEntity> entityList) {
        List<Date> sortedListOfUniqueDates = sortedListOfUniqueDates(entityList.stream().map(e -> e.getDate().getDate()).collect(Collectors.toList()));
        Map<Date, Integer> aggregatedByDate = new HashMap<>();

        entityList.forEach(entity -> {
            Date date = entity.getDate().getDate();
            Integer aggregatedAmount = aggregatedByDate.getOrDefault(date, 0);
            aggregatedByDate.put(date, aggregatedAmount + entity.getDeaths());
        });

        return toCovidMetricDtoList(sortedListOfUniqueDates, aggregatedByDate);
    }

    public static List<CovidMetricDto> toDailyDeathsNDayAverage(List<CovidStateDeathsEntity> entityList, int nDay) {
        List<CovidMetricDto> dailyDeaths = toDailyDeaths(entityList);
        List<Integer> casesValues = new LinkedList<>();
        int index = 0;
        int sum = 0;

        List<CovidMetricDto> results = new ArrayList<>();
        for (CovidMetricDto dailyDeath : dailyDeaths) {
            if (index < nDay) {
                casesValues.add(dailyDeath.getValue());
                sum += dailyDeath.getValue();
                index++;
            }
            else {
                CovidMetricDto metricDto = new CovidMetricDto();
                metricDto.setDate(dailyDeath.getDate());
                metricDto.setValue(sum / nDay);
                results.add(metricDto);

                casesValues.add(dailyDeath.getValue());
                sum += dailyDeath.getValue();
                sum -= casesValues.get(0);
                casesValues.remove(0);
            }
        }
        return results;
    }

    public static List<CovidMetricDto> toDailyDeathsNDayAveragePer100K(List<CovidStateDeathsEntity> entityList, int nDay, Integer denominator) {
        return CovidDeathsMetricTransformer.toDailyDeathsNDayAverage(entityList, nDay)
                .stream()
                .peek(dto -> dto.setValue(calculateValuePer100K(dto.getValue(), denominator)))
                .collect(Collectors.toList());
    }

    public static List<CovidMetricDto> toDailyDeathsPercentChangeInNDayAverage(List<CovidStateDeathsEntity> entityList, int nDay) {
        List<CovidMetricDto> results = toDailyDeathsNDayAverage(entityList, nDay);
        return toPercentChangeInValue(results);
    }
}
