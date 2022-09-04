package com.projectrefocus.service.covid.utils;

import com.projectrefocus.service.covid.dto.CovidMetricDto;
import com.projectrefocus.service.covid.entity.CovidStateCasesEntity;
import com.projectrefocus.service.covid.entity.CovidStateDeathsEntity;
import com.projectrefocus.service.covid.entity.CovidStateTestsEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Transformer {

    private static final int PER_100K = 100000;

    private static List<Date> sortedListOfUniqueDates(List<Date> dateList) {
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

    public static List<CovidMetricDto> toCumulativeCases(List<CovidStateCasesEntity> casesEntityList, Integer initialAggregate) {
        List<Date> sortedListOfUniqueDates = sortedListOfUniqueDates(casesEntityList.stream().map(e -> e.getDate().getDate()).collect(Collectors.toList()));
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

    public static List<CovidMetricDto> toDailyCases(List<CovidStateCasesEntity> casesEntityList) {
        List<Date> sortedListOfUniqueDates = sortedListOfUniqueDates(casesEntityList.stream().map(e -> e.getDate().getDate()).collect(Collectors.toList()));
        Map<Date, Integer> aggregatedByDate = new HashMap<>();

        casesEntityList.forEach(entity -> {
            Date date = entity.getDate().getDate();
            Integer aggregatedAmount = aggregatedByDate.getOrDefault(date, 0);
            aggregatedByDate.put(date, aggregatedAmount + entity.getCases());
        });

        List<CovidMetricDto> results = new ArrayList<>();
        for (Date uniqueDate : sortedListOfUniqueDates) {
            CovidMetricDto metricDto = new CovidMetricDto();
            metricDto.setValue(aggregatedByDate.get(uniqueDate));
            metricDto.setDate(uniqueDate);

            results.add(metricDto);
        }

        return results;
    }

    public static List<CovidMetricDto> toDailyCasesPer100K(List<CovidStateCasesEntity> entityList, Integer denominator) {
        return toDailyCases(entityList).stream().peek(dto -> dto.setValue((dto.getValue() / denominator) * PER_100K)).collect(Collectors.toList());
    }

    public static List<CovidMetricDto> toDailyCasesNDayAverage(List<CovidStateCasesEntity> entityList, int nDay) {
        List<CovidMetricDto> dailyCases = Transformer.toDailyCases(entityList);
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

    public static List<CovidMetricDto> toCumulativeDeaths(List<CovidStateDeathsEntity> entityList, Integer initialAggregate) {
        List<Date> sortedListOfUniqueDates = sortedListOfUniqueDates(entityList.stream().map(e -> e.getDate().getDate()).collect(Collectors.toList()));
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

    public static List<CovidMetricDto> toDailyDeaths(List<CovidStateDeathsEntity> entityList) {
        List<Date> sortedListOfUniqueDates = sortedListOfUniqueDates(entityList.stream().map(e -> e.getDate().getDate()).collect(Collectors.toList()));
        Map<Date, Integer> aggregatedByDate = new HashMap<>();

        entityList.forEach(entity -> {
            Date date = entity.getDate().getDate();
            Integer aggregatedAmount = aggregatedByDate.getOrDefault(date, 0);
            aggregatedByDate.put(date, aggregatedAmount + entity.getDeaths());
        });

        return sortedListOfUniqueDates.stream().map(date -> {
            CovidMetricDto metricDto = new CovidMetricDto();
            metricDto.setValue(aggregatedByDate.get(date));
            metricDto.setDate(date);

            return metricDto;
        }).collect(Collectors.toList());
    }

    public static List<CovidMetricDto> toDailyDeathsPer100K(List<CovidStateDeathsEntity> entityList, Integer denominator) {
        return toDailyDeaths(entityList).stream().peek(dto -> dto.setValue((dto.getValue() / denominator) * PER_100K)).collect(Collectors.toList());
    }

    public static List<CovidMetricDto> toDailyDeathsNDayAverage(List<CovidStateDeathsEntity> entityList, int nDay) {
        List<CovidMetricDto> dailyDeaths = Transformer.toDailyDeaths(entityList);
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

        return sortedListOfUniqueDates.stream().map(date -> {
            CovidMetricDto metricDto = new CovidMetricDto();
            metricDto.setValue(aggregatedByDate.get(date));
            metricDto.setDate(date);

            return metricDto;
        }).collect(Collectors.toList());
    }

    public static List<CovidMetricDto> toDailyTestsPer100K(List<CovidStateTestsEntity> entityList, Integer denominator) {
        return toDailyTests(entityList).stream().peek(dto -> dto.setValue((dto.getValue() / denominator) * PER_100K)).collect(Collectors.toList());
    }

    public static List<CovidMetricDto> toDailyTestsNDayAverage(List<CovidStateTestsEntity> entityList, int nDay) {
        List<CovidMetricDto> dailyTests = Transformer.toDailyTests(entityList);
        List<Integer> casesValues = new LinkedList<>();
        int index = 0;
        int sum = 0;

        List<CovidMetricDto> results = new ArrayList<>();
        for (CovidMetricDto dailyTest : dailyTests) {
            if (index < nDay) {
                casesValues.add(dailyTest.getValue());
                sum += dailyTest.getValue();
                index++;
            }
            else {
                CovidMetricDto metricDto = new CovidMetricDto();
                metricDto.setDate(dailyTest.getDate());
                metricDto.setValue(sum / nDay);
                results.add(metricDto);

                casesValues.add(dailyTest.getValue());
                sum += dailyTest.getValue();
                sum -= casesValues.get(0);
                casesValues.remove(0);
            }
        }
        return results;
    }
}
