package com.projectrefocus.service.covid.utils;

import com.projectrefocus.service.common.dto.MetricDto;
import com.projectrefocus.service.common.utils.MetricTransformer;
import com.projectrefocus.service.covid.entity.CovidBehindBarsEntity;

import java.util.*;
import java.util.stream.Collectors;

public class CovidBehindBarsMetricTransformer extends MetricTransformer {

    public static List<MetricDto> toCumulativeResidentCases(List<CovidBehindBarsEntity> entityList, Integer initialAggregate) {
        List<Date> sortedListOfUniqueDates = sortedListOfUniqueDates(entityList.stream().map(e -> e.getDate().getDate()).collect(Collectors.toList()));
        Map<Date, Integer> aggregatedByDate = new HashMap<>();

        entityList.forEach(entity -> {
            Date date = entity.getDate().getDate();
            Integer aggregatedAmount = aggregatedByDate.getOrDefault(date, 0);
            aggregatedByDate.put(date, aggregatedAmount + entity.getResidentCases());
        });

        return toAccumulatedMetricDtoList(sortedListOfUniqueDates, aggregatedByDate, initialAggregate);
    }

    public static List<MetricDto> toCumulativeStaffCases(List<CovidBehindBarsEntity> entityList, Integer initialAggregate) {
        List<Date> sortedListOfUniqueDates = sortedListOfUniqueDates(entityList.stream().map(e -> e.getDate().getDate()).collect(Collectors.toList()));
        Map<Date, Integer> aggregatedByDate = new HashMap<>();

        entityList.forEach(entity -> {
            Date date = entity.getDate().getDate();
            Integer aggregatedAmount = aggregatedByDate.getOrDefault(date, 0);
            aggregatedByDate.put(date, aggregatedAmount + entity.getStaffCases());
        });

        return toAccumulatedMetricDtoList(sortedListOfUniqueDates, aggregatedByDate, initialAggregate);
    }

    public static List<MetricDto> toCumulativeResidentDeaths(List<CovidBehindBarsEntity> entityList, Integer initialAggregate) {
        List<Date> sortedListOfUniqueDates = sortedListOfUniqueDates(entityList.stream().map(e -> e.getDate().getDate()).collect(Collectors.toList()));
        Map<Date, Integer> aggregatedByDate = new HashMap<>();

        entityList.forEach(entity -> {
            Date date = entity.getDate().getDate();
            Integer aggregatedAmount = aggregatedByDate.getOrDefault(date, 0);
            aggregatedByDate.put(date, aggregatedAmount + entity.getResidentDeaths());
        });

        return toAccumulatedMetricDtoList(sortedListOfUniqueDates, aggregatedByDate, initialAggregate);
    }

    public static List<MetricDto> toCumulativeStaffDeaths(List<CovidBehindBarsEntity> entityList, Integer initialAggregate) {
        List<Date> sortedListOfUniqueDates = sortedListOfUniqueDates(entityList.stream().map(e -> e.getDate().getDate()).collect(Collectors.toList()));
        Map<Date, Integer> aggregatedByDate = new HashMap<>();

        entityList.forEach(entity -> {
            Date date = entity.getDate().getDate();
            Integer aggregatedAmount = aggregatedByDate.getOrDefault(date, 0);
            aggregatedByDate.put(date, aggregatedAmount + entity.getStaffDeaths());
        });

        return toAccumulatedMetricDtoList(sortedListOfUniqueDates, aggregatedByDate, initialAggregate);
    }

    public static List<MetricDto> toCumulativeResidentTests(List<CovidBehindBarsEntity> entityList, Integer initialAggregate) {
        List<Date> sortedListOfUniqueDates = sortedListOfUniqueDates(entityList.stream().map(e -> e.getDate().getDate()).collect(Collectors.toList()));
        Map<Date, Integer> aggregatedByDate = new HashMap<>();

        entityList.forEach(entity -> {
            Date date = entity.getDate().getDate();
            Integer aggregatedAmount = aggregatedByDate.getOrDefault(date, 0);
            aggregatedByDate.put(date, aggregatedAmount + entity.getResidentTests());
        });

        return toAccumulatedMetricDtoList(sortedListOfUniqueDates, aggregatedByDate, initialAggregate);
    }

