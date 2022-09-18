package com.projectrefocus.service.covid.utils;

import com.projectrefocus.service.request.enums.DataOrientation;

import java.util.Date;

public class CovidServiceUtils {

    public static Date adjustedDate(Date startDate, DataOrientation orientation) {
        return switch (orientation) {
            case daily7DayAvg -> new Date(startDate.getTime() - (86400000 * 7));
            case daily14DayAvg -> new Date(startDate.getTime() - (86400000 * 14));
            case percentChangeInDailyOver7, percentChangeInDailyOver14 -> new Date(startDate.getTime() - 86400000);
            default -> startDate;
        };
    }
}
