package com.projectrefocus.service.covid.utils;

import com.projectrefocus.service.covid.dto.CovidMetricDto;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class CovidMetricTransformer {

    private static final int PER_100K = 100000;

    protected static Integer calculateValuePer100K(Integer value, Integer denominator) {
        BigDecimal decimalValue = new BigDecimal(value);
        BigDecimal decimalDenominator = new BigDecimal(denominator);
        BigDecimal result = decimalValue.divide(decimalDenominator, MathContext.DECIMAL32).multiply(new BigDecimal(PER_100K));

        return result.intValue();
    }

    protected static List<Date> sortedListOfUniqueDates(List<Date> dateList) {
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

    protected static List<CovidMetricDto> toCovidMetricDtoList(List<Date> dates, Map<Date, Integer> aggregationByDate) {
        return dates.stream().map(date -> {
            CovidMetricDto metricDto = new CovidMetricDto();
            metricDto.setValue(aggregationByDate.get(date));
            metricDto.setDate(date);

            return metricDto;
        }).collect(Collectors.toList());
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
}
