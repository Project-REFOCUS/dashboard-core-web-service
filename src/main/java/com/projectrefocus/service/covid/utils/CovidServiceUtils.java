package com.projectrefocus.service.covid.utils;

import com.projectrefocus.service.request.enums.DataOrientation;

import java.util.Date;

public class CovidServiceUtils {

    public static Date adjustedDate(Date startDate, DataOrientation orientation) {
        switch (orientation) {
            case daily7DayAvg:
                return new Date(startDate.getTime() - (86400000 * 7));

            case daily14DayAvg:
                return new Date(startDate.getTime() - (86400000 * 14));
        }
        return startDate;
    }
}