    public static List<MetricDto> toCumulativeResidentAdministeredOneDose(List<CovidBehindBarsEntity> entityList, Integer initialAggregate) {
        List<Date> sortedListOfUniqueDates = sortedListOfUniqueDates(entityList.stream().map(e -> e.getDate().getDate()).collect(Collectors.toList()));
        Map<Date, Integer> aggregatedByDate = new HashMap<>();

        entityList.forEach(entity -> {
            Date date = entity.getDate().getDate();
            Integer aggregatedAmount = aggregatedByDate.getOrDefault(date, 0);
            aggregatedByDate.put(date, aggregatedAmount + entity.getResidentAdministeredOneDose());
        });

        return toAccumulatedMetricDtoList(sortedListOfUniqueDates, aggregatedByDate, initialAggregate);
    }

    public static List<MetricDto> toCumulativeStaffAdministeredOneDose(List<CovidBehindBarsEntity> entityList, Integer initialAggregate) {
        List<Date> sortedListOfUniqueDates = sortedListOfUniqueDates(entityList.stream().map(e -> e.getDate().getDate()).collect(Collectors.toList()));
        Map<Date, Integer> aggregatedByDate = new HashMap<>();

        entityList.forEach(entity -> {
            Date date = entity.getDate().getDate();
            Integer aggregatedAmount = aggregatedByDate.getOrDefault(date, 0);
            aggregatedByDate.put(date, aggregatedAmount + entity.getStaffAdministeredOneDose());
        });

        return toAccumulatedMetricDtoList(sortedListOfUniqueDates, aggregatedByDate, initialAggregate);
    }

    public static List<MetricDto> toCumulativeResidentAdministeredTwoDose(List<CovidBehindBarsEntity> entityList, Integer initialAggregate) {
        List<Date> sortedListOfUniqueDates = sortedListOfUniqueDates(entityList.stream().map(e -> e.getDate().getDate()).collect(Collectors.toList()));
        Map<Date, Integer> aggregatedByDate = new HashMap<>();

        entityList.forEach(entity -> {
            Date date = entity.getDate().getDate();
            Integer aggregatedAmount = aggregatedByDate.getOrDefault(date, 0);
            aggregatedByDate.put(date, aggregatedAmount + entity.getResidentAdministeredTwoDose());
        });

        return toAccumulatedMetricDtoList(sortedListOfUniqueDates, aggregatedByDate, initialAggregate);
    }

    public static List<MetricDto> toCumulativeStaffAdministeredTwoDose(List<CovidBehindBarsEntity> entityList, Integer initialAggregate) {
        List<Date> sortedListOfUniqueDates = sortedListOfUniqueDates(entityList.stream().map(e -> e.getDate().getDate()).collect(Collectors.toList()));
        Map<Date, Integer> aggregatedByDate = new HashMap<>();

        entityList.forEach(entity -> {
            Date date = entity.getDate().getDate();
            Integer aggregatedAmount = aggregatedByDate.getOrDefault(date, 0);
            aggregatedByDate.put(date, aggregatedAmount + entity.getStaffAdministeredTwoDose());
        });

        return toAccumulatedMetricDtoList(sortedListOfUniqueDates, aggregatedByDate, initialAggregate);
    }

    public static List<MetricDto> toDailyResidentCases(List<CovidBehindBarsEntity> entityList) {
        List<Date> sortedListOfUniqueDates = sortedListOfUniqueDates(entityList.stream().map(e -> e.getDate().getDate()).collect(Collectors.toList()));
        Map<Date, Integer> aggregatedByDate = new HashMap<>();

        entityList.forEach(entity -> {
            Date date = entity.getDate().getDate();
            Integer aggregatedAmount = aggregatedByDate.getOrDefault(date, 0);
            aggregatedByDate.put(date, aggregatedAmount + entity.getResidentCases());
        });

        return toMetricDtoList(sortedListOfUniqueDates, aggregatedByDate);
    }

    public static List<MetricDto> toDailyStaffCases(List<CovidBehindBarsEntity> entityList) {
        List<Date> sortedListOfUniqueDates = sortedListOfUniqueDates(entityList.stream().map(e -> e.getDate().getDate()).collect(Collectors.toList()));
        Map<Date, Integer> aggregatedByDate = new HashMap<>();

        entityList.forEach(entity -> {
            Date date = entity.getDate().getDate();
            Integer aggregatedAmount = aggregatedByDate.getOrDefault(date, 0);
            aggregatedByDate.put(date, aggregatedAmount + entity.getStaffCases());
        });

        return toMetricDtoList(sortedListOfUniqueDates, aggregatedByDate);
    }

