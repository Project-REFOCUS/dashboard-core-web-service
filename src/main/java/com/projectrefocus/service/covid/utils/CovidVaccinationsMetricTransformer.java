package com.projectrefocus.service.covid.utils;

import com.projectrefocus.service.covid.dto.CovidMetricDto;
import com.projectrefocus.service.covid.entity.CovidStateVaccinationsEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CovidVaccinationsMetricTransformer extends CovidMetricTransformer {

    public static List<CovidMetricDto> toCumulativeDistributedVaccinations(List<CovidStateVaccinationsEntity> entityList, Integer initialAggregate) {
        List<Date> sortedListOfUniqueDates = sortedListOfUniqueDates(entityList.stream().map(e -> e.getDate().getDate()).collect(Collectors.toList()));
        Map<Date, Integer> aggregatedByDate = new HashMap<>();

        entityList.forEach(entity -> {
            Date date = entity.getDate().getDate();
            Integer aggregatedAmount = aggregatedByDate.getOrDefault(date, 0);
            aggregatedByDate.put(date, aggregatedAmount + entity.getDistributed());
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

    public static List<CovidMetricDto> toCumulativeAdministeredVaccinations(List<CovidStateVaccinationsEntity> entityList, Integer initialAggregate) {
        List<Date> sortedListOfUniqueDates = sortedListOfUniqueDates(entityList.stream().map(e -> e.getDate().getDate()).collect(Collectors.toList()));
        Map<Date, Integer> aggregatedByDate = new HashMap<>();

        entityList.forEach(entity -> {
            Date date = entity.getDate().getDate();
            Integer aggregatedAmount = aggregatedByDate.getOrDefault(date, 0);
            aggregatedByDate.put(date, aggregatedAmount + entity.getAdministered());
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

    public static List<CovidMetricDto> toCumulativeAdministeredOneDoseVaccinations(List<CovidStateVaccinationsEntity> entityList, Integer initialAggregate) {
        List<Date> sortedListOfUniqueDates = sortedListOfUniqueDates(entityList.stream().map(e -> e.getDate().getDate()).collect(Collectors.toList()));
        Map<Date, Integer> aggregatedByDate = new HashMap<>();

        entityList.forEach(entity -> {
            Date date = entity.getDate().getDate();
            Integer aggregatedAmount = aggregatedByDate.getOrDefault(date, 0);
            aggregatedByDate.put(date, aggregatedAmount + entity.getAdministeredOneDose());
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

    public static List<CovidMetricDto> toCumulativeAdministeredTwoDoseVaccinations(List<CovidStateVaccinationsEntity> entityList, Integer initialAggregate) {
        List<Date> sortedListOfUniqueDates = sortedListOfUniqueDates(entityList.stream().map(e -> e.getDate().getDate()).collect(Collectors.toList()));
        Map<Date, Integer> aggregatedByDate = new HashMap<>();

        entityList.forEach(entity -> {
            Date date = entity.getDate().getDate();
            Integer aggregatedAmount = aggregatedByDate.getOrDefault(date, 0);
            aggregatedByDate.put(date, aggregatedAmount + entity.getAdministeredTwoDose());
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

    public static List<CovidMetricDto> toDailyDistributedVaccinations(List<CovidStateVaccinationsEntity> entityList) {
        List<Date> sortedListOfUniqueDates = sortedListOfUniqueDates(entityList.stream().map(e -> e.getDate().getDate()).collect(Collectors.toList()));
        Map<Date, Integer> aggregatedByDate = new HashMap<>();

        entityList.forEach(entity -> {
            Date date = entity.getDate().getDate();
            Integer aggregatedAmount = aggregatedByDate.getOrDefault(date, 0);
            aggregatedByDate.put(date, aggregatedAmount + entity.getDistributed());
        });

        return toCovidMetricDtoList(sortedListOfUniqueDates, aggregatedByDate);
    }

    public static List<CovidMetricDto> toDailyAdministeredVaccinations(List<CovidStateVaccinationsEntity> entityList) {
        List<Date> sortedListOfUniqueDates = sortedListOfUniqueDates(entityList.stream().map(e -> e.getDate().getDate()).collect(Collectors.toList()));
        Map<Date, Integer> aggregatedByDate = new HashMap<>();

        entityList.forEach(entity -> {
            Date date = entity.getDate().getDate();
            Integer aggregatedAmount = aggregatedByDate.getOrDefault(date, 0);
            aggregatedByDate.put(date, aggregatedAmount + entity.getAdministered());
        });

        return toCovidMetricDtoList(sortedListOfUniqueDates, aggregatedByDate);
    }

    public static List<CovidMetricDto> toDailyAdministeredOneDoseVaccinations(List<CovidStateVaccinationsEntity> entityList) {
        List<Date> sortedListOfUniqueDates = sortedListOfUniqueDates(entityList.stream().map(e -> e.getDate().getDate()).collect(Collectors.toList()));
        Map<Date, Integer> aggregatedByDate = new HashMap<>();

        entityList.forEach(entity -> {
            Date date = entity.getDate().getDate();
            Integer aggregatedAmount = aggregatedByDate.getOrDefault(date, 0);
            aggregatedByDate.put(date, aggregatedAmount + entity.getAdministeredOneDose());
        });

        return toCovidMetricDtoList(sortedListOfUniqueDates, aggregatedByDate);
    }

    public static List<CovidMetricDto> toDailyAdministeredTwoDoseVaccinations(List<CovidStateVaccinationsEntity> entityList) {
        List<Date> sortedListOfUniqueDates = sortedListOfUniqueDates(entityList.stream().map(e -> e.getDate().getDate()).collect(Collectors.toList()));
        Map<Date, Integer> aggregatedByDate = new HashMap<>();

        entityList.forEach(entity -> {
            Date date = entity.getDate().getDate();
            Integer aggregatedAmount = aggregatedByDate.getOrDefault(date, 0);
            aggregatedByDate.put(date, aggregatedAmount + entity.getAdministeredTwoDose());
        });

        return toCovidMetricDtoList(sortedListOfUniqueDates, aggregatedByDate);
    }


    public static List<CovidMetricDto> toDailyDistributedVaccinationsPer100K(List<CovidStateVaccinationsEntity> entityList, Integer denominator) {
        return toDailyDistributedVaccinations(entityList).stream().peek(dto -> dto.setValue(calculateValuePer100K(dto.getValue(), denominator))).collect(Collectors.toList());
    }

    public static List<CovidMetricDto> toDailyAdministeredVaccinationsPer100K(List<CovidStateVaccinationsEntity> entityList, Integer denominator) {
        return toDailyAdministeredVaccinations(entityList).stream().peek(dto -> dto.setValue(calculateValuePer100K(dto.getValue(), denominator))).collect(Collectors.toList());
    }

    public static List<CovidMetricDto> toDailyAdministeredOneDoseVaccinationsPer100K(List<CovidStateVaccinationsEntity> entityList, Integer denominator) {
        return toDailyAdministeredOneDoseVaccinations(entityList).stream().peek(dto -> dto.setValue(calculateValuePer100K(dto.getValue(), denominator))).collect(Collectors.toList());
    }

    public static List<CovidMetricDto> toDailyAdministeredTwoDoseVaccinationsPer100K(List<CovidStateVaccinationsEntity> entityList, Integer denominator) {
        return toDailyAdministeredTwoDoseVaccinations(entityList).stream().peek(dto -> dto.setValue(calculateValuePer100K(dto.getValue(), denominator))).collect(Collectors.toList());
    }

    public static List<CovidMetricDto> toDailyDistributedVaccinationsNDayAverage(List<CovidStateVaccinationsEntity> entityList, int nDay) {
        List<CovidMetricDto> dailyDistributedVaccinations = toDailyDistributedVaccinations(entityList);
        List<Integer> values = new LinkedList<>();
        int index = 0;
        int sum = 0;

        List<CovidMetricDto> results = new ArrayList<>();
        for (CovidMetricDto vaccination : dailyDistributedVaccinations) {
            if (index < nDay) {
                values.add(vaccination.getValue());
                sum += vaccination.getValue();
                index++;
            }
            else {
                CovidMetricDto metricDto = new CovidMetricDto();
                metricDto.setDate(vaccination.getDate());
                metricDto.setValue(sum / nDay);
                results.add(metricDto);

                values.add(vaccination.getValue());
                sum += vaccination.getValue();
                sum -= values.get(0);
                values.remove(0);
            }
        }
        return results;
    }

    public static List<CovidMetricDto> toDailyAdministeredVaccinationsNDayAverage(List<CovidStateVaccinationsEntity> entityList, int nDay) {
        List<CovidMetricDto> vaccinations = toDailyAdministeredVaccinations(entityList);
        List<Integer> values = new LinkedList<>();
        int index = 0;
        int sum = 0;

        List<CovidMetricDto> results = new ArrayList<>();
        for (CovidMetricDto vaccination : vaccinations) {
            if (index < nDay) {
                values.add(vaccination.getValue());
                sum += vaccination.getValue();
                index++;
            }
            else {
                CovidMetricDto metricDto = new CovidMetricDto();
                metricDto.setDate(vaccination.getDate());
                metricDto.setValue(sum / nDay);
                results.add(metricDto);

                values.add(vaccination.getValue());
                sum += vaccination.getValue();
                sum -= values.get(0);
                values.remove(0);
            }
        }
        return results;
    }

    public static List<CovidMetricDto> toDailyAdministeredOneDoseVaccinationsNDayAverage(List<CovidStateVaccinationsEntity> entityList, int nDay) {
        List<CovidMetricDto> vaccinations = toDailyAdministeredOneDoseVaccinations(entityList);
        List<Integer> values = new LinkedList<>();
        int index = 0;
        int sum = 0;

        List<CovidMetricDto> results = new ArrayList<>();
        for (CovidMetricDto vaccination : vaccinations) {
            if (index < nDay) {
                values.add(vaccination.getValue());
                sum += vaccination.getValue();
                index++;
            }
            else {
                CovidMetricDto metricDto = new CovidMetricDto();
                metricDto.setDate(vaccination.getDate());
                metricDto.setValue(sum / nDay);
                results.add(metricDto);

                values.add(vaccination.getValue());
                sum += vaccination.getValue();
                sum -= values.get(0);
                values.remove(0);
            }
        }
        return results;
    }

    public static List<CovidMetricDto> toDailyAdministeredTwoDoseVaccinationsNDayAverage(List<CovidStateVaccinationsEntity> entityList, int nDay) {
        List<CovidMetricDto> vaccinations = toDailyAdministeredTwoDoseVaccinations(entityList);
        List<Integer> values = new LinkedList<>();
        int index = 0;
        int sum = 0;

        List<CovidMetricDto> results = new ArrayList<>();
        for (CovidMetricDto vaccination : vaccinations) {
            if (index < nDay) {
                values.add(vaccination.getValue());
                sum += vaccination.getValue();
                index++;
            }
            else {
                CovidMetricDto metricDto = new CovidMetricDto();
                metricDto.setDate(vaccination.getDate());
                metricDto.setValue(sum / nDay);
                results.add(metricDto);

                values.add(vaccination.getValue());
                sum += vaccination.getValue();
                sum -= values.get(0);
                values.remove(0);
            }
        }
        return results;
    }
}
