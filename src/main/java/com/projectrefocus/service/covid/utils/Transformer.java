package com.projectrefocus.service.covid.utils;

import com.projectrefocus.service.covid.dto.CovidMetricDto;
import com.projectrefocus.service.covid.entity.CovidStateCasesEntity;
import com.projectrefocus.service.covid.entity.CovidStateDeathsEntity;
import com.projectrefocus.service.covid.entity.CovidStateTestsEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Transformer {

    public static List<CovidMetricDto> toCumulativeCases(List<CovidStateCasesEntity> casesEntityList, Integer initialAggregate) {
        List<Date> listOfUniqueDates = new ArrayList<>();
        Set<Date> uniqueSetOfDates = new HashSet<>();

        casesEntityList.forEach(entity -> {
            Date date = entity.getDate().getDate();
            if (!uniqueSetOfDates.contains(date)) {
                listOfUniqueDates.add(date);
                uniqueSetOfDates.add(date);
            }
        });

        List<Date> sortedListOfUniqueDates = listOfUniqueDates.stream().sorted().collect(Collectors.toList());
        Map<Date, Integer> aggregatedByDate = new HashMap<>();

        casesEntityList.forEach(entity -> {
            Date date = entity.getDate().getDate();
            Integer aggregatedAmount = aggregatedByDate.getOrDefault(date, 0);
            aggregatedByDate.put(date, aggregatedAmount + entity.getCases());
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

    public static List<CovidMetricDto> toCumulativeDeaths(List<CovidStateDeathsEntity> entityList, Integer initialAggregate) {
        List<Date> listOfUniqueDates = new ArrayList<>();
        Set<Date> uniqueSetOfDates = new HashSet<>();

        entityList.forEach(entity -> {
            Date date = entity.getDate().getDate();
            if (!uniqueSetOfDates.contains(date)) {
                listOfUniqueDates.add(date);
                uniqueSetOfDates.add(date);
            }
        });

        List<Date> sortedListOfUniqueDates = listOfUniqueDates.stream().sorted().collect(Collectors.toList());
        Map<Date, Integer> aggregatedByDate = new HashMap<>();

        entityList.forEach(entity -> {
            Date date = entity.getDate().getDate();
            Integer aggregatedAmount = aggregatedByDate.getOrDefault(date, 0);
            aggregatedByDate.put(date, aggregatedAmount + entity.getDeaths());
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

    public static List<CovidMetricDto> toCumulativeTests(List<CovidStateTestsEntity> entityList, Integer initialAggregate) {
        List<Date> listOfUniqueDates = new ArrayList<>();
        Set<Date> uniqueSetOfDates = new HashSet<>();

        entityList.forEach(entity -> {
            Date date = entity.getDate().getDate();
            if (!uniqueSetOfDates.contains(date)) {
                listOfUniqueDates.add(date);
                uniqueSetOfDates.add(date);
            }
        });

        List<Date> sortedListOfUniqueDates = listOfUniqueDates.stream().sorted().collect(Collectors.toList());
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
}