    public static List<MetricDto> toDailyResidentDeaths(List<CovidBehindBarsEntity> entityList) {
        List<Date> sortedListOfUniqueDates = sortedListOfUniqueDates(entityList.stream().map(e -> e.getDate().getDate()).collect(Collectors.toList()));
        Map<Date, Integer> aggregatedByDate = new HashMap<>();

        entityList.forEach(entity -> {
            Date date = entity.getDate().getDate();
            Integer aggregatedAmount = aggregatedByDate.getOrDefault(date, 0);
            aggregatedByDate.put(date, aggregatedAmount + entity.getResidentDeaths());
        });

        return toMetricDtoList(sortedListOfUniqueDates, aggregatedByDate);
    }

    public static List<MetricDto> toDailyStaffDeaths(List<CovidBehindBarsEntity> entityList) {
        List<Date> sortedListOfUniqueDates = sortedListOfUniqueDates(entityList.stream().map(e -> e.getDate().getDate()).collect(Collectors.toList()));
        Map<Date, Integer> aggregatedByDate = new HashMap<>();

        entityList.forEach(entity -> {
            Date date = entity.getDate().getDate();
            Integer aggregatedAmount = aggregatedByDate.getOrDefault(date, 0);
            aggregatedByDate.put(date, aggregatedAmount + entity.getStaffDeaths());
        });

        return toMetricDtoList(sortedListOfUniqueDates, aggregatedByDate);
    }

    public static List<MetricDto> toDailyResidentTests(List<CovidBehindBarsEntity> entityList) {
        List<Date> sortedListOfUniqueDates = sortedListOfUniqueDates(entityList.stream().map(e -> e.getDate().getDate()).collect(Collectors.toList()));
        Map<Date, Integer> aggregatedByDate = new HashMap<>();

        entityList.forEach(entity -> {
            Date date = entity.getDate().getDate();
            Integer aggregatedAmount = aggregatedByDate.getOrDefault(date, 0);
            aggregatedByDate.put(date, aggregatedAmount + entity.getResidentTests());
        });

        return toMetricDtoList(sortedListOfUniqueDates, aggregatedByDate);
    }

    public static List<MetricDto> toDailyResidentAdministeredOneDose(List<CovidBehindBarsEntity> entityList) {
        List<Date> sortedListOfUniqueDates = sortedListOfUniqueDates(entityList.stream().map(e -> e.getDate().getDate()).collect(Collectors.toList()));
        Map<Date, Integer> aggregatedByDate = new HashMap<>();

        entityList.forEach(entity -> {
            Date date = entity.getDate().getDate();
            Integer aggregatedAmount = aggregatedByDate.getOrDefault(date, 0);
            aggregatedByDate.put(date, aggregatedAmount + entity.getResidentAdministeredOneDose());
        });

        return toMetricDtoList(sortedListOfUniqueDates, aggregatedByDate);
    }

    public static List<MetricDto> toDailyStaffAdministeredOneDose(List<CovidBehindBarsEntity> entityList) {
        List<Date> sortedListOfUniqueDates = sortedListOfUniqueDates(entityList.stream().map(e -> e.getDate().getDate()).collect(Collectors.toList()));
        Map<Date, Integer> aggregatedByDate = new HashMap<>();

        entityList.forEach(entity -> {
            Date date = entity.getDate().getDate();
            Integer aggregatedAmount = aggregatedByDate.getOrDefault(date, 0);
            aggregatedByDate.put(date, aggregatedAmount + entity.getStaffAdministeredOneDose());
        });

        return toMetricDtoList(sortedListOfUniqueDates, aggregatedByDate);
    }

    public static List<MetricDto> toDailyResidentAdministeredTwoDose(List<CovidBehindBarsEntity> entityList) {
        List<Date> sortedListOfUniqueDates = sortedListOfUniqueDates(entityList.stream().map(e -> e.getDate().getDate()).collect(Collectors.toList()));
        Map<Date, Integer> aggregatedByDate = new HashMap<>();

        entityList.forEach(entity -> {
            Date date = entity.getDate().getDate();
            Integer aggregatedAmount = aggregatedByDate.getOrDefault(date, 0);
            aggregatedByDate.put(date, aggregatedAmount + entity.getResidentAdministeredTwoDose());
        });

        return toMetricDtoList(sortedListOfUniqueDates, aggregatedByDate);
    }

    public static List<MetricDto> toDailyStaffAdministeredTwoDose(List<CovidBehindBarsEntity> entityList) {
        List<Date> sortedListOfUniqueDates = sortedListOfUniqueDates(entityList.stream().map(e -> e.getDate().getDate()).collect(Collectors.toList()));
        Map<Date, Integer> aggregatedByDate = new HashMap<>();

        entityList.forEach(entity -> {
            Date date = entity.getDate().getDate();
            Integer aggregatedAmount = aggregatedByDate.getOrDefault(date, 0);
            aggregatedByDate.put(date, aggregatedAmount + entity.getStaffAdministeredTwoDose());
        });

        return toMetricDtoList(sortedListOfUniqueDates, aggregatedByDate);
    }

