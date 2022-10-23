package com.projectrefocus.service.police.utils;

import com.projectrefocus.service.calendar.entity.CalendarDateEntity;
import com.projectrefocus.service.common.dto.MetricDto;
import com.projectrefocus.service.common.utils.MetricTransformer;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PoliceTransformer extends MetricTransformer {

    public static List<MetricDto> toCumulativeShootings(List<CalendarDateEntity> entityList, Integer initialAggregate) {
        List<Date> sortedListOfUniqueDates = sortedListOfUniqueDates(entityList.stream().map(CalendarDateEntity::getDate).collect(Collectors.toList()));
        Map<Date, Integer> aggregatedByDate = new HashMap<>();

        entityList.forEach(entity -> {
                Date date = entity.getDate();
                Integer aggregatedAmount = aggregatedByDate.getOrDefault(date, 0);
                aggregatedByDate.put(date, aggregatedAmount + (entity.getShootings().isEmpty() ? 0 : 1));
        });

        return toAccumulatedMetricDtoList(sortedListOfUniqueDates, aggregatedByDate, initialAggregate);
    }
}