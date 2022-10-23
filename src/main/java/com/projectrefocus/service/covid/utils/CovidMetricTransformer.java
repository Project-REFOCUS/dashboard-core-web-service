package com.projectrefocus.service.covid.utils;

import com.projectrefocus.service.common.utils.MetricTransformer;
import com.projectrefocus.service.covid.dto.CovidMetricDto;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CovidMetricTransformer extends MetricTransformer {

    private static final int PER_100K = 100000;

    protected static Integer calculateValuePer100K(Integer value, Integer denominator) {
        BigDecimal decimalValue = new BigDecimal(value);
        BigDecimal decimalDenominator = new BigDecimal(denominator);
        BigDecimal result = decimalValue.divide(decimalDenominator, MathContext.DECIMAL32).multiply(new BigDecimal(PER_100K));

        return result.intValue();
    }

    protected static List<CovidMetricDto> toCovidMetricDtoList(List<Date> dates, Map<Date, Integer> aggregationByDate) {
        return dates.stream().map(date -> {
            CovidMetricDto metricDto = new CovidMetricDto();
            metricDto.setValue(aggregationByDate.get(date));
            metricDto.setDate(date);

            return metricDto;
        }).collect(Collectors.toList());
    }

    protected static List<CovidMetricDto> toAccumulatedCovidMetricDtoList(List<Date> dates, Map<Date, Integer> aggregationByDate, Integer initialAggregate) {
        List<CovidMetricDto> results = new ArrayList<>();
        Integer accumulation = initialAggregate;
        for (Date uniqueDate : dates) {
            CovidMetricDto metricDto = new CovidMetricDto();
            accumulation += aggregationByDate.get(uniqueDate);
            metricDto.setValue(accumulation);
            metricDto.setDate(uniqueDate);

            results.add(metricDto);
        }

        return results;
    }

    protected static List<CovidMetricDto> toNDayAverageCovidMetricDtoList(List<CovidMetricDto> dtoList, int nDay) {
        List<Integer> values = new LinkedList<>();
        int index = 0;
        int sum = 0;

        List<CovidMetricDto> results = new ArrayList<>();
        for (CovidMetricDto dto : dtoList) {
            if (index < nDay) {
                values.add(dto.getValue());
                sum += dto.getValue();
                index++;
            }
            else {
                CovidMetricDto metricDto = new CovidMetricDto();
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

    private static Integer percentChange(int initial, int target) {
        if (initial == 0) {
            return initial;
        }
        BigDecimal targetMinusInitialDecimal = new BigDecimal(target - initial);
        BigDecimal initialDecimal = new BigDecimal(initial);
        BigDecimal result = targetMinusInitialDecimal.divide(initialDecimal, MathContext.DECIMAL32)
                .multiply(BigDecimal.TEN).multiply(BigDecimal.TEN);
        return result.intValue();
    }

    protected static List<CovidMetricDto> toPercentChangeInValue(List<CovidMetricDto> metricDtoList) {
        List<CovidMetricDto> results = new ArrayList<>();
        int previousValue = metricDtoList.remove(0).getValue();
        for (CovidMetricDto dto : metricDtoList) {
            int currentValue = dto.getValue();
            dto.setValue(percentChange(previousValue, dto.getValue()));
            results.add(dto);

            previousValue = currentValue;
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