    public static List<MetricDto> toDailyResidentCasesNDayAverage(List<CovidBehindBarsEntity> entityList, int nDay) {
        List<MetricDto> dailyResidentCases = toDailyResidentCases(entityList);
        List<Integer> values = new LinkedList<>();
        int index = 0;
        int sum = 0;

        List<MetricDto> results = new ArrayList<>();
        for (MetricDto residentCase : dailyResidentCases) {
            if (index < nDay) {
                values.add(residentCase.getValue());
                sum += residentCase.getValue();
                index++;
            }
            else {
                MetricDto metricDto = new MetricDto();
                metricDto.setDate(residentCase.getDate());
                metricDto.setValue(sum / nDay);
                results.add(metricDto);

                values.add(residentCase.getValue());
                sum += residentCase.getValue();
                sum -= values.get(0);
                values.remove(0);
            }
        }
        return results;
    }

    public static List<MetricDto> toDailyStaffCasesNDayAverage(List<CovidBehindBarsEntity> entityList, int nDay) {
        List<MetricDto> dailyStaffCases = toDailyStaffCases(entityList);
        List<Integer> values = new LinkedList<>();
        int index = 0;
        int sum = 0;

        List<MetricDto> results = new ArrayList<>();
        for (MetricDto staffCase : dailyStaffCases) {
            if (index < nDay) {
                values.add(staffCase.getValue());
                sum += staffCase.getValue();
                index++;
            }
            else {
                MetricDto metricDto = new MetricDto();
                metricDto.setDate(staffCase.getDate());
                metricDto.setValue(sum / nDay);
                results.add(metricDto);

                values.add(staffCase.getValue());
                sum += staffCase.getValue();
                sum -= values.get(0);
                values.remove(0);
            }
        }
        return results;
    }

    public static List<MetricDto> toDailyResidentDeathsNDayAverage(List<CovidBehindBarsEntity> entityList, int nDay) {
        List<MetricDto> dailyResidentDeaths = toDailyResidentDeaths(entityList);
        List<Integer> values = new LinkedList<>();
        int index = 0;
        int sum = 0;

        List<MetricDto> results = new ArrayList<>();
        for (MetricDto residentDeath : dailyResidentDeaths) {
            if (index < nDay) {
                values.add(residentDeath.getValue());
                sum += residentDeath.getValue();
                index++;
            }
            else {
                MetricDto metricDto = new MetricDto();
                metricDto.setDate(residentDeath.getDate());
                metricDto.setValue(sum / nDay);
                results.add(metricDto);

                values.add(residentDeath.getValue());
                sum += residentDeath.getValue();
                sum -= values.get(0);
                values.remove(0);
            }
        }
        return results;
    }

    public static List<MetricDto> toDailyStaffDeathsNDayAverage(List<CovidBehindBarsEntity> entityList, int nDay) {
        List<MetricDto> dailyStaffDeaths = toDailyStaffDeaths(entityList);
        List<Integer> values = new LinkedList<>();
        int index = 0;
        int sum = 0;

        List<MetricDto> results = new ArrayList<>();
        for (MetricDto staffDeath : dailyStaffDeaths) {
            if (index < nDay) {
                values.add(staffDeath.getValue());
                sum += staffDeath.getValue();
                index++;
            }
            else {
                MetricDto metricDto = new MetricDto();
                metricDto.setDate(staffDeath.getDate());
                metricDto.setValue(sum / nDay);
                results.add(metricDto);

                values.add(staffDeath.getValue());
                sum += staffDeath.getValue();
                sum -= values.get(0);
                values.remove(0);
            }
        }
        return results;
    }

    public static List<MetricDto> toDailyResidentTestsNDayAverage(List<CovidBehindBarsEntity> entityList, int nDay) {
        List<MetricDto> dailyResidentTests = toDailyResidentTests(entityList);
        List<Integer> values = new LinkedList<>();
        int index = 0;
        int sum = 0;

        List<MetricDto> results = new ArrayList<>();
        for (MetricDto residentTest : dailyResidentTests) {
            if (index < nDay) {
                values.add(residentTest.getValue());
                sum += residentTest.getValue();
                index++;
            }
            else {
                MetricDto metricDto = new MetricDto();
                metricDto.setDate(residentTest.getDate());
                metricDto.setValue(sum / nDay);
                results.add(metricDto);

                values.add(residentTest.getValue());
                sum += residentTest.getValue();
                sum -= values.get(0);
                values.remove(0);
            }
        }
        return results;
    }

