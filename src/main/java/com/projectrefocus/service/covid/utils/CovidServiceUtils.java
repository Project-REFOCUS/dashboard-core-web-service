package com.projectrefocus.service.covid.utils;

import com.projectrefocus.service.request.enums.DataOrientation;

import java.util.Date;

public class CovidServiceUtils {

    private static final Long MILLIS_IN_DAY = 86400000L;

    public static Date adjustedDate(Date startDate, DataOrientation orientation) {
        return switch (orientation) {
            case daily7DayAvg, daily7DayAvgPer100K -> new Date(startDate.getTime() - (MILLIS_IN_DAY * 7));
            case daily14DayAvg, daily14DayAvgPer100K -> new Date(startDate.getTime() - (MILLIS_IN_DAY * 14));
            case percentChangeInDailyOver7, percentChangeInDailyOver14 -> new Date(startDate.getTime() - MILLIS_IN_DAY);
            default -> startDate;
        };
    }
}
