package com.projectrefocus.service.request.enums;

public enum DataOrientation {
    cumulative,
    daily,
    dailyPer100K,
    daily7DayAvg,
    daily14DayAvg,
    daily7DayAvgPer100K,
    daily14DayAvgPer100K,
    weekly,
    weeklyPer100K,
    monthly,
    monthlyPer100K,
    percentageChangeInDaily,
    percentChangeInDailyOver7,
    percentChangeInDailyOver14,

    mortalityRate,
    mortalityRateOver7Days,
    mortalityRateOver14Days,
    percentChangeInMortalityRate,
    percentChangeInMortalityRateOver7,
    percentChangeInMortalityRateOver14,

    weeklyMortalityRate,
    percentChangeInWeeklyMortalityRate,
    monthlyMortalityRate,
    percentChangeInMonthlyMortalityRate
}
