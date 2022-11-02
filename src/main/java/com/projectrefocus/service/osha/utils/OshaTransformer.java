package com.projectrefocus.service.osha.utils;

import com.projectrefocus.service.common.dto.MetricDto;
import com.projectrefocus.service.common.utils.MetricTransformer;
import com.projectrefocus.service.osha.entity.OshaComplaintsCalendarDateEntity;

import java.util.*;
import java.util.stream.Collectors;

public class OshaTransformer extends MetricTransformer {

    public static List<MetricDto> toCumulativeComplaints(List<OshaComplaintsCalendarDateEntity> entityList, Integer initialAggregate) {
         List<Date> sortedListUniqueDates = sortedListOfUniqueDates(entityList.stream().map(OshaComplaintsCalendarDateEntity::getDate).collect(Collectors.toList()));
         Map<Date, Integer> aggregatedByDate = new HashMap<>();

         entityList.forEach(entity -> {
             Date date = entity.getDate();
             Integer aggregatedAmount = aggregatedByDate.getOrDefault(date, 0);
             aggregatedByDate.put(date, aggregatedAmount + (entity.getComplaints().isEmpty() ? 0 : 1));
         });

         return toAccumulatedMetricDtoList(sortedListUniqueDates, aggregatedByDate, initialAggregate);
    }
}
