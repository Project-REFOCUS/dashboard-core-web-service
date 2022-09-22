package com.projectrefocus.service.covid.utils;

import com.projectrefocus.service.covid.dto.CovidMetricDto;
import com.projectrefocus.service.covid.entity.CovidStateCasesEntity;

import java.util.*;
import java.util.stream.Collectors;

public class CovidCasesMetricTransformer extends CovidMetricTransformer {

    public static List<CovidMetricDto> toCumulativeCases(List<CovidStateCasesEntity> entityList, Integer initialAggregate) {
        List<Date> sortedListOfUniqueDates = sortedListOfUniqueDates(entityList.stream().map(e -> e.getDate().getDate()).collect(Collectors.toList()));
        Map<Date, Integer> aggregatedByDate = new HashMap<>();

        entityList.forEach(entity -> {
            Date date = entity.getDate().getDate();
            Integer aggregatedAmount = aggregatedByDate.getOrDefault(date, 0);
            aggregatedByDate.put(date, aggregatedAmount + entity.getCases());
        });

        return toAccumulatedCovidMetricDtoList(sortedListOfUniqueDates, aggregatedByDate, initialAggregate);
    }

    public static List<CovidMetricDto> toDailyCases(List<CovidStateCasesEntity> entityList) {
        List<Date> sortedListOfUniqueDates = sortedListOfUniqueDates(entityList.stream().map(e -> e.getDate().getDate()).collect(Collectors.toList()));
        Map<Date, Integer> aggregatedByDate = new HashMap<>();

        entityList.forEach(entity -> {
            Date date = entity.getDate().getDate();
            Integer aggregatedAmount = aggregatedByDate.getOrDefault(date, 0);
            aggregatedByDate.put(date, aggregatedAmount + entity.getCases());
        });

        return toCovidMetricDtoList(sortedListOfUniqueDates, aggregatedByDate);
    }

    public static List<CovidMetricDto> toDailyCasesPer100K(List<CovidStateCasesEntity> entityList, Integer denominator) {
        return toDailyCases(entityList).stream().peek(dto -> dto.setValue(calculateValuePer100K(dto.getValue(), denominator))).collect(Collectors.toList());
    }

    public static List<CovidMetricDto> toDailyCasesNDayAverage(List<CovidStateCasesEntity> entityList, int nDay) {
        List<CovidMetricDto> dailyCases = toDailyCases(entityList);
        List<Integer> casesValues = new LinkedList<>();
        int index = 0;
        int sum = 0;

        List<CovidMetricDto> results = new ArrayList<>();
        for (CovidMetricDto dailyCase : dailyCases) {
            if (index < nDay) {
                casesValues.add(dailyCase.getValue());
                sum += dailyCase.getValue();
                index++;
            }
            else {
                CovidMetricDto metricDto = new CovidMetricDto();
                metricDto.setDate(dailyCase.getDate());
                metricDto.setValue(sum / nDay);
                results.add(metricDto);

                casesValues.add(dailyCase.getValue());
                sum += dailyCase.getValue();
                sum -= casesValues.get(0);
                casesValues.remove(0);
            }
        }
        return results;
    }

    public static List<CovidMetricDto> toDailyCasesNDayAveragePer100K(List<CovidStateCasesEntity> entityList, int nDay, Integer denominator) {
        return toDailyCasesNDayAverage(entityList, nDay)
                .stream()
                .peek(dto -> dto.setValue(calculateValuePer100K(dto.getValue(), denominator)))
                .collect(Collectors.toList());
    }

    public static List<CovidMetricDto> toDailyCasesPercentChangeInNDayAverage(List<CovidStateCasesEntity> entityList, int nDay) {
        List<CovidMetricDto> results = toDailyCasesNDayAverage(entityList, nDay);
        return toPercentChangeInValue(results);
    }
}