    public static List<MetricDto> toDailyResidentAdministeredOneDoseNDayAverage(List<CovidBehindBarsEntity> entityList, int nDay) {
        List<MetricDto> dailyResidentAdministeredOneDose = toDailyResidentAdministeredOneDose(entityList);
        List<Integer> values = new LinkedList<>();
        int index = 0;
        int sum = 0;

        List<MetricDto> results = new ArrayList<>();
        for (MetricDto residentAdministeredOneDose : dailyResidentAdministeredOneDose) {
            if (index < nDay) {
                values.add(residentAdministeredOneDose.getValue());
                sum += residentAdministeredOneDose.getValue();
                index++;
            }
            else {
                MetricDto metricDto = new MetricDto();
                metricDto.setDate(residentAdministeredOneDose.getDate());
                metricDto.setValue(sum / nDay);
                results.add(metricDto);

                values.add(residentAdministeredOneDose.getValue());
                sum += residentAdministeredOneDose.getValue();
                sum -= values.get(0);
                values.remove(0);
            }
        }
        return results;
    }

    public static List<MetricDto> toDailyStaffAdministeredOneDoseNDayAverage(List<CovidBehindBarsEntity> entityList, int nDay) {
        List<MetricDto> dailyStaffAdministeredOneDose = toDailyStaffAdministeredOneDose(entityList);
        List<Integer> values = new LinkedList<>();
        int index = 0;
        int sum = 0;

        List<MetricDto> results = new ArrayList<>();
        for (MetricDto staffAdministeredOneDose : dailyStaffAdministeredOneDose) {
            if (index < nDay) {
                values.add(staffAdministeredOneDose.getValue());
                sum += staffAdministeredOneDose.getValue();
                index++;
            }
            else {
                MetricDto metricDto = new MetricDto();
                metricDto.setDate(staffAdministeredOneDose.getDate());
                metricDto.setValue(sum / nDay);
                results.add(metricDto);

                values.add(staffAdministeredOneDose.getValue());
                sum += staffAdministeredOneDose.getValue();
                sum -= values.get(0);
                values.remove(0);
            }
        }
        return results;
    }

    public static List<MetricDto> toDailyResidentAdministeredTwoDoseNDayAverage(List<CovidBehindBarsEntity> entityList, int nDay) {
        List<MetricDto> dailyResidentAdministeredTwoDose = toDailyResidentAdministeredTwoDose(entityList);
        List<Integer> values = new LinkedList<>();
        int index = 0;
        int sum = 0;

        List<MetricDto> results = new ArrayList<>();
        for (MetricDto residentAdministeredTwoDose : dailyResidentAdministeredTwoDose) {
            if (index < nDay) {
                values.add(residentAdministeredTwoDose.getValue());
                sum += residentAdministeredTwoDose.getValue();
                index++;
            }
            else {
                MetricDto metricDto = new MetricDto();
                metricDto.setDate(residentAdministeredTwoDose.getDate());
                metricDto.setValue(sum / nDay);
                results.add(metricDto);

                values.add(residentAdministeredTwoDose.getValue());
                sum += residentAdministeredTwoDose.getValue();
                sum -= values.get(0);
                values.remove(0);
            }
        }
        return results;
    }

    public static List<MetricDto> toDailyStaffAdministeredTwoDoseNDayAverage(List<CovidBehindBarsEntity> entityList, int nDay) {
        List<MetricDto> dailyStaffAdministeredTwoDose = toDailyStaffAdministeredTwoDose(entityList);
        List<Integer> values = new LinkedList<>();
        int index = 0;
        int sum = 0;

        List<MetricDto> results = new ArrayList<>();
        for (MetricDto staffAdministeredTwoDose : dailyStaffAdministeredTwoDose) {
            if (index < nDay) {
                values.add(staffAdministeredTwoDose.getValue());
                sum += staffAdministeredTwoDose.getValue();
                index++;
            }
            else {
                MetricDto metricDto = new MetricDto();
                metricDto.setDate(staffAdministeredTwoDose.getDate());
                metricDto.setValue(sum / nDay);
                results.add(metricDto);

                values.add(staffAdministeredTwoDose.getValue());
                sum += staffAdministeredTwoDose.getValue();
                sum -= values.get(0);
                values.remove(0);
            }
        }
        return results;
    }
}
